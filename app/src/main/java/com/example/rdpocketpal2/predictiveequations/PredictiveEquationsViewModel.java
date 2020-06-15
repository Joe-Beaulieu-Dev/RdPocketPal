package com.example.rdpocketpal2.predictiveequations;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.rdpocketpal2.R;
import com.example.rdpocketpal2.model.PreferenceRepository;
import com.example.rdpocketpal2.model.QueryResult;
import com.example.rdpocketpal2.model.UserPreferences;
import com.example.rdpocketpal2.util.CalculationUtil;
import com.example.rdpocketpal2.util.ConstantsKotlin;
import com.example.rdpocketpal2.util.CoroutineCallbackListener;
import com.example.rdpocketpal2.util.NumberUtil;
import com.example.rdpocketpal2.util.Sex;
import com.example.rdpocketpal2.util.Unit;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

public class PredictiveEquationsViewModel extends AndroidViewModel implements CoroutineCallbackListener {
    private static final String LOG_TAG = "PredictiveEqViewModel";

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

    // Hints
    public MutableLiveData<String> mWeightUnitLabel = new MutableLiveData<>();
    public MutableLiveData<String> mHeightUnitLabel = new MutableLiveData<>();
    public MutableLiveData<String> mAgeUnitLabel = new MutableLiveData<>();
    public MutableLiveData<String> mTmaxUnitLabel = new MutableLiveData<>();
    public MutableLiveData<String> mHeartRateUnitLabel = new MutableLiveData<>();
    public MutableLiveData<String> mVeUnitLabel = new MutableLiveData<>();
    //endregion

    //region LiveData SavedState Keys
    // Error message keys
    private static final String WEIGHT_ERROR_MSG_KEY = "weightErrorMsgKey";
    private static final String HEIGHT_ERROR_MSG_KEY = "heightErrorMsgKey";
    private static final String AGE_ERROR_MSG_KEY = "ageErrorMsgKey";
    private static final String TMAX_ERROR_MSG_KEY = "tmaxErrorMsgKey";
    private static final String HEART_RATE_ERROR_MSG_KEY = "heartRateErrorMsgKey";
    private static final String VE_ERROR_MSG_KEY = "veErrorMsgKey";
    private static final String ACTIVITY_FACTOR_MIN_ERROR_MSG_KEY = "activityFactorMinErrorMsgKey";
    private static final String ACTIVITY_FACTOR_MAX_ERROR_MSG_KEY = "activityFactorMaxErrorMsgKey";
    //endregion

    // this is fine because we're storing the application context here
    private Context mApplicationContext;
    private SavedStateHandle mState;
    private UserPreferences mPrefs;

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
        mWeight.setValue("");
        mHeight.setValue("");
        mAge.setValue("");
        mTmax.setValue("");
        mVe.setValue("");
        mActivityFactorMin.setValue("");
        mActivityFactorMax.setValue("");
        mBmr.setValue("");
        mCalorieMin.setValue("");
        mCalorieMax.setValue("");
    }

    public void onCalculateClicked() {
        PreferenceRepository repo = new PreferenceRepository();
        repo.getAllNumericSettings(mApplicationContext, this);
    }

    private void calculate() {
        // Log field values
        logFields();

        try {
            // even though fields have validation on focus change, still need to validate everything
            // when the calculation button is pressed in case the user never clicks on a field,
            // leaving it null, which would bypass the validation. This must all be done at once at
            // the start or else if bmr fields are invalid, activity factor fields won't get
            // validated when the calculate button is pressed
            if (validateAllNecessaryFields(getEquationSelection())) {
                // perform calculations
                double bmr = calculateBmr(getEquationSelection());
                double calorieMin = calculateCalorieMin(bmr);
                double calorieMax = calculateCalorieMax(bmr);

                // set values to live data
                mBmr.setValue(String.valueOf(bmr));
                mCalorieMin.setValue(String.valueOf(calorieMin));
                mCalorieMax.setValue(String.valueOf(calorieMax));
            } else {
                throw new ValidationException("Necessary fields not valid");
            }
        } catch (ValidationException | NumberFormatException e) {
            e.printStackTrace();
            // fields not valid, display Toast
            Toast.makeText(mApplicationContext,
                    mApplicationContext.getResources().getString(R.string.toast_invalid_fields),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onUnitRadioBtnClicked(RadioButton btn) {
        if (btn.getText().toString().equals(mApplicationContext.getResources().getString(R.string.text_metric))) {
            setUiDataToMetric();
        } else if (btn.getText().toString().equals(mApplicationContext.getResources().getString(R.string.text_standard))) {
            setUiDataToStandard();
        }
    }
    //endregion

    //region Calculation methods
    private double calculateBmr(@Equations int equation) throws ValidationException {
        int unit = getUnitSelection();
        int sex = getSexSelection();

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
                return calculateBrandi(unit);
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

    private double calculateMifflin(@Unit int unit, @Sex int sex) throws NumberFormatException {
        return CalculationUtil.calculateBmrMifflin(unit
                , sex
                , NumberUtil.parseDouble(mWeight)
                , NumberUtil.parseDouble(mHeight)
                , NumberUtil.parseInt(mAge)
        );
    }

    private double calculateBenedict(@Unit int unit, @Sex int sex) throws NumberFormatException {
        return CalculationUtil.calculateBmrBenedict(unit
                , sex
                , NumberUtil.parseDouble(mWeight)
                , NumberUtil.parseDouble(mHeight)
                , NumberUtil.parseInt(mAge)
        );
    }

    private double calculatePennState2003b(@Unit int unit) throws ValidationException, NumberFormatException {
        double bmrMifflin = calculateBmr(MIFFLIN);

        return CalculationUtil.calculateBmrPennState2003b(unit
                , bmrMifflin
                , NumberUtil.parseDouble(mTmax)
                , NumberUtil.parseDouble(mVe)
        );
    }

    private double calculatePennState2010(@Unit int unit) throws ValidationException, NumberFormatException {
        double bmrMifflin = calculateBmr(MIFFLIN);

        return CalculationUtil.calculateBmrPennState2010(unit
                , bmrMifflin
                , NumberUtil.parseDouble(mTmax)
                , NumberUtil.parseDouble(mVe)
        );
    }

    private double calculateBrandi(@Unit int unit) throws ValidationException, NumberFormatException {
        double bmrBenedict = calculateBmr(BENEDICT);

        return CalculationUtil.calculateBmrBrandi(unit
                , bmrBenedict
                , NumberUtil.parseDouble(mHeartRate)
                , NumberUtil.parseDouble(mVe)
        );
    }
    //endregion

    //region Helper methods
    private int getEquationSelection() throws ValidationException {
        String selection = mSelectedEquation.getValue();

        if (selection == null) {
            throw new NullPointerException();
        }

        // compare selection String to String Resource currently being
        // used in order to decide which equation we are calculating
        if (selection.equals(mApplicationContext.getResources().getString(R.string.mifflin_st_jear))) {
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

    private int getUnitSelection() throws ValidationException {
        String selection = mSelectedUnit.getValue();

        if (selection == null) {
            throw new NullPointerException();
        }

        // compare selection String to String Resource currently being
        // used in order to decide which units are being used
        if (selection.equals(mApplicationContext.getResources().getString(R.string.text_metric))) {
            return ConstantsKotlin.METRIC;
        } else if (selection.equals(mApplicationContext.getResources().getString(R.string.text_standard))) {
            return ConstantsKotlin.STANDARD;
        } else {
            throw new ValidationException("Unit selection not valid");
        }
    }

    private int getSexSelection() throws ValidationException {
        String selection = mSelectedSex.getValue();

        if (selection == null) {
            throw new NullPointerException();
        }

        // compare selection String to String Resource currently being
        // used in order to decide which sex is being used
        if (selection.equals(mApplicationContext.getResources().getString(R.string.text_male))) {
            return ConstantsKotlin.MALE;
        } else if (selection.equals(mApplicationContext.getResources().getString(R.string.text_female))) {
            return ConstantsKotlin.FEMALE;
        } else {
            throw new ValidationException("Sex selection not valid");
        }
    }

    private void setUiDataToMetric() {
        mWeightUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_kg));
        mHeightUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_cm));
        mAgeUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_yr));
        mTmaxUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_celsius));
        mHeartRateUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_beats_per_minute));
        mVeUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_liters_per_minute));
    }

    private void setUiDataToStandard() {
        mWeightUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_lb));
        mHeightUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_in));
        mAgeUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_yr));
        mTmaxUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_fahrenheit));
        mHeartRateUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_beats_per_minute));
        mVeUnitLabel.setValue(mApplicationContext.getResources().getString(R.string.text_gallons_per_minute));
    }

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

    // chose not to do inline validation for null/empty checks on EditTexts as it would miss fields
    // is the user never clicked them. Therefore, just doing them when the user presses the
    // Calculate button
    //TODO cases like PENN2003b stop validation after first method fails, which means no error
    // messages on subsequent fields
    private boolean areBmrFieldsValid(@Equations int equation) {
        switch (equation) {
            case MIFFLIN:
            case BENEDICT:
                return validateWeightHeightAge();
            case PENN2003B:
            case PENN2010:
                return validateWeightHeightAge() && isTmaxValid() && isVeValid();
            case BRANDI:
                return validateWeightHeightAge() && isHeartRateValid() && isVeValid();
            default:
                return false;
        }
    }

    private boolean validateWeightHeightAge() {
        boolean isValid = true;

        if (!NumberUtil.isDouble(mWeight)) {
            setEnterNumberError(mWeightErrorMsg);
            isValid = false;
        }
        if (!NumberUtil.isDouble(mHeight)) {
            setEnterNumberError(mHeightErrorMsg);
            isValid = false;
        }
        if (!NumberUtil.isDouble(mAge)) {
            setEnterNumberError(mAgeErrorMsg);
            isValid = false;
        }

        return isValid;
    }

    private boolean isTmaxValid() {
        if (!NumberUtil.isDouble(mTmax)) {
            setEnterNumberError(mTmaxErrorMsg);
            return false;
        }
        return true;
    }

    private boolean isHeartRateValid() {
        if (!NumberUtil.isDouble(mHeartRate)) {
            setEnterNumberError(mHeartRateErrorMsg);
            return false;
        }
        return true;
    }

    private boolean isVeValid() {
        if (!NumberUtil.isDouble(mVe)) {
            setEnterNumberError(mVeErrorMsg);
            return false;
        }
        return true;
    }

    private boolean isActivityLevelMinValid() {
        if (!NumberUtil.isDouble(mActivityFactorMin)) {
            setEnterNumberError(mActivityFactorMinErrorMsg);
            return false;
        }
        return true;
    }

    private boolean isActivityLevelMaxValid() {
        if (!NumberUtil.isDouble(mActivityFactorMax)) {
            setEnterNumberError(mActivityFactorMaxErrorMsg);
            return false;
        }
        return true;
    }

    private void setEnterNumberError(MutableLiveData<String> fieldData) {
        fieldData.setValue(mApplicationContext.getResources().getString(R.string.error_enter_a_number));
    }
    //endregion

    //region SavedState methods
    void saveState() {
        // unlike other values in the UI, error messages do not get persisted upon system initiated
        // process death, so they must be saved to the SavedStateHandle
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

    MutableLiveData<String> getSelectedEquation() {
        return mSelectedEquation;
    }

    @Override
    public <T> void onCoroutineFinished(QueryResult<T> result) {
        if (result instanceof QueryResult.Success
                && ((QueryResult.Success<T>) result).getData() instanceof UserPreferences) {
            // get UserPreference obj and pull values from it
            mPrefs = (UserPreferences) ((QueryResult.Success<T>) result).getData();
            String roundingMethod = mPrefs.getRounding();
            Integer scale = mPrefs.getNumericScale();

            // test coroutine usage
            Toast.makeText(mApplicationContext
                    , "Rounding method: " + roundingMethod
                    + "\nNumeric scale: " + scale
                    , Toast.LENGTH_SHORT).show();

        } else if (result instanceof QueryResult.Failure) {
            // get and log exception
            Exception e = ((QueryResult.Failure) result).getException();
            if (e.getMessage() != null) {
                Log.e(LOG_TAG, e.getMessage());
            }

            // display feedback to user
            Toast.makeText(mApplicationContext
                    , "Failed to access user settings"
                    , Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
