package com.josephbeaulieu.rdpocketpal.anthropometrics

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.josephbeaulieu.rdpocketpal.R
import com.josephbeaulieu.rdpocketpal.model.PreferenceRepository
import com.josephbeaulieu.rdpocketpal.model.QueryResult
import com.josephbeaulieu.rdpocketpal.model.UserPreferences
import com.josephbeaulieu.rdpocketpal.quickmethod.FatalCalculationException
import com.josephbeaulieu.rdpocketpal.util.*
import com.josephbeaulieu.rdpocketpal.util.Unit
import kotlinx.coroutines.launch

private const val SELECTED_SEX_OLD_VALUE_KEY = "selectedSexOldValue"
private const val SELECTED_UNIT_OLD_VALUE_KEY = "selectedUnitOldValue"
private const val WEIGHT_ERROR_MSG_KEY = "weightErrorMsgKey"
private const val HEIGHT_ERROR_MSG_KEY = "heightErrorMsgKey"

class AnthropometricsViewModel(application: Application, savedStateHandle: SavedStateHandle)
    : AndroidViewModel(application), LifecycleObserver {

    //region LiveData
    // UI LiveData
    val mSelectedSex =  MutableLiveData<String>()
    val mSelectedUnit =  MutableLiveData<String>()
    val mWeight =  MutableLiveData<String>()
    val mHeight =  MutableLiveData<String>()
    val mBmi =  MutableLiveData<String>()
    val mIbw =  MutableLiveData<String>()
    val mPercentIbw =  MutableLiveData<String>()
    val mAdjustedIbw =  MutableLiveData<String>()

    // Unit labels
    val mWeightUnitLabel =  MutableLiveData<String>()
    val mHeightUnitLabel =  MutableLiveData<String>()
    val mIbwUnitLabel = MutableLiveData<String>()
    val mAdjustedIbwUnitLabel = MutableLiveData<String>()

    //region SavedState data
    private var mState: SavedStateHandle = savedStateHandle
    private val mSelectedSexOldValue: MutableLiveData<String> =
            mState.getLiveData(SELECTED_SEX_OLD_VALUE_KEY)
    private val mSelectedUnitOldValue: MutableLiveData<String> =
            mState.getLiveData(SELECTED_UNIT_OLD_VALUE_KEY)
    val mWeightErrorMsg: MutableLiveData<String> = mState.getLiveData(WEIGHT_ERROR_MSG_KEY)
    val mHeightErrorMsg: MutableLiveData<String> = mState.getLiveData(HEIGHT_ERROR_MSG_KEY)
    //endregion

    private var mApplicationContext: Context = application.applicationContext

    //region Listeners
    fun onClearClicked() {
        clearInput()
        clearOutput()
    }

    fun onCalculateClicked() {
        if (allInputValid()) {
            // get user preferences and calculate
            viewModelScope.launch {
                when (val prefs = PreferenceRepository()
                        .getAllNumericSettings(mApplicationContext)) {
                    is QueryResult.Success<UserPreferences> -> calculate(prefs.data)
                    is QueryResult.Failure ->
                        // error accessing settings, show Toast
                        showToast(mApplicationContext
                                , R.string.toast_failed_to_access_settings, Toast.LENGTH_LONG)
                }
            }
        } else {
            // validation error, show Toast
            showToast(mApplicationContext, R.string.toast_invalid_fields, Toast.LENGTH_LONG)
        }
    }

    fun onSexRadioBtnClicked(btn: RadioButton) {
        clearOutputsAndToastIfNecessary(btn
                , mSelectedSexOldValue, R.string.toast_results_cleared_sex_change)
    }

    fun onUnitRadioBtnClicked(btn: RadioButton) {
        clearOutputsAndToastIfNecessary(btn, mSelectedUnitOldValue
                , R.string.toast_results_cleared_unit_change)
    }
    //endregion

    //region Validation
    private fun allInputValid(): Boolean {
        return validateFieldsAndSetErrors(mApplicationContext
                , FieldErrorPair(mWeight, mWeightErrorMsg)
                , FieldErrorPair(mHeight, mHeightErrorMsg))
    }
    //endregion

    //region Calculation
    private fun calculate(prefs: UserPreferences) {
        try {
            calculateBmi(prefs)
            calculateIbw(prefs)
            calculatePercentIbw(prefs)
            calculateAdjustedIbw(prefs)
        } catch (e: FatalCalculationException) {
            showToast(mApplicationContext, e.message, Toast.LENGTH_SHORT)
        }
    }

    @Throws(FatalCalculationException::class)
    private fun calculateBmi(prefs: UserPreferences) {
        mBmi.value = NumberUtil.roundOrTruncate(mApplicationContext
                , prefs
                , CalculationUtil.calculateBmi(getUnit()
                , NumberUtil.parseDouble(mWeight)
                , NumberUtil.parseDouble(mHeight)))
    }

    @Throws(FatalCalculationException::class)
    private fun calculateIbw(prefs: UserPreferences) {
        mIbw.value = NumberUtil.roundOrTruncate(mApplicationContext
                , prefs
                , CalculationUtil.calculateIbwHamwi(getUnit()
                , getSex()
                , NumberUtil.parseDouble(mHeight)))
    }

    @Throws(FatalCalculationException::class)
    private fun calculatePercentIbw(prefs: UserPreferences) {
        mPercentIbw.value = NumberUtil.roundOrTruncate(mApplicationContext
                , prefs
                , CalculationUtil.calculatePercentIbw(getUnit()
                , getSex()
                , NumberUtil.parseDouble(mWeight)
                , NumberUtil.parseDouble(mHeight)))
    }

    @Throws(FatalCalculationException::class)
    private fun calculateAdjustedIbw(prefs: UserPreferences) {
        mAdjustedIbw.value = NumberUtil.roundOrTruncate(mApplicationContext
                , prefs
                , CalculationUtil.calculateAdjustedIbw(getUnit()
                , getSex()
                , NumberUtil.parseDouble(mWeight)
                , NumberUtil.parseDouble(mHeight)))
    }
    //endregion

    //region Helper methods
    private fun clearInput() {
        clearFields(mHeight, mWeight)
    }

    private fun clearOutput(): Boolean {
        return clearFields(mBmi, mIbw, mPercentIbw, mAdjustedIbw)
    }

    private fun clearOutputsAndToastIfNecessary(btn: RadioButton
                                                , oldValue: MutableLiveData<String>
                                                , @StringRes errorToast: Int) {
        // just to make things easier to understand
        val isInitialSelectionAndNotDefault =
                oldValue.value == null && !isDefaultRadioBtnChecked(btn)
        val isNotInitialSelectionAndDiffThanLast =
                oldValue.value != null && btn.text.toString() != oldValue.value

        if (isInitialSelectionAndNotDefault || isNotInitialSelectionAndDiffThanLast) {
            // if we're dealing with the Unit RadioButton, set the units
            if (oldValue == mSelectedUnitOldValue) {
                setUnits()
            }
            // clear output fields and show Toast as to why
            if (clearOutput()) {
                showToast(mApplicationContext, errorToast, Toast.LENGTH_LONG)
            }
        }

        oldValue.value = btn.text.toString()
    }

    @Throws(FatalCalculationException::class)
    private fun getSex(): Sex {
        return when (mSelectedSex.value) {
            mApplicationContext.getString(R.string.text_male) -> Sex.MALE
            mApplicationContext.getString(R.string.text_female) -> Sex.FEMALE
            else -> throw FatalCalculationException("Sex selection not valid")
        }
    }

    @Throws(FatalCalculationException::class)
    private fun getUnit(): Unit {
        return when (mSelectedUnit.value) {
            mApplicationContext.getString(R.string.text_metric) -> Unit.METRIC
            mApplicationContext.getString(R.string.text_standard) -> Unit.STANDARD
            else -> throw FatalCalculationException("Unit selection not valid")
        }
    }

    private fun setUnits() {
        when (getUnit()) {
            Unit.METRIC -> setUnitsMetric()
            Unit.STANDARD -> setUnitsStandard()
        }
    }

    private fun setUnitsMetric() {
        mWeightUnitLabel.value = mApplicationContext.getString(R.string.text_kg)
        mHeightUnitLabel.value = mApplicationContext.getString(R.string.text_cm)
        mIbwUnitLabel.value = mApplicationContext.getString(R.string.text_kg)
        mAdjustedIbwUnitLabel.value = mApplicationContext.getString(R.string.text_kg)

    }

    private fun setUnitsStandard() {
        mWeightUnitLabel.value = mApplicationContext.getString(R.string.text_lb)
        mHeightUnitLabel.value = mApplicationContext.getString(R.string.text_in)
        mIbwUnitLabel.value = mApplicationContext.getString(R.string.text_lb)
        mAdjustedIbwUnitLabel.value = mApplicationContext.getString(R.string.text_lb)
    }
    //endregion

    //region Lifecycle Events
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        // RadioGroup Binding Adapter doesn't fire child listeners as to avoid unintended behavior
        // on orientation change, etc. Listeners only fired on actual presses, so set units here.
        setUnits()
    }
    //endregion

    //region SavedState methods
    fun saveState() {
        mState.set(SELECTED_SEX_OLD_VALUE_KEY, mSelectedSexOldValue.value)
        mState.set(SELECTED_UNIT_OLD_VALUE_KEY, mSelectedUnitOldValue.value)
        mState.set(WEIGHT_ERROR_MSG_KEY, mWeightErrorMsg.value)
        mState.set(HEIGHT_ERROR_MSG_KEY, mHeightErrorMsg.value)
    }
    //endregion

    //region Test against process death
    // couldn't find a way to test process death with Espresso, so just leaving this method here
    @SuppressLint("unused")
    private fun checkPersistenceAfterSystemInitProcessDeath() {
        val input = "Sex: ${mSelectedSex.value}" +
                "\nUnit: ${mSelectedUnit.value}" +
                "\nWeight: ${mWeight.value}" +
                "\nHeight: ${mHeight.value}"

        val output = "BMI: ${mBmi.value}" +
                "\nIBW: ${mIbw.value}" +
                "\n%IBW: ${mPercentIbw.value}" +
                "\nAdj. IBW: ${mAdjustedIbw.value}"

        val errors = "Weight err: ${mWeightErrorMsg.value}" +
                "\nHeight err: ${mHeightErrorMsg.value}"

        showToast(mApplicationContext, "$input\n$output\n$errors", Toast.LENGTH_LONG)
    }
    //endregion
}