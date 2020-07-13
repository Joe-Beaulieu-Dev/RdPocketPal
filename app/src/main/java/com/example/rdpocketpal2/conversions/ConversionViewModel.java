package com.example.rdpocketpal2.conversions;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rdpocketpal2.R;
import com.example.rdpocketpal2.model.UserPreferences;
import com.example.rdpocketpal2.quickmethod.FatalCalculationException;
import com.example.rdpocketpal2.util.Constants;
import com.example.rdpocketpal2.util.ConversionUtil;
import com.example.rdpocketpal2.util.Element;
import com.example.rdpocketpal2.util.NumberUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

public class ConversionViewModel extends AndroidViewModel {
    //region LiveData
    // UI data
    public MutableLiveData<String> mConversionType = new MutableLiveData<>();
    public MutableLiveData<String> mElement = new MutableLiveData<>();
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
    @IntDef({IN_TO_CM, CM_TO_IN, LB_TO_KG, KG_TO_LB, GM_TO_MEQ, MEQ_TO_GM, MG_TO_MEQ, MEQ_TO_MG})
    private @interface Conversion {}
    private static final int IN_TO_CM = 0;
    private static final int CM_TO_IN = 1;
    private static final int LB_TO_KG = 2;
    private static final int KG_TO_LB = 3;
    private static final int GM_TO_MEQ = 4;
    private static final int MEQ_TO_GM = 5;
    private static final int MG_TO_MEQ = 6;
    private static final int MEQ_TO_MG = 7;

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
                convertAndSetResult(getConversionType(FIELD_LEFT), FIELD_LEFT);
            }
        }
    }

    public void onRightFieldChanged(EditText editText) {
        // check for focus to break loops
        if (editText.hasFocus()) {
            if (validateInputDataAndToast(FIELD_RIGHT)) {
                convertAndSetResult(getConversionType(FIELD_RIGHT), FIELD_RIGHT);
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
            case GM_TO_MEQ:
                return ConversionUtil.gramsToMilliequivalents(getElement()
                        , NumberUtil.parseDouble(inputField));
            case MEQ_TO_GM:
                return ConversionUtil.milliequivalentsToGrams(getElement()
                        , NumberUtil.parseDouble(inputField));
            case MG_TO_MEQ:
                return ConversionUtil.milligramsToMilliequivalents(getElement()
                        , NumberUtil.parseDouble(inputField));
            case MEQ_TO_MG:
                return ConversionUtil.milliequivalentsToMilligrams(getElement()
                        , NumberUtil.parseDouble(inputField));
            default:
                // this should never happen, just here to quiet the ide
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
            if (!validateFieldAndSetError(inputField, errorField)) {
                showToast(R.string.toast_invalid_fields, Toast.LENGTH_SHORT);
                return false;
            }
        } else {
            clearAllFields();
            resetError(errorField == mFieldLeftErrorMsg ? mFieldRightErrorMsg : mFieldLeftErrorMsg);
            return false;
        }
        // input is valid, reset error message of output field (edge case -> if user has error
        // messages in both fields, the output field's message needs to be reset)
        resetError(errorField == mFieldLeftErrorMsg ? mFieldRightErrorMsg : mFieldLeftErrorMsg);
        return true;
    }

    private boolean isFieldEmptyOrNull(MutableLiveData<String> field) {
        return field.getValue() == null || field.getValue() != null && field.getValue().equals("");
    }

    private boolean validateFieldAndSetError(MutableLiveData<String> field,
                                          MutableLiveData<String> fieldError) {
        // if invalid, set error message
        if (!NumberUtil.isDouble(field)) {
            setNumberError(fieldError);
            return false;
        }
        return true;
    }

    private void setNumberError(MutableLiveData<String> fieldError) {
        // set error message for LiveData associated with field
        fieldError.setValue(mApplicationContext.getResources().getString(R.string.error_enter_a_number));
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
        } else if (type.equals(mApplicationContext.getResources().getString(R.string.text_gm_to_meq))) {
            if (inputField == FIELD_LEFT) {
                return GM_TO_MEQ;
            } else if (inputField == FIELD_RIGHT) {
                return MEQ_TO_GM;
            }
        } else if (type.equals(mApplicationContext.getResources().getString(R.string.text_mg_to_meq))) {
            if (inputField == FIELD_LEFT) {
                return MG_TO_MEQ;
            } else if (inputField == FIELD_RIGHT) {
                return MEQ_TO_MG;
            }
        }

        // this should never happen, just here to quiet the ide
        throw new FatalCalculationException("Conversion type selection or input field not valid");
    }

    @Element
    private int getElement() throws FatalCalculationException {
        String element = mElement.getValue();

        if (element == null) {
            throw new NullPointerException();
        }

        // compare selection String to String Resource currently being
        // used in order to decide which element was chosen
        if (element.equals(mApplicationContext
                .getResources().getString(R.string.text_calcium))) {
            return Constants.CALCIUM;
        } else if (element.equals(mApplicationContext
                .getResources().getString(R.string.text_chlorine))) {
            return Constants.CHLORINE;
        } else if (element.equals(mApplicationContext
                .getResources().getString(R.string.text_magnesium))) {
            return Constants.MAGNESIUM;
        } else if (element.equals(mApplicationContext
                .getResources().getString(R.string.text_phosphorus))) {
            return Constants.PHOSPHORUS;
        } else if (element.equals(mApplicationContext
                .getResources().getString(R.string.text_potassium))) {
            return Constants.POTASSIUM;
        } else if (element.equals(mApplicationContext
                .getResources().getString(R.string.text_sodium))) {
            return Constants.SODIUM;
        }

        // this should never happen, just here to quiet the ide
        throw new FatalCalculationException("Element selection not valid");
    }

    private void setFieldLabels(int leftFieldStringId, int rightFieldStringId) {
        // set left field unit data
        mFieldLeftLabel.setValue(mApplicationContext.getResources().getString(leftFieldStringId));
        // set right field unit data
        mFieldRightLabel.setValue(mApplicationContext.getResources().getString(rightFieldStringId));
    }

    void clearAllFields() {
        clearField(mFieldLeft);
        clearField(mFieldRight);
    }

    private void clearField(MutableLiveData<String> fieldData) {
        if (fieldData.getValue() != null && !fieldData.getValue().equals("")) {
            fieldData.setValue("");
        }
    }

    private void resetError(MutableLiveData<String> errorField) {
        errorField.setValue(null);
    }

    private void createDefaultPrefsObject() {
        mPrefs = new UserPreferences(mApplicationContext.getString(R.string.key_rounding), 5);
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

    //region View methods
    MutableLiveData<String> getConversionTypeData() {
        return mConversionType;
    }

    MutableLiveData<String> getElementData() {
        return mElement;
    }

    void updateFieldLabelData() {
        if (mConversionType.getValue() != null) {
            if (mConversionType.getValue().equals(mApplicationContext
                    .getResources().getString(R.string.text_in_to_cm))) {
                setFieldLabels(R.string.text_in, R.string.text_cm);
            } else if (mConversionType.getValue().equals(mApplicationContext
                    .getResources().getString(R.string.text_lb_to_kg))) {
                setFieldLabels(R.string.text_lb, R.string.text_kg);
            } else if (mConversionType.getValue().equals(mApplicationContext
                    .getResources().getString(R.string.text_gm_to_meq))) {
                setFieldLabels(R.string.text_gm, R.string.text_meq);
            } else if (mConversionType.getValue().equals(mApplicationContext
                    .getResources().getString(R.string.text_mg_to_meq))) {
                setFieldLabels(R.string.text_mg, R.string.text_meq);
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
}
