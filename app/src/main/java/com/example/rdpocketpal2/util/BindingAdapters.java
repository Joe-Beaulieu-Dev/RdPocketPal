package com.example.rdpocketpal2.util;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.rdpocketpal2.R;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

public class BindingAdapters {

//    @BindingAdapter("watcher")
//    public static void bindTextWatcher(EditText editText, TextWatcher watcher) {
//        editText.addTextChangedListener(watcher);
//    }

//    @BindingAdapter("errorMsg")
//    public static void setErrorMessage(final EditText editText, final String errorMsg) {
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (!ValidationUtil.isDouble(s.toString())) {
////                    editText.setError(editText.getContext().getResources().getString(R.string.error_not_a_double));
//                    editText.setError(errorMsg);
//                }
//            }
//        });
//    }

    //region EditText
    @BindingAdapter("errorMsg")
    public static void setErrorMsg(EditText editText, String errorMsg) {
        // infinite loop not created
        editText.setError(errorMsg);
    }

    @InverseBindingAdapter(attribute = "errorMsg")
    public static String getErrorMsg(EditText editText) {
        if (!editText.getText().toString().equals("")
                && !NumberUtil.isDouble(editText.getText().toString())) {
            editText.setError(editText.getContext().getResources()
                    .getString(R.string.error_enter_a_number));
        }
        return editText.getError() != null ? editText.getError().toString() : null;
    }

    @BindingAdapter("errorMsgAttrChanged")
    public static void setListener(final EditText editText, final InverseBindingListener listener) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    listener.onChange();
                }
            }
        });
    }
    //endregion

    //region RadioGroup
    @BindingAdapter("selectedRadioBtn")
    public static void setSelectedSex(RadioGroup radioGroup, String selection) {
        // default value when Activity is loaded for the first time
        if (selection == null) {
            radioGroup.check(radioGroup.getChildAt(0).getId());
            // if this is not called, then the onClickListener will not be called, unlike on
            // subsequent clicks
            radioGroup.getChildAt(0).callOnClick();
            return;
        }

        int childCount = radioGroup.getChildCount();
        RadioButton[] radioButtons = new RadioButton[childCount];

        // get list of all RadioButtons in RadioGroup
        for (int i = 0; i < childCount; i++) {
            if (radioGroup.getChildAt(i) instanceof RadioButton) {
                radioButtons[i] = (RadioButton) radioGroup.getChildAt(i);
            }
        }

        // if selection equals the displayed text of a RadioButton in the RadioGroup, check it
        for (RadioButton btn : radioButtons) {
            if (selection.equals(btn.getText().toString())) {
                radioGroup.check(btn.getId());
                return;
            }
        }
    }

    @InverseBindingAdapter(attribute = "selectedRadioBtn", event = "selectedRadioBtnAttrChanged")
    public static String getSelectedSex(RadioGroup radioGroup) {
        int checkedBtnId = radioGroup.getCheckedRadioButtonId();
        return ((RadioButton) radioGroup.findViewById(checkedBtnId)).getText().toString();
    }

    @BindingAdapter("selectedRadioBtnAttrChanged")
    public static void bindRadioGroupData(RadioGroup radioGroup,
                                          final InverseBindingListener listener) {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                listener.onChange();
            }
        });
    }
    //endregion

    //region Spinner
    @InverseBindingAdapter(attribute = "selectedSpinnerItem", event = "selectedSpinnerItemAttrChanged")
    public static String getSelectedEquation(Spinner spinner) {
        // set attribute: selectedSpinnerItem to the String value of the selected Spinner item
        return spinner.getSelectedItem().toString();
    }

    @BindingAdapter(value = {"selectedSpinnerItem", "selectedSpinnerItemAttrChanged"}, requireAll = false)
    public static void bindEquationSpinnerData(Spinner spinner,
                                               final String selection,
                                               final InverseBindingListener listener) {
        // set the listener on the Spinner, which triggers onChange() whenever a Spinner item is selected
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // check to avoid infinite loops
                if (selection != null && selection.equals(parent.getSelectedItem())) {
                    return;
                }
                // trigger event: selectedSpinnerItemAttrChanged, which triggers the InverseBindingAdapter
                listener.onChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // i still don't know if this is necessary, maybe if activity dies and
        // viewmodel remains and needs to set to the previous value?
        // set selected value of Spinner
        if (selection != null) {
            // TODO sort this out
            int pos = ((ArrayAdapter<String>) spinner.getAdapter()).getPosition(selection);
            spinner.setSelection(pos, true);
        }
    }
    //endregion
}
