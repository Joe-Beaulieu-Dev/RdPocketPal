package com.example.rdpocketpal2.quickmethod;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

public class QuickMethodViewModel extends AndroidViewModel implements CoroutineCallbackListener
        , SharedPreferences.OnSharedPreferenceChangeListener {
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

    //region SavedState keys
    // error message keys
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

        // initialize repo and get settings
        mRepo = new PreferenceRepository();
        getAllNumericSettings();

        // restore data not persisted through system initiated process death
        restoreState();
    }

    //region Button Listeners
    public void onUnitRadioBtnClicked(RadioButton btn) {
        if (btn.getText().toString().equals(mApplicationContext.getResources().getString(R.string.text_metric))) {
            setUiDataToMetric();

            // clear results because once the unit labels change, the results will no longer make
            // sense relative to the user input. If fields were cleared, show Toast
            if (clearAllResults()) {
                // show toast for result clearing
                showToast(R.string.toast_results_cleared_unit_change, Toast.LENGTH_LONG);
            }
        } else if (btn.getText().toString().equals(mApplicationContext.getResources().getString(R.string.text_standard))) {
            setUiDataToStandard();

            // clear results because once the unit labels change, the results will no longer make
            // sense relative to the user input. If fields were cleared, show Toast
            if (clearAllResults()) {
                // show toast for result clearing
                showToast(R.string.toast_results_cleared_unit_change, Toast.LENGTH_LONG);
            }
        }
    }

    public void onCalorieClearClicked() {
        // clear all Calorie fields
        clearCalorieInput();
        clearCalorieResults();
    }

    public void onCalorieCalculateClicked() {
        // validate required fields, then set daily values
        if (validateFields(CALORIE)) {
            // calculate and set daily values
            calculate(CALORIE);
        } else {
            // display error Toast
            showToast(R.string.toast_invalid_fields, Toast.LENGTH_SHORT);
        }
    }

    public void onProteinClearClicked() {
        // clear all Protein fields
        clearProteinInput();
        clearProteinResults();
    }

    public void onProteinCalculateClicked() {
        // validate required fields, then set daily values
        if (validateFields(PROTEIN)) {
            // calculate and set daily values
            calculate(PROTEIN);
        } else {
            // display error Toast
            showToast(R.string.toast_invalid_fields, Toast.LENGTH_SHORT);
        }
    }

    public void onFluidClearClicked() {
        // clear all Fluid fields
        clearFluidInput();
        clearFluidResults();
    }

    public void onFluidCalculateClicked() {
        // validate required fields, then set daily values
        if (validateFields(FLUID)) {
            // calculate and set daily values
            calculate(FLUID);
        } else {
            // display error Toast
            showToast(R.string.toast_invalid_fields, Toast.LENGTH_SHORT);
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
        return validateFieldAndSetError(mWeight, mWeightErrorMsg);
    }

    private boolean areCalorieFieldsValid() {
        boolean isValid = true;

        // validate all required fields and set flag
        if (!validateFieldAndSetError(mKcalPerKgMin, mKcalPerKgMinErrorMsg)) {
            isValid = false;
        }
        if (!validateFieldAndSetError(mKcalPerKgMax, mKcalPerKgMaxErrorMsg)) {
            isValid = false;
        }

        return isValid;
    }

    private boolean areProteinFieldsValid() {
        boolean isValid = true;

        // validate all required fields and set flag
        if (!validateFieldAndSetError(mGramsPerKgMin, mGramsPerKgMinErrorMsg)) {
            isValid = false;
        }
        if (!validateFieldAndSetError(mGramsPerKgMax, mGramsPerKgMaxErrorMsg)) {
            isValid = false;
        }

        return isValid;
    }

    private boolean areFluidFieldsValid() {
        boolean isValid = true;

        // validate all required fields and set flag
        if (!validateFieldAndSetError(mMlPerKgMin, mMlPerKgMinErrorMsg)) {
            isValid = false;
        }
        if (!validateFieldAndSetError(mMlPerKgMax, mMlPerKgMaxErrorMsg)) {
            isValid = false;
        }

        return isValid;
    }

    private boolean validateFieldAndSetError(MutableLiveData<String> field,
                                             MutableLiveData<String> fieldError) {
        if (!NumberUtil.isDouble(field)) {
            setEnterNumberError(fieldError);
            return false;
        }
        return true;
    }

    private void setEnterNumberError(MutableLiveData<String> fieldData) {
        fieldData.setValue(mApplicationContext.getResources().getString(R.string.error_enter_a_number));
    }
    //endregion

    //region Helper Methods
    private void getAllNumericSettings() {
        if (mRepo != null) {
            mRepo.getAllNumericSettings(mApplicationContext, this);
        }
    }

    private int getUnit() throws FatalCalculationException {
        String unit = mUnitSelection.getValue();

        if (unit == null) {
            throw new NullPointerException();
        }

        // compare selection String to String Resource currently being
        // used in order to decide which units are being used
        if (unit.equals(mApplicationContext.getResources().getString(R.string.text_metric))) {
            return ConstantsKotlin.METRIC;
        } else if (unit.equals(mApplicationContext.getResources().getString(R.string.text_standard))) {
            return ConstantsKotlin.STANDARD;
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
        clearField(mKcalPerKgMin);
        clearField(mKcalPerKgMax);
    }

    private void clearProteinInput() {
        clearField(mGramsPerKgMin);
        clearField(mGramsPerKgMax);
    }

    private void clearFluidInput() {
        clearField(mMlPerKgMin);
        clearField(mMlPerKgMax);
    }

    private boolean clearAllResults() {
        boolean allCleared = true;

        // clear all fields and set flag
        if (!clearCalorieResults()) {
            allCleared = false;
        }
        if (!clearProteinResults()) {
            allCleared = false;
        }
        if (!clearFluidResults()) {
            allCleared = false;
        }

        return allCleared;
    }

    private boolean clearCalorieResults() {
        boolean allCleared = true;

        // clear all fields and set flag
        if (!clearField(mKcalPerDayMin)) {
            allCleared = false;
        }
        if (!clearField(mKcalPerDayMax)) {
            allCleared = false;
        }

        return allCleared;
    }

    private boolean clearProteinResults() {
        boolean allCleared = true;

        // clear all fields and set flag
        if (!clearField(mGramsPerDayMin)) {
            allCleared = false;
        }
        if (!clearField(mGramsPerDayMax)) {
            allCleared = false;
        }

        return allCleared;
    }

    private boolean clearFluidResults() {
        boolean allCleared = true;

        // clear all fields and set flag
        if (!clearField(mMlPerDayMin)) {
            allCleared = false;
        }
        if (!clearField(mMlPerDayMax)) {
            allCleared = false;
        }

        return allCleared;
    }

    private boolean clearField(MutableLiveData<String> fieldData) {
        if (fieldData.getValue() != null && !fieldData.getValue().equals("")) {
            fieldData.setValue("");
            return true;
        }
        return false;
    }

    private void showToast(int stringId, int duration) {
        // can't reference @View.DURATION in the parameters so have to do this
        if (duration != Toast.LENGTH_SHORT && duration != Toast.LENGTH_LONG) {
            return;
        }

        // show Toast
        Toast.makeText(mApplicationContext
                , mApplicationContext.getResources().getString(stringId)
                , duration)
                .show();
    }
    //endregion

    //region SavedState methods
    void saveState() {
        mState.set(WEIGHT_ERROR_MSG_KEY, mWeightErrorMsg.getValue());
        mState.set(KCAL_PER_KG_MIN_ERROR_MSG_KEY, mKcalPerKgMinErrorMsg.getValue());
        mState.set(KCAL_PER_KG_MAX_ERROR_MSG_KEY, mKcalPerKgMaxErrorMsg.getValue());
        mState.set(GRAMS_PER_KG_MIN_ERROR_MSG_KEY, mGramsPerKgMinErrorMsg.getValue());
        mState.set(GRAMS_PER_KG_MAX_ERROR_MSG_KEY, mGramsPerKgMaxErrorMsg.getValue());
        mState.set(ML_PER_KG_MIN_ERROR_MSG_KEY, mMlPerKgMinErrorMsg.getValue());
        mState.set(ML_PER_KG_MAX_ERROR_MSG_KEY, mMlPerKgMaxErrorMsg.getValue());
    }

    private void restoreState() {
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(mApplicationContext.getString(R.string.key_decimal_reduction_method))
                || key.equals(mApplicationContext.getString(R.string.key_numeric_scale))) {
            getAllNumericSettings();
        }
    }
    //endregion
}
