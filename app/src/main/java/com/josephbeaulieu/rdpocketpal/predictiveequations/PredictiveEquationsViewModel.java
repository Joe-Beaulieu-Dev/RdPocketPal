package com.josephbeaulieu.rdpocketpal.predictiveequations;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Toast;

import com.josephbeaulieu.rdpocketpal.R;
import com.josephbeaulieu.rdpocketpal.model.CoroutineCallbackListener;
import com.josephbeaulieu.rdpocketpal.model.PreferenceRepository;
import com.josephbeaulieu.rdpocketpal.model.QueryResult;
import com.josephbeaulieu.rdpocketpal.model.UserPreferences;
import com.josephbeaulieu.rdpocketpal.util.CalculationUtil;
import com.josephbeaulieu.rdpocketpal.util.FieldErrorPair;
import com.josephbeaulieu.rdpocketpal.util.NumberUtil;
import com.josephbeaulieu.rdpocketpal.util.Sex;
import com.josephbeaulieu.rdpocketpal.util.UiUtil;
import com.josephbeaulieu.rdpocketpal.util.Unit;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.SavedStateHandle;

public class PredictiveEquationsViewModel extends AndroidViewModel implements
        CoroutineCallbackListener, LifecycleObserver {
    private static final String LOG_TAG = "PredictiveEqViewModel";
    // this is fine because we're storing the application context here
    @SuppressLint("StaticFieldLeak")
    private Context mApplicationContext;
    private SavedStateHandle mState;
    private UserPreferences mPrefs;

    //region LiveData
    // User input LiveData
    public MutableLiveData<String> mSelectedEquation = new MutableLiveData<>();
    public MutableLiveData<String> mSelectedSex = new MutableLiveData<>();
    public MutableLiveData<String> mSelectedUnit = new MutableLiveData<>();
    public MutableLiveData<String> mWeight = new MutableLiveData<>();
    public MutableLiveData<String> mHeight = new MutableLiveData<>();
    public MutableLiveData<String> mAge = new MutableLiveData<>();
    public MutableLiveData<String> mTmax = new MutableLiveData<>();
    public MutableLiveData<String> mHeartRate = new MutableLiveData<>();
    public MutableLiveData<String> mVe = new MutableLiveData<>();
    public MutableLiveData<String> mActivityFactorMin = new MutableLiveData<>();
    public MutableLiveData<String> mActivityFactorMax = new MutableLiveData<>();
    public MutableLiveData<String> mBmr = new MutableLiveData<>();
    public MutableLiveData<String> mCalorieMin = new MutableLiveData<>();
    public MutableLiveData<String> mCalorieMax = new MutableLiveData<>();

    // Error messages
    public MutableLiveData<String> mWeightErrorMsg;
    public MutableLiveData<String> mHeightErrorMsg;
    public MutableLiveData<String> mAgeErrorMsg;
    public MutableLiveData<String> mTmaxErrorMsg;
    public MutableLiveData<String> mHeartRateErrorMsg;
    public MutableLiveData<String> mVeErrorMsg;
    public MutableLiveData<String> mActivityFactorMinErrorMsg;
    public MutableLiveData<String> mActivityFactorMaxErrorMsg;

    // Unit labels
    public MutableLiveData<String> mWeightUnitLabel = new MutableLiveData<>();
    public MutableLiveData<String> mHeightUnitLabel = new MutableLiveData<>();
    public MutableLiveData<String> mTmaxUnitLabel = new MutableLiveData<>();
    //endregion

    //region SavedState data
    // Data
    private MutableLiveData<String> mSelectedSexOldValue = new MutableLiveData<>();
    private MutableLiveData<String> mSelectedUnitOldValue = new MutableLiveData<>();

    // Keys
    private static final String SELECTED_SEX_OLD_VALUE_KEY = "selectedSexOldValue";
    private static final String SELECTED_UNIT_OLD_VALUE_KEY = "selectedUnitOldValue";
    private static final String WEIGHT_ERROR_MSG_KEY = "weightErrorMsgKey";
    private static final String HEIGHT_ERROR_MSG_KEY = "heightErrorMsgKey";
    private static final String AGE_ERROR_MSG_KEY = "ageErrorMsgKey";
    private static final String TMAX_ERROR_MSG_KEY = "tmaxErrorMsgKey";
    private static final String HEART_RATE_ERROR_MSG_KEY = "heartRateErrorMsgKey";
    private static final String VE_ERROR_MSG_KEY = "veErrorMsgKey";
    private static final String ACTIVITY_FACTOR_MIN_ERROR_MSG_KEY = "activityFactorMinErrorMsgKey";
    private static final String ACTIVITY_FACTOR_MAX_ERROR_MSG_KEY = "activityFactorMaxErrorMsgKey";
    //endregion

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MIFFLIN, BENEDICT, PENN2003B, PENN2010, BRANDI})
    public @interface Equations{}
    public static final int MIFFLIN = 0;
    public static final int BENEDICT = 1;
    public static final int PENN2003B = 2;
    public static final int PENN2010 = 3;
    public static final int BRANDI = 4;

    public PredictiveEquationsViewModel(@NonNull Application application, SavedStateHandle state) {
        super(application);
        mApplicationContext = application.getApplicationContext();
        mState = state;

        // restore data not persisted through system initiated process death
        restoreState();
    }

    //region Button listeners
    public void onClearClicked() {
        clearAllFields();
    }

    public void onCalculateClicked() {
        PreferenceRepository repo = new PreferenceRepository();
        repo.getAllNumericSettings(mApplicationContext, this);
    }

    private void calculate() {
        // Log field values. Disabled for release
//        logFields();

        try {
            // validate in case user never inputs into fields
            if (validateAllNecessaryFields(getEquationSelection())) {
                // perform calculations
                double bmr = calculateBmr(getEquationSelection());
                double calorieMin = calculateCalorieMin(bmr);
                double calorieMax = calculateCalorieMax(bmr);

                // set values to live data
                mBmr.setValue(
                        NumberUtil.roundOrTruncate(mApplicationContext, mPrefs, bmr));
                mCalorieMin.setValue(
                        NumberUtil.roundOrTruncate(mApplicationContext, mPrefs, calorieMin));
                mCalorieMax.setValue(
                        NumberUtil.roundOrTruncate(mApplicationContext, mPrefs, calorieMax));
            } else {
                throw new ValidationException("Necessary fields not valid");
            }
        } catch (ValidationException | NumberFormatException e) {
            e.printStackTrace();
            // fields not valid, display Toast
            UiUtil.showToast(mApplicationContext
                    , R.string.toast_invalid_fields, Toast.LENGTH_SHORT);
        }
    }

    public void onSexRadioBtnClicked(RadioButton btn) {
        clearOutputsAndToastIfNecessary(btn
                , mSelectedSexOldValue
                , R.string.toast_results_cleared_sex_change);
    }

    public void onUnitRadioBtnClicked(RadioButton btn) {
        clearOutputsAndToastIfNecessary(btn
                , mSelectedUnitOldValue
                , R.string.toast_results_cleared_unit_change);
    }
    //endregion

    //region Calculation methods
    private double calculateBmr(@Equations int equation) throws ValidationException {
        Unit unit = getUnitSelection();
        Sex sex = getSexSelection();

        // perform bmr calculation
        switch (equation) {
            case MIFFLIN:
                return calculateMifflin(unit, sex);
            case BENEDICT:
                return calculateBenedict(unit, sex);
            case PENN2003B:
                return calculatePennState2003b(unit);
            case PENN2010:
                return calculatePennState2010(unit);
            case BRANDI:
                return calculateBrandi();
            default:
                throw new ValidationException("Equation selection not valid");
        }
    }

    private double calculateCalorieMin(double bmr) throws NumberFormatException {
        return CalculationUtil.calculateCalorieMin(bmr, NumberUtil.parseDouble(mActivityFactorMin));
    }

    private double calculateCalorieMax(double bmr) throws NumberFormatException {
        return CalculationUtil.calculateCalorieMax(bmr, NumberUtil.parseDouble(mActivityFactorMax));
    }

    private double calculateMifflin(Unit unit, Sex sex) throws NumberFormatException {
        return CalculationUtil.calculateBmrMifflin(unit
                , sex
                , NumberUtil.parseDouble(mWeight)
                , NumberUtil.parseDouble(mHeight)
                , NumberUtil.parseInt(mAge)
        );
    }

    private double calculateBenedict(Unit unit, Sex sex) throws NumberFormatException {
        return CalculationUtil.calculateBmrBenedict(unit
                , sex
                , NumberUtil.parseDouble(mWeight)
                , NumberUtil.parseDouble(mHeight)
                , NumberUtil.parseInt(mAge)
        );
    }

    private double calculatePennState2003b(Unit unit) throws ValidationException, NumberFormatException {
        double bmrMifflin = calculateBmr(MIFFLIN);

        return CalculationUtil.calculateBmrPennState2003b(unit
                , bmrMifflin
                , NumberUtil.parseDouble(mTmax)
                , NumberUtil.parseDouble(mVe)
        );
    }

    private double calculatePennState2010(Unit unit) throws ValidationException, NumberFormatException {
        double bmrMifflin = calculateBmr(MIFFLIN);

        return CalculationUtil.calculateBmrPennState2010(unit
                , bmrMifflin
                , NumberUtil.parseDouble(mTmax)
                , NumberUtil.parseDouble(mVe)
        );
    }

    private double calculateBrandi() throws ValidationException, NumberFormatException {
        double bmrBenedict = calculateBmr(BENEDICT);

        return CalculationUtil.calculateBmrBrandi(bmrBenedict
                , NumberUtil.parseDouble(mHeartRate)
                , NumberUtil.parseDouble(mVe)
        );
    }
    //endregion

    //region Data gathering
    private int getEquationSelection() throws ValidationException {
        String selection = mSelectedEquation.getValue();

        if (selection == null) {
            throw new NullPointerException();
        }

        // compare selection String to String Resource currently being
        // used in order to decide which equation we are calculating
        if (selection.equals(mApplicationContext.getResources().getString(R.string.mifflin_st_jeor))) {
            return MIFFLIN;
        } else if (selection.equals(mApplicationContext.getResources().getString(R.string.harris_benedict))) {
            return BENEDICT;
        } else if (selection.equals(mApplicationContext.getResources().getString(R.string.penn_state_2003b))) {
            return PENN2003B;
        } else if (selection.equals(mApplicationContext.getResources().getString(R.string.penn_state_2010))) {
            return PENN2010;
        } else if (selection.equals(mApplicationContext.getResources().getString(R.string.brandi))){
            return BRANDI;
        } else {
            throw new ValidationException("Equation selection not valid");
        }
    }

    private Unit getUnitSelection() throws ValidationException {
        String selection = mSelectedUnit.getValue();

        if (selection == null) {
            throw new NullPointerException();
        }

        // compare selection String to String Resource currently being
        // used in order to decide which units are being used
        if (selection.equals(mApplicationContext.getResources().getString(R.string.text_metric))) {
            return Unit.METRIC;
        } else if (selection.equals(mApplicationContext.getResources().getString(R.string.text_standard))) {
            return Unit.STANDARD;
        } else {
            throw new ValidationException("Unit selection not valid");
        }
    }

    private Sex getSexSelection() throws ValidationException {
        String selection = mSelectedSex.getValue();

        if (selection == null) {
            throw new NullPointerException();
        }

        // compare selection String to String Resource currently being
        // used in order to decide which sex is being used
        if (selection.equals(mApplicationContext.getResources().getString(R.string.text_male))) {
            return Sex.MALE;
        } else if (selection.equals(mApplicationContext.getResources().getString(R.string.text_female))) {
            return Sex.FEMALE;
        } else {
            throw new ValidationException("Sex selection not valid");
        }
    }
    //endregion

    //region UI data manipulation
    private void setUiDataToMetric() {
        mWeightUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_kg));
        mHeightUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_cm));
        mTmaxUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_celsius));
    }

    private void setUiDataToStandard() {
        mWeightUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_lb));
        mHeightUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_in));
        mTmaxUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_fahrenheit));
    }
    //endregion

    //region Validation methods
    private boolean validateAllNecessaryFields(@Equations int equation) {
        boolean isValid = true;

        if (!areBmrFieldsValid(equation)) {
            isValid = false;
        }
        if (!isActivityLevelMinValid()) {
            isValid = false;
        }
        if (!isActivityLevelMaxValid()) {
            isValid = false;
        }

        return isValid;
    }

    private boolean areBmrFieldsValid(@Equations int equation) {
        switch (equation) {
            case MIFFLIN:
            case BENEDICT:
                return validateMifflinOrBenedictFields();
            case PENN2003B:
            case PENN2010:
                return validatePennStateFields();
            case BRANDI:
                return validateBrandiFields();
            default:
                return false;
        }
    }

    private boolean validateMifflinOrBenedictFields() {
        return UiUtil.validateFieldsAndSetErrors(mApplicationContext
                , new FieldErrorPair(mWeight, mWeightErrorMsg)
                , new FieldErrorPair(mHeight, mHeightErrorMsg)
                , new FieldErrorPair(mAge, mAgeErrorMsg)
        );
    }

    private boolean validatePennStateFields() {
        return UiUtil.validateFieldsAndSetErrors(mApplicationContext
                , new FieldErrorPair(mWeight, mWeightErrorMsg)
                , new FieldErrorPair(mHeight, mHeightErrorMsg)
                , new FieldErrorPair(mAge, mAgeErrorMsg)
                , new FieldErrorPair(mTmax, mTmaxErrorMsg)
                , new FieldErrorPair(mVe, mVeErrorMsg)
        );
    }

    private boolean validateBrandiFields() {
        return UiUtil.validateFieldsAndSetErrors(mApplicationContext
                , new FieldErrorPair(mWeight, mWeightErrorMsg)
                , new FieldErrorPair(mHeight, mHeightErrorMsg)
                , new FieldErrorPair(mAge, mAgeErrorMsg)
                , new FieldErrorPair(mHeartRate, mHeartRateErrorMsg)
                , new FieldErrorPair(mVe, mVeErrorMsg)
        );
    }

    private boolean isActivityLevelMinValid() {
        return UiUtil.validateFieldsAndSetErrors(mApplicationContext
                , new FieldErrorPair(mActivityFactorMin, mActivityFactorMinErrorMsg)
        );
    }

    private boolean isActivityLevelMaxValid() {
        return UiUtil.validateFieldsAndSetErrors(mApplicationContext
                , new FieldErrorPair(mActivityFactorMax, mActivityFactorMaxErrorMsg)
        );
    }
    //endregion

    //region Clear fields
    private void clearAllFields() {
        UiUtil.clearFields(mWeight
                , mHeight
                , mAge
                , mTmax
                , mVe
                , mHeartRate
                , mActivityFactorMin
                , mActivityFactorMax
                , mBmr
                , mCalorieMin
                , mCalorieMax
        );
    }

    public void clearOutputDataFromActivity() {
        if (clearOutput()) {
            UiUtil.showToast(mApplicationContext
                    , R.string.toast_results_cleared_equation_change, Toast.LENGTH_LONG);
        }
    }

    private boolean clearOutput() {
        return UiUtil.clearFields(mBmr, mCalorieMin, mCalorieMax);
    }

    private void clearOutputsAndToastIfNecessary(RadioButton btn
            , MutableLiveData<String> oldValue
            , @StringRes int errorToast) {
        // just to make things easier to understand
        boolean isInitialSelectionAndNotDefault =
                oldValue.getValue() == null && !UiUtil.isDefaultRadioBtnChecked(btn);
        boolean isNotInitialSelectionAndDiffThanLast =
                oldValue.getValue() != null
                        && !btn.getText().toString().equals(oldValue.getValue());

        if (isInitialSelectionAndNotDefault || isNotInitialSelectionAndDiffThanLast) {
            // if we're dealing with the Unit RadioButton, set the units
            if (oldValue == mSelectedUnitOldValue) {
                setUnits();
            }
            // clear output fields and show Toast as to why
            if (clearOutput()) {
                UiUtil.showToast(mApplicationContext, errorToast, Toast.LENGTH_LONG);
            }
        }

        oldValue.setValue(btn.getText().toString());
    }
    //endregion

    //region SavedState methods
    void saveState() {
        mState.set(SELECTED_SEX_OLD_VALUE_KEY, mSelectedSexOldValue.getValue());
        mState.set(SELECTED_UNIT_OLD_VALUE_KEY, mSelectedUnitOldValue.getValue());
        mState.set(WEIGHT_ERROR_MSG_KEY, mWeightErrorMsg.getValue());
        mState.set(HEIGHT_ERROR_MSG_KEY, mHeightErrorMsg.getValue());
        mState.set(AGE_ERROR_MSG_KEY, mAgeErrorMsg.getValue());
        mState.set(TMAX_ERROR_MSG_KEY, mTmaxErrorMsg.getValue());
        mState.set(HEART_RATE_ERROR_MSG_KEY, mHeartRateErrorMsg.getValue());
        mState.set(VE_ERROR_MSG_KEY, mVeErrorMsg.getValue());
        mState.set(ACTIVITY_FACTOR_MIN_ERROR_MSG_KEY, mActivityFactorMinErrorMsg.getValue());
        mState.set(ACTIVITY_FACTOR_MAX_ERROR_MSG_KEY, mActivityFactorMaxErrorMsg.getValue());
    }

    private void restoreState() {
        mSelectedSexOldValue = mState.getLiveData(SELECTED_SEX_OLD_VALUE_KEY);
        mSelectedUnitOldValue = mState.getLiveData(SELECTED_UNIT_OLD_VALUE_KEY);
        // restore all error message LiveData
        mWeightErrorMsg = mState.getLiveData(WEIGHT_ERROR_MSG_KEY);
        mHeightErrorMsg = mState.getLiveData(HEIGHT_ERROR_MSG_KEY);
        mAgeErrorMsg = mState.getLiveData(AGE_ERROR_MSG_KEY);
        mTmaxErrorMsg = mState.getLiveData(TMAX_ERROR_MSG_KEY);
        mHeartRateErrorMsg = mState.getLiveData(HEART_RATE_ERROR_MSG_KEY);
        mVeErrorMsg = mState.getLiveData(VE_ERROR_MSG_KEY);
        mActivityFactorMinErrorMsg = mState.getLiveData(ACTIVITY_FACTOR_MIN_ERROR_MSG_KEY);
        mActivityFactorMaxErrorMsg = mState.getLiveData(ACTIVITY_FACTOR_MAX_ERROR_MSG_KEY);
    }
    //endregion

    //region View methods
    LiveData<String> getSelectedEquation() {
        return mSelectedEquation;
    }

    void clearTmaxError() {
        mTmaxErrorMsg.setValue(null);
    }

    void clearHeartRateError() {
        mHeartRateErrorMsg.setValue(null);
    }

    void clearVeError() {
        mVeErrorMsg.setValue(null);
    }
    //endregion

    //region Callbacks
    @Override
    public <T> void onCoroutineFinished(QueryResult<T> result) {
        if (result instanceof QueryResult.Success
                && ((QueryResult.Success<T>) result).getData() instanceof UserPreferences) {
            // get UserPreference object
            mPrefs = (UserPreferences) ((QueryResult.Success<T>) result).getData();
            // perform calculations
            calculate();
        } else if (result instanceof QueryResult.Failure) {
            // get and log exception
            Exception e = ((QueryResult.Failure) result).getException();
            if (e.getMessage() != null) {
                // disabled for release
//                Log.e(LOG_TAG, e.getMessage(), e);
            }

            // display feedback to user
            UiUtil.showToast(mApplicationContext
                    , R.string.toast_failed_to_access_settings, Toast.LENGTH_SHORT);
        }
    }
    //endregion

    //region LifeCycle Events
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        // RadioGroup Binding Adapter doesn't fire child listeners as to avoid unintended behavior
        // on orientation change, etc. Listeners only fired on actual presses, so set units here.
        setUnits();
    }

    private void setUnits() {
        try {
            Unit unit = getUnitSelection();
            if (unit == Unit.METRIC) {
                setUiDataToMetric();
            } else {
                setUiDataToStandard();
            }
        } catch (ValidationException e) {
            // units don't get displayed, but will never happen
            e.printStackTrace();
        }
    }
    //endregion

    //region Test against process death
    // couldn't find a way to test process death with Espresso, so just leaving this method here
    @SuppressLint("unused")
    private void checkPersistenceAfterSystemInitProcessDeath() {
        String input = "Eq: " + mSelectedEquation.getValue()
                + "\nSex: " + mSelectedSex.getValue()
                + "\nUnit: " + mSelectedUnit.getValue()
                + "\nWeight: " + mWeight.getValue()
                + "\nHeight: " + mHeight.getValue()
                + "\nAge: " + mAge.getValue()
                + "\nTmax: " + mTmax.getValue()
                + "\nHR: " + mHeartRate.getValue()
                + "\nVe: " + mVe.getValue()
                + "\nAF min: " + mActivityFactorMin.getValue()
                + "\nAF max: " + mActivityFactorMax.getValue();

        String output = "BMR: " + mBmr.getValue()
                + "\nCal min: " + mCalorieMin.getValue()
                + "\nCal max: " + mCalorieMax.getValue();

        String errors = "Weight Err: " + mWeightErrorMsg.getValue()
                + "\nHeight Err: " + mHeightErrorMsg.getValue()
                + "\nAge Err: " + mAgeErrorMsg.getValue()
                + "\nTmax Err: " + mTmaxErrorMsg.getValue()
                + "\nHR Err: " + mHeartRateErrorMsg.getValue()
                + "\nVe Err: " + mVeErrorMsg.getValue()
                + "\nAF min Err: " + mActivityFactorMinErrorMsg.getValue()
                + "\nAF max Err: " + mActivityFactorMaxErrorMsg.getValue();

        UiUtil.showToast(mApplicationContext
                , input + "\n" + output + "\n" + errors
                , Toast.LENGTH_LONG);
    }
    //endregion

    private void logFields() {
        Log.d(LOG_TAG, "Selected Equation: " + mSelectedEquation.getValue());
        Log.d(LOG_TAG, "Selected Sex: " + mSelectedSex.getValue());
        Log.d(LOG_TAG, "Selected Unit: " + mSelectedUnit.getValue());
        Log.d(LOG_TAG, "Weight: " + mWeight.getValue());
        Log.d(LOG_TAG, "Height: " + mHeight.getValue());
        Log.d(LOG_TAG, "Age: " + mAge.getValue());
        Log.d(LOG_TAG, "Tmax: " + mTmax.getValue());
        Log.d(LOG_TAG, "Heart rate: " + mHeartRate.getValue());
        Log.d(LOG_TAG, "Ve: " + mVe.getValue());
        Log.d(LOG_TAG, "Activity factor min: " + mActivityFactorMin.getValue());
        Log.d(LOG_TAG, "Activity factor Max: " + mActivityFactorMax.getValue());
        Log.d(LOG_TAG, "BMR: " + mBmr.getValue());
        Log.d(LOG_TAG, "Calorie min: " + mCalorieMin.getValue());
        Log.d(LOG_TAG, "Calorie max: " + mCalorieMax.getValue());
    }
}
