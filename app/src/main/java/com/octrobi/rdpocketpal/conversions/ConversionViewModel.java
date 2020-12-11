package com.octrobi.rdpocketpal.conversions;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.octrobi.rdpocketpal.R;
import com.octrobi.rdpocketpal.model.UserPreferences;
import com.octrobi.rdpocketpal.quickmethod.FatalCalculationException;
import com.octrobi.rdpocketpal.util.ConversionUtil;
import com.octrobi.rdpocketpal.util.FieldErrorPair;
import com.octrobi.rdpocketpal.util.NumberUtil;
import com.octrobi.rdpocketpal.util.UiUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

public class ConversionViewModel extends AndroidViewModel {
    //region LiveData
    // UI data
    public MutableLiveData<String> mConversionType = new MutableLiveData<>();
    public MutableLiveData<String> mFieldLeft = new MutableLiveData<>();
    public MutableLiveData<String> mFieldRight = new MutableLiveData<>();

    // Unit labels
    public MutableLiveData<String> mFieldLeftLabel = new MutableLiveData<>();
    public MutableLiveData<String> mFieldRightLabel = new MutableLiveData<>();

    // Error messages
    public MutableLiveData<String> mFieldLeftErrorMsg = new MutableLiveData<>();
    public MutableLiveData<String> mFieldRightErrorMsg = new MutableLiveData<>();
    //endregion

    //region SavedState keys
    private static final String FIELD_LEFT_ERROR_MSG_KEY = "fieldLeftErrorMsgKey";
    private static final String FIELD_RIGHT_ERROR_MSG_KEY = "fieldRightErrorMsgKey";
    //endregion

    // okay since when the app dies, so does the ViewModel
    @SuppressLint("StaticFieldLeak")
    private Context mApplicationContext;
    private SavedStateHandle mState;
    private UserPreferences mPrefs;

    // Input/output fields
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FIELD_LEFT, FIELD_RIGHT})
    private @interface Fields {}
    private static final int FIELD_LEFT = 0;
    private static final int FIELD_RIGHT = 1;

    // Conversion types
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({IN_TO_CM, CM_TO_IN, LB_TO_KG, KG_TO_LB})
    private @interface Conversion {}
    private static final int IN_TO_CM = 0;
    private static final int CM_TO_IN = 1;
    private static final int LB_TO_KG = 2;
    private static final int KG_TO_LB = 3;

    public ConversionViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);

        mApplicationContext = application.getApplicationContext();
        mState = handle;

        // create prefs object with hard coded settings
        createDefaultPrefsObject();

        // restore state in the case of system initiated process death
        restoreState();
    }

    //region Button listeners
    public void onClearClicked() {
        clearAllFields();
    }

    public void onLeftFieldChanged(EditText editText) {
        // check for focus to break loops
        if (editText.hasFocus()) {
            if (validateInputDataAndToast(FIELD_LEFT)) {
                try {
                    convertAndSetResult(getConversionType(FIELD_LEFT), FIELD_LEFT);
                } catch (NullPointerException | FatalCalculationException e) {
                    // this shouldn't happen unless the Spinners' Adapters don't load their data,
                    // or their data/the code is broken. The User will just have to refresh here
                    UiUtil.showToast(mApplicationContext
                            , "Conversion error. Please reload screen."
                            , Toast.LENGTH_SHORT);
                }
            }
        }
    }

    public void onRightFieldChanged(EditText editText) {
        // check for focus to break loops
        if (editText.hasFocus()) {
            if (validateInputDataAndToast(FIELD_RIGHT)) {
                try {
                    convertAndSetResult(getConversionType(FIELD_RIGHT), FIELD_RIGHT);
                } catch (NullPointerException | FatalCalculationException e) {
                    // this shouldn't happen unless the Spinners' Adapters don't load their data,
                    // or their data/the code is broken. The User will just have to refresh here
                    UiUtil.showToast(mApplicationContext
                            , "Conversion error. Please reload screen."
                            , Toast.LENGTH_SHORT);
                }
            }
        }
    }
    //endregion

    //region Calculation methods
    private void convertAndSetResult(@Conversion int conversion, @Fields int inputField) {
        // set value of output field
        switch (inputField) {
            case FIELD_LEFT:
                mFieldRight.setValue(NumberUtil.roundOrTruncate(
                        mApplicationContext, mPrefs, convert(conversion, mFieldLeft)));
                break;
            case FIELD_RIGHT:
                mFieldLeft.setValue(NumberUtil.roundOrTruncate(
                        mApplicationContext, mPrefs, convert(conversion, mFieldRight)));
                break;
        }
    }

    private double convert(@Conversion int conversion,
                           MutableLiveData<String> inputField) throws FatalCalculationException {
        switch (conversion) {
            case IN_TO_CM:
                return ConversionUtil.inchesToCentimeters(NumberUtil.parseDouble(inputField));
            case CM_TO_IN:
                return ConversionUtil.centimetersToInches(NumberUtil.parseDouble(inputField));
            case LB_TO_KG:
                return ConversionUtil.poundsToKilograms(NumberUtil.parseDouble(inputField));
            case KG_TO_LB:
                return ConversionUtil.kilogramsToPounds(NumberUtil.parseDouble(inputField));
            default:
                throw new FatalCalculationException("Conversion type not valid");
        }
    }
    //endregion

    //region Validation Methods
    private boolean validateInputDataAndToast(@Fields int field) {
        MutableLiveData<String> inputField = field == FIELD_LEFT ? mFieldLeft : mFieldRight;
        MutableLiveData<String> errorField = field == FIELD_LEFT ? mFieldLeftErrorMsg : mFieldRightErrorMsg;

        // Of course, we only want to return true if the input if valid. That said, we don't want to
        // set an error message if the user just erases the input, or corrects it, but we still want
        // to return false in these cases to indicate that the input is invalid. Therefore:
        //
        // if empty or null (user erases text) -> not valid, don't set error, don't toast, clear fields, reset error
        // if not empty or null and not a double -> not valid, set error, toast
        // if not empty, not null, and a double -> valid
        if (!isFieldEmptyOrNull(inputField)) {
            // field contains data, validate it
            if (!UiUtil.validateFieldsAndSetErrors(mApplicationContext
                    , new FieldErrorPair(inputField, errorField))) {
                // field data not valid, error already set during validation, show Toast
                UiUtil.showToast(mApplicationContext
                        , R.string.toast_invalid_fields, Toast.LENGTH_SHORT);
                return false;
            }
        } else {
            // field doesn't contain any data, clear output field's data and error msg
            clearAllFields();
            resetError(errorField == mFieldLeftErrorMsg ? mFieldRightErrorMsg : mFieldLeftErrorMsg);
            return false;
        }

        // input is valid, reset error message of output field because of edge case
        //
        // edge case -> if user has error messages in both fields,
        // the output field's message needs to be reset
        resetError(errorField == mFieldLeftErrorMsg ? mFieldRightErrorMsg : mFieldLeftErrorMsg);
        return true;
    }

    private boolean isFieldEmptyOrNull(MutableLiveData<String> field) {
        return field.getValue() == null || field.getValue() != null && field.getValue().equals("");
    }
    //endregion

    //region Helper methods
    @Conversion
    private int getConversionType(@Fields int inputField) throws FatalCalculationException {
        String type = mConversionType.getValue();

        if (type == null) {
            throw new NullPointerException();
        }

        // compare selection String to String Resource currently being
        // used in order to decide which conversion we are calculating
        if (type.equals(mApplicationContext.getResources().getString(R.string.text_in_to_cm))) {
            if (inputField == FIELD_LEFT) {
                return IN_TO_CM;
            } else if (inputField == FIELD_RIGHT) {
                return CM_TO_IN;
            }
        } else if (type.equals(mApplicationContext.getResources().getString(R.string.text_lb_to_kg))) {
            if (inputField == FIELD_LEFT) {
                return LB_TO_KG;
            } else if (inputField == FIELD_RIGHT) {
                return KG_TO_LB;
            }
        }

        throw new FatalCalculationException("Conversion type selection or input field not valid");
    }

    private void setFieldLabels(int leftFieldStringId, int rightFieldStringId) {
        // set left field unit data
        mFieldLeftLabel.setValue(mApplicationContext.getResources().getString(leftFieldStringId));
        // set right field unit data
        mFieldRightLabel.setValue(mApplicationContext.getResources().getString(rightFieldStringId));
    }

    private void clearAllFields() {
        UiUtil.clearFields(mFieldLeft, mFieldRight);
    }

    private void resetError(MutableLiveData<String> errorField) {
        errorField.setValue(null);
    }

    private void createDefaultPrefsObject() {
        mPrefs = new UserPreferences(mApplicationContext.getString(R.string.key_rounding), 5);
    }
    //endregion

    //region View methods
    LiveData<String> getConversionTypeData() {
        return mConversionType;
    }

    void clearAllFieldsAndErrors() {
        clearAllFields();
        mFieldLeftErrorMsg.setValue(null);
        mFieldRightErrorMsg.setValue(null);
    }

    void updateFieldLabelData() {
        if (mConversionType.getValue() != null) {
            if (mConversionType.getValue().equals(mApplicationContext
                    .getResources().getString(R.string.text_in_to_cm))) {
                setFieldLabels(R.string.text_in, R.string.text_cm);
            } else if (mConversionType.getValue().equals(mApplicationContext
                    .getResources().getString(R.string.text_lb_to_kg))) {
                setFieldLabels(R.string.text_lb, R.string.text_kg);
            }
        }
    }
    //endregion

    //region SavedState methods
    void saveState() {
        // save error message data
        mState.set(FIELD_LEFT_ERROR_MSG_KEY, mFieldLeftErrorMsg.getValue());
        mState.set(FIELD_RIGHT_ERROR_MSG_KEY, mFieldRightErrorMsg.getValue());
    }

    private void restoreState() {
        // retrieve error message data
        mFieldLeftErrorMsg = mState.getLiveData(FIELD_LEFT_ERROR_MSG_KEY);
        mFieldRightErrorMsg = mState.getLiveData(FIELD_RIGHT_ERROR_MSG_KEY);
    }
    //endregion

    //region Test against process death
    // couldn't find a way to test process death with Espresso, so just leaving this method here
    @SuppressLint("unused")
    private void checkPersistenceAfterSystemInitProcessDeath() {
        String inputOutput = "Conv type: " + mConversionType.getValue()
                + "\nLeft: " + mFieldLeft.getValue()
                + "\nRight: " + mFieldRight.getValue();

        String errors = "Left err: " + mFieldLeftErrorMsg.getValue()
                + "\nRight err: " + mFieldRightErrorMsg.getValue();

        UiUtil.showToast(mApplicationContext, inputOutput + "\n" + errors, Toast.LENGTH_LONG);
    }
    //endregion
}
