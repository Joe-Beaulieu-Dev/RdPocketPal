package com.example.rdpocketpal2.quickmethod;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.rdpocketpal2.R;
import com.example.rdpocketpal2.model.CoroutineCallbackListener;
import com.example.rdpocketpal2.model.PreferenceRepository;
import com.example.rdpocketpal2.model.QueryResult;
import com.example.rdpocketpal2.model.UserPreferences;
import com.example.rdpocketpal2.util.CalculationUtil;
import com.example.rdpocketpal2.util.Constants;
import com.example.rdpocketpal2.util.FieldErrorPair;
import com.example.rdpocketpal2.util.NumberUtil;
import com.example.rdpocketpal2.util.UiUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.SavedStateHandle;

public class QuickMethodViewModel extends AndroidViewModel implements
        CoroutineCallbackListener, LifecycleObserver {
    private static final String LOG_TAG = "QuickMethodViewModel";

    //region LiveData
    // UI LiveData
    public MutableLiveData<String> mWeight = new MutableLiveData<>();
    public MutableLiveData<String> mUnitSelection = new MutableLiveData<>();
    public MutableLiveData<String> mKcalPerKgMin = new MutableLiveData<>();
    public MutableLiveData<String> mKcalPerKgMax = new MutableLiveData<>();
    public MutableLiveData<String> mKcalPerDayMin = new MutableLiveData<>();
    public MutableLiveData<String> mKcalPerDayMax = new MutableLiveData<>();
    public MutableLiveData<String> mGramsPerKgMin = new MutableLiveData<>();
    public MutableLiveData<String> mGramsPerKgMax = new MutableLiveData<>();
    public MutableLiveData<String> mGramsPerDayMin = new MutableLiveData<>();
    public MutableLiveData<String> mGramsPerDayMax = new MutableLiveData<>();
    public MutableLiveData<String> mMlPerKgMin = new MutableLiveData<>();
    public MutableLiveData<String> mMlPerKgMax = new MutableLiveData<>();
    public MutableLiveData<String> mMlPerDayMin = new MutableLiveData<>();
    public MutableLiveData<String> mMlPerDayMax = new MutableLiveData<>();

    // Unit labels
    public MutableLiveData<String> mWeightUnitLabel = new MutableLiveData<>();

    // Error messages
    public MutableLiveData<String> mWeightErrorMsg = new MutableLiveData<>();
    public MutableLiveData<String> mKcalPerKgMinErrorMsg = new MutableLiveData<>();
    public MutableLiveData<String> mKcalPerKgMaxErrorMsg = new MutableLiveData<>();
    public MutableLiveData<String> mGramsPerKgMinErrorMsg = new MutableLiveData<>();
    public MutableLiveData<String> mGramsPerKgMaxErrorMsg = new MutableLiveData<>();
    public MutableLiveData<String> mMlPerKgMinErrorMsg = new MutableLiveData<>();
    public MutableLiveData<String> mMlPerKgMaxErrorMsg = new MutableLiveData<>();
    //endregion

    //region SavedState data
    // Data
    private static final String SELECTED_UNIT_OLD_VALUE_KEY = "selectedUnitOldValue";
    private MutableLiveData<String> mSelectedUnitOldValue = new MutableLiveData<>();

    // Error message keys
    private static final String WEIGHT_ERROR_MSG_KEY = "weightErrorMsgKey";
    private static final String KCAL_PER_KG_MIN_ERROR_MSG_KEY = "kcalPerKgMinErrorMsgKey";
    private static final String KCAL_PER_KG_MAX_ERROR_MSG_KEY = "kcalPerKgMaxErrorMsgKey";
    private static final String GRAMS_PER_KG_MIN_ERROR_MSG_KEY = "gramsPerKgMinErrorMsgKey";
    private static final String GRAMS_PER_KG_MAX_ERROR_MSG_KEY = "gramsPerKgMaxErrorMsgKey";
    private static final String ML_PER_KG_MIN_ERROR_MSG_KEY = "mlPerKgMinErrorMsgKey";
    private static final String ML_PER_KG_MAX_ERROR_MSG_KEY = "mlPerKgMaxErrorMsgKey";
    //endregion

    // okay since when the app dies, so does the ViewModel
    @SuppressLint("StaticFieldLeak")
    private Context mApplicationContext;
    private SavedStateHandle mState;
    private PreferenceRepository mRepo;
    private UserPreferences mPrefs;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CALORIE, PROTEIN, FLUID})
    private @interface CalculationType{}
    private static final int CALORIE = 0;
    private static final int PROTEIN = 1;
    private static final int FLUID = 2;

    public QuickMethodViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);

        mApplicationContext = application.getApplicationContext();
        mState = handle;
        mRepo = new PreferenceRepository();

        // restore data not persisted through system initiated process death
        restoreState();
    }

    //region Button Listeners
    public void onUnitRadioBtnClicked(RadioButton btn) {
        clearAllOutputsAndToastIfNecessary(btn, mSelectedUnitOldValue);
    }

    public void onCalorieClearClicked() {
        // clear all Calorie fields
        clearCalorieInput();
        clearCalorieOutput();
    }

    public void onCalorieCalculateClicked() {
        // validate required fields, then set daily values
        if (validateFields(CALORIE)) {
            // calculate and set daily values
            calculate(CALORIE);
        } else {
            // display error Toast
            UiUtil.showToast(mApplicationContext
                    , R.string.toast_invalid_fields, Toast.LENGTH_SHORT);
        }
    }

    public void onProteinClearClicked() {
        // clear all Protein fields
        clearProteinInput();
        clearProteinOutput();
    }

    public void onProteinCalculateClicked() {
        // validate required fields, then set daily values
        if (validateFields(PROTEIN)) {
            // calculate and set daily values
            calculate(PROTEIN);
        } else {
            // display error Toast
            UiUtil.showToast(mApplicationContext
                    , R.string.toast_invalid_fields, Toast.LENGTH_SHORT);
        }
    }

    public void onFluidClearClicked() {
        // clear all Fluid fields
        clearFluidInput();
        clearFluidOutput();
    }

    public void onFluidCalculateClicked() {
        // validate required fields, then set daily values
        if (validateFields(FLUID)) {
            // calculate and set daily values
            calculate(FLUID);
        } else {
            // display error Toast
            UiUtil.showToast(mApplicationContext
                    , R.string.toast_invalid_fields, Toast.LENGTH_SHORT);
        }
    }
    //endregion

    //region Calculation Methods
    private void calculate(@CalculationType int type) {
        // calculate values and set results to appropriate fields
        switch (type) {
            case CALORIE:
                calculateAndSetDailyValues(mKcalPerKgMin
                        , mKcalPerKgMax
                        , mKcalPerDayMin
                        , mKcalPerDayMax);
                break;
            case PROTEIN:
                calculateAndSetDailyValues(mGramsPerKgMin
                        , mGramsPerKgMax
                        , mGramsPerDayMin
                        , mGramsPerDayMax);
                break;
            case FLUID:
                calculateAndSetDailyValues(mMlPerKgMin
                        , mMlPerKgMax
                        , mMlPerDayMin
                        , mMlPerDayMax);
                break;
        }
    }

    private void calculateAndSetDailyValues(MutableLiveData<String> minFactor,
                                            MutableLiveData<String> maxFactor,
                                            MutableLiveData<String> minResult,
                                            MutableLiveData<String> maxResult) {
        minResult.setValue(NumberUtil.roundOrTruncate(
                mApplicationContext, mPrefs, calculateValuePerDay(minFactor)));
        maxResult.setValue(NumberUtil.roundOrTruncate(
                mApplicationContext, mPrefs, calculateValuePerDay(maxFactor)));
    }

    private double calculateValuePerDay(MutableLiveData<String> factor) {
        return CalculationUtil.calculateQuickMethod(getUnit()
                , NumberUtil.parseDouble(mWeight)
                , NumberUtil.parseDouble(factor)
        );
    }
    //endregion

    //region Validation Methods
    private boolean validateFields(@CalculationType int type) {
        boolean isValid = true;

        // validate Weight field
        if (!isWeightValid()) {
            isValid = false;
        }

        // validate other required fields based on Calculation type
        switch (type) {
            case CALORIE:
                if (!areCalorieFieldsValid()) {
                    isValid = false;
                }
                break;
            case PROTEIN:
                if (!areProteinFieldsValid()) {
                    isValid = false;
                }
                break;
            case FLUID:
                if (!areFluidFieldsValid()) {
                    isValid = false;
                }
                break;
        }

        return isValid;
    }

    private boolean isWeightValid() {
        return UiUtil.validateFieldsAndSetErrors(mApplicationContext
                , new FieldErrorPair(mWeight, mWeightErrorMsg)
        );
    }

    private boolean areCalorieFieldsValid() {
        return UiUtil.validateFieldsAndSetErrors(mApplicationContext
                , new FieldErrorPair(mKcalPerKgMin, mKcalPerKgMinErrorMsg)
                , new FieldErrorPair(mKcalPerKgMax, mKcalPerKgMaxErrorMsg)
        );
    }

    private boolean areProteinFieldsValid() {
        return UiUtil.validateFieldsAndSetErrors(mApplicationContext
                , new FieldErrorPair(mGramsPerKgMin, mGramsPerKgMinErrorMsg)
                , new FieldErrorPair(mGramsPerKgMax, mGramsPerKgMaxErrorMsg)
        );
    }

    private boolean areFluidFieldsValid() {
        return UiUtil.validateFieldsAndSetErrors(mApplicationContext
                , new FieldErrorPair(mMlPerKgMin, mMlPerKgMinErrorMsg)
                , new FieldErrorPair(mMlPerKgMax, mMlPerKgMaxErrorMsg)
        );
    }
    //endregion

    //region Helper Methods
    private int getUnit() throws FatalCalculationException {
        String unit = mUnitSelection.getValue();

        if (unit == null) {
            throw new NullPointerException();
        }

        // compare selection String to String Resource currently being
        // used in order to decide which units are being used
        if (unit.equals(mApplicationContext.getResources().getString(R.string.text_metric))) {
            return Constants.METRIC;
        } else if (unit.equals(mApplicationContext.getResources().getString(R.string.text_standard))) {
            return Constants.STANDARD;
        } else {
            throw new FatalCalculationException("Unit selection not valid");
        }
    }

    private void setUiDataToMetric() {
        mWeightUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_kg));
    }

    private void setUiDataToStandard() {
        mWeightUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_lb));
    }

    private void clearCalorieInput() {
        UiUtil.clearFields(mKcalPerKgMin, mKcalPerKgMax);
    }

    private void clearProteinInput() {
        UiUtil.clearFields(mGramsPerKgMin, mGramsPerKgMax);
    }

    private void clearFluidInput() {
        UiUtil.clearFields(mMlPerKgMin, mMlPerKgMax);
    }

    /**
     * Clears all results on screen. returns true if *any* results are cleared.
     *
     * @return true if any results are cleared
     */
    private boolean clearAllOutput() {
        boolean anyFieldsCleared = false;

        // clear all fields and set flag
        if (clearCalorieOutput()) {
            anyFieldsCleared = true;
        }
        if (clearProteinOutput()) {
            anyFieldsCleared = true;
        }
        if (clearFluidOutput()) {
            anyFieldsCleared = true;
        }

        return anyFieldsCleared;
    }

    private boolean clearCalorieOutput() {
        return UiUtil.clearFields(mKcalPerDayMin, mKcalPerDayMax);
    }

    private boolean clearProteinOutput() {
        return UiUtil.clearFields(mGramsPerDayMin, mGramsPerDayMax);
    }

    private boolean clearFluidOutput() {
        return UiUtil.clearFields(mMlPerDayMin, mMlPerDayMax);
    }

    private void clearAllOutputsAndToastIfNecessary(RadioButton btn
            , MutableLiveData<String> oldValue) {
        // just to make things easier to understand
        boolean isInitialSelectionAndNotDefault =
                oldValue.getValue() == null && !UiUtil.isDefaultRadioBtnChecked(btn);
        boolean isNotInitialSelectionAndDiffThanLast =
                oldValue.getValue() != null
                        && !btn.getText().toString().equals(oldValue.getValue());

        if (isInitialSelectionAndNotDefault || isNotInitialSelectionAndDiffThanLast) {
            setUnits();
            // clear output fields and show Toast as to why
            if (clearAllOutput()) {
                UiUtil.showToast(mApplicationContext
                        , R.string.toast_results_cleared_unit_change
                        , Toast.LENGTH_LONG);
            }
        }

        oldValue.setValue(btn.getText().toString());
    }
    //endregion

    //region SavedState methods
    void saveState() {
        mState.set(SELECTED_UNIT_OLD_VALUE_KEY, mSelectedUnitOldValue.getValue());
        mState.set(WEIGHT_ERROR_MSG_KEY, mWeightErrorMsg.getValue());
        mState.set(KCAL_PER_KG_MIN_ERROR_MSG_KEY, mKcalPerKgMinErrorMsg.getValue());
        mState.set(KCAL_PER_KG_MAX_ERROR_MSG_KEY, mKcalPerKgMaxErrorMsg.getValue());
        mState.set(GRAMS_PER_KG_MIN_ERROR_MSG_KEY, mGramsPerKgMinErrorMsg.getValue());
        mState.set(GRAMS_PER_KG_MAX_ERROR_MSG_KEY, mGramsPerKgMaxErrorMsg.getValue());
        mState.set(ML_PER_KG_MIN_ERROR_MSG_KEY, mMlPerKgMinErrorMsg.getValue());
        mState.set(ML_PER_KG_MAX_ERROR_MSG_KEY, mMlPerKgMaxErrorMsg.getValue());
    }

    private void restoreState() {
        mSelectedUnitOldValue = mState.getLiveData(SELECTED_UNIT_OLD_VALUE_KEY);
        mWeightErrorMsg = mState.getLiveData(WEIGHT_ERROR_MSG_KEY);
        mKcalPerKgMinErrorMsg = mState.getLiveData(KCAL_PER_KG_MIN_ERROR_MSG_KEY);
        mKcalPerKgMaxErrorMsg = mState.getLiveData(KCAL_PER_KG_MAX_ERROR_MSG_KEY);
        mGramsPerKgMinErrorMsg = mState.getLiveData(GRAMS_PER_KG_MIN_ERROR_MSG_KEY);
        mGramsPerKgMaxErrorMsg = mState.getLiveData(GRAMS_PER_KG_MAX_ERROR_MSG_KEY);
        mMlPerKgMinErrorMsg = mState.getLiveData(ML_PER_KG_MIN_ERROR_MSG_KEY);
        mMlPerKgMaxErrorMsg = mState.getLiveData(ML_PER_KG_MAX_ERROR_MSG_KEY);
    }
    //endregion

    //region Callbacks
    @Override
    public <T> void onCoroutineFinished(QueryResult<T> result) {
        if (result instanceof QueryResult.Success
                && ((QueryResult.Success<T>) result).getData() instanceof UserPreferences) {
            // get UserPreference object
            mPrefs = (UserPreferences) ((QueryResult.Success<T>) result).getData();
        } else if (result instanceof QueryResult.Failure) {
            // get and log exception
            Exception e = ((QueryResult.Failure) result).getException();
            if (e.getMessage() != null) {
                Log.e(LOG_TAG, e.getMessage(), e);
            }

            // display feedback to user
            Toast.makeText(mApplicationContext
                    , mApplicationContext.getString(R.string.toast_failed_to_access_settings)
                    , Toast.LENGTH_SHORT)
                    .show();
        }
    }
    //endregion

    //region Lifecycle Events
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        // settings not pulled on Calculate btn click
        getAllNumericSettings();
        // RadioGroup Binding Adapter doesn't fire child listeners as to avoid unintended behavior
        // on orientation change, etc. Listeners only fired on actual presses, so set units here.
        setUnits();
    }

    private void getAllNumericSettings() {
        if (mRepo != null) {
            mRepo.getAllNumericSettings(mApplicationContext, this);
        }
    }

    private void setUnits() {
        int unit = getUnit();
        if (unit == Constants.METRIC) {
            setUiDataToMetric();
        } else {
            setUiDataToStandard();
        }
    }
    //endregion
}
