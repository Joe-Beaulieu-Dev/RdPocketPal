package com.octrobi.rdpocketpal.settings;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.octrobi.rdpocketpal.R;

import androidx.fragment.app.DialogFragment;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {
    private static final String TAG_NUMBER_PICKER_DIALOG = "NumberPickerDialog";
    private int mHighlightColor;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey);

        // set up preference elements
        setUpPreferenceUi();
    }

    private void setUpPreferenceUi() {
        setUpHighlightColor();
        setUpDisplayedDecimalPlaces();
        setUpDecimalReduction();
    }

    private void setUpHighlightColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && getActivity() != null) {
            mHighlightColor = getResources().getColor(R.color.colorAccent, getActivity().getTheme());
        } else {
            mHighlightColor = getResources().getColor(R.color.colorAccent);
        }
    }

    private void setUpDisplayedDecimalPlaces() {
        final NumberPickerPreference numericScale = findPreference(getString(R.string.key_numeric_scale));

        // set summary to current entry
        if (numericScale != null) {
            numericScale.setSummaryProvider(new Preference.SummaryProvider() {
                @Override
                public CharSequence provideSummary(Preference preference) {
                    // create summary string
                    int entry = numericScale.getValue();
                    String desc = " " + getString(R.string.text_numeric_scale_desc);
                    String summary = entry + desc;

                    // return a colorized, bold version of the summary
                    return getBoldColorizedString(summary, 0, String.valueOf(entry).length());
                }
            });
        }
    }

    private void setUpDecimalReduction() {
        final ListPreference decimalReductionMethod = findPreference(getString(R.string.key_decimal_reduction_method));

        // set summary to current entry
        if (decimalReductionMethod != null) {
            decimalReductionMethod.setSummaryProvider(new Preference.SummaryProvider() {
                @Override
                public CharSequence provideSummary(Preference preference) {
                    String entry = decimalReductionMethod.getEntry().toString();
                    return getBoldColorizedString(entry, 0, entry.length());
                }
            });
        }
    }

    private Spannable getBoldColorizedString(String string, int startColorIndex, int endColorIndex) {
        Spannable spannable = new SpannableString(string);

        // make string portion bold
        spannable.setSpan(new StyleSpan(Typeface.BOLD)
                , startColorIndex
                , endColorIndex
                , Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        // colorize string portion
        spannable.setSpan(new ForegroundColorSpan(mHighlightColor)
                , startColorIndex
                , endColorIndex
                , Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannable;
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        DialogFragment dialogFragment = null;
        // check if opening a custom Preference
        if (preference instanceof NumberPickerPreference) {
            dialogFragment = NumberPickerPreferenceDialog.newInstance(preference.getKey());
        }

        // display PreferenceDialogFragment
        if (dialogFragment != null && isAdded()) {
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(getParentFragmentManager(), TAG_NUMBER_PICKER_DIALOG);
        } else {
            super.onDisplayPreferenceDialog(preference);
        }
    }
}
