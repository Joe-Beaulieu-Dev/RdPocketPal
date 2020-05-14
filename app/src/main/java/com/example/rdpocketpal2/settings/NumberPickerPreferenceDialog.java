package com.example.rdpocketpal2.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import com.example.rdpocketpal2.R;

import androidx.preference.PreferenceDialogFragmentCompat;

public class NumberPickerPreferenceDialog extends PreferenceDialogFragmentCompat {
    private NumberPicker mPicker;

    public static NumberPickerPreferenceDialog newInstance(String key) {
        // store which Preference this Dialog belongs to
        final NumberPickerPreferenceDialog fragment = new NumberPickerPreferenceDialog();
        final Bundle bundle = new Bundle(1);
        bundle.putString(ARG_KEY, key);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        mPicker = view.findViewById(R.id.pref_dialog_number_picker);

        // configure preference
        mPicker.setMinValue(NumberPickerPreference.MIN_VALUE);
        mPicker.setMaxValue(NumberPickerPreference.MAX_VALUE);
        mPicker.setWrapSelectorWheel(NumberPickerPreference.WRAP_SELECTOR_WHEEL);

        // set value on preference
        mPicker.setValue(((NumberPickerPreference) getPreference()).getValue());
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult && mPicker != null) {
            // get selected value
            int value = mPicker.getValue();

            // get Preference and save value
            NumberPickerPreference preference = (NumberPickerPreference) getPreference();
            // allows the client to ignore the user value
            if (preference.callChangeListener(value)) {
                preference.setValue(value);
            }
        }
    }
}
