package com.josephbeaulieu.rdpocketpal.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

import com.josephbeaulieu.rdpocketpal.R;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceDialogFragmentCompat;

public class NumberPickerPreferenceDialog extends PreferenceDialogFragmentCompat {
    // NumberPicker value
    private int mValue;

    // NumberPicker value saved state key
    private static final String KEY_VALUE = "keyValue";

    private NumberPicker mPicker;

    static NumberPickerPreferenceDialog newInstance(String key) {
        // store which Preference this Dialog belongs to
        final NumberPickerPreferenceDialog fragment = new NumberPickerPreferenceDialog();
        final Bundle bundle = new Bundle(1);
        bundle.putString(ARG_KEY, key);
        fragment.setArguments(bundle);

        return fragment;
    }

    //region Lifecycle methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mValue = getNumberPickerPreference().getValue();
        } else {
            mValue = savedInstanceState.getInt(KEY_VALUE);
        }
    }

    @Override
    protected View onCreateDialogView(Context context) {
        FrameLayout layout = (FrameLayout) LayoutInflater.from(context)
                .inflate(R.layout.dialog_number_picker_preference, null, false);
        mPicker = (NumberPicker) LayoutInflater.from(context)
                .inflate(R.layout.pref_dialog_number_picker, layout, false);

        // add the custom NumberPicker to the Dialog
        layout.addView(mPicker);

        return layout;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        NumberPickerPreference preference = (NumberPickerPreference) getPreference();

        // configure preference
        mPicker.setMinValue(preference.getMinValue());
        mPicker.setMaxValue(preference.getMaxValue());
        mPicker.setWrapSelectorWheel(preference.getWrapSelectorWheel());
        mPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // record the value so it can be used when restoring state
                mValue = newVal;
            }
        });

        // set value on preference
        mPicker.setValue(mValue);
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult && mPicker != null) {
            // get selected value
            int value = mPicker.getValue();

            // get Preference and save value
            NumberPickerPreference preference = (NumberPickerPreference) getPreference();
            if (preference.callChangeListener(value)) {
                preference.setValue(value);
            }
        }
    }
    //endregion

    //region Getters
    private NumberPickerPreference getNumberPickerPreference() {
        return (NumberPickerPreference) getPreference();
    }
    //endregion

    //region Saved state
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_VALUE, mValue);
    }
    //endregion
}
