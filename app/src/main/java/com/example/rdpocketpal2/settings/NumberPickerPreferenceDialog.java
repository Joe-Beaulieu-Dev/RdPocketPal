package com.example.rdpocketpal2.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

import com.example.rdpocketpal2.R;

import androidx.preference.PreferenceDialogFragmentCompat;

public class NumberPickerPreferenceDialog extends PreferenceDialogFragmentCompat {
    private NumberPicker mPicker;

    static NumberPickerPreferenceDialog newInstance(String key) {
        // store which Preference this Dialog belongs to
        final NumberPickerPreferenceDialog fragment = new NumberPickerPreferenceDialog();
        final Bundle bundle = new Bundle(1);
        bundle.putString(ARG_KEY, key);
        fragment.setArguments(bundle);

        return fragment;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        if (getDialog() != null && getDialog().getWindow() != null) {
//            AlertDialog dialog = (AlertDialog) getDialog();
//
//            (dialog.getButton(AlertDialog.BUTTON_NEGATIVE)).setBackgroundResource(R.drawable.ripple_rectangle);
//
//            // set up Button ripple
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                setUpBtnRipple(dialog.getButton(AlertDialog.BUTTON_NEGATIVE));
//                setUpBtnRipple(dialog.getButton(AlertDialog.BUTTON_POSITIVE));
//            }
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void setUpBtnRipple(Button btn) {
//        if (getActivity() != null) {
//            btn.setBackgroundResource(R.drawable.ripple_rectangle);
////            btn.setForeground(getActivity().getResources()
////                    .getDrawable(R.drawable.ripple_rectangle, getActivity().getTheme()));
//        }
//    }

    @Override
    protected View onCreateDialogView(Context context) {
        FrameLayout layout = (FrameLayout) LayoutInflater.from(context)
                .inflate(R.layout.dialog_number_picker_preference, null, false);
        mPicker = (NumberPicker) LayoutInflater.from(context)
                .inflate(R.layout.pref_dialog_number_picker, layout, false);

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

        // set value on preference
        mPicker.setValue(preference.getValue());
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
