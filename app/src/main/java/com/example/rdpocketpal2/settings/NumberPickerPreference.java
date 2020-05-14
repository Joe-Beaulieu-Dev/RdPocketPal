package com.example.rdpocketpal2.settings;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.example.rdpocketpal2.R;

import androidx.annotation.Nullable;
import androidx.preference.DialogPreference;

public class NumberPickerPreference extends DialogPreference {
    // value range
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 4;
    // if wheel wraps values
    public static final boolean WRAP_SELECTOR_WHEEL = false;
    // flag for if value not saved
    private static final int NO_PERSISTED_VALUE = -1;

    private int mValue;
    private int mDialogLayoutResId = R.layout.pref_dialog_number_picker;

    public NumberPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberPickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        mValue = value;
        persistInt(mValue);
        notifyChanged();
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        // default value from xml attribute, with fallback
        return a.getInt(index, MIN_VALUE);
    }

    @Override
    protected void onSetInitialValue(@Nullable Object defaultValue) {
        // onSetInitialValue (boolean restorePersistedValue, Object defaultValue) is deprecated,
        // so we have to do this. NO_PERSISTED_VALUE is the fallback for getPersistedInt()

        int value = defaultValue == null ? MIN_VALUE : (int) defaultValue;
        if (getPersistedInt(NO_PERSISTED_VALUE) != NO_PERSISTED_VALUE) {
            // a value is set, don't use defaultValue
            value = getPersistedInt(mValue);
        }

        setValue(value);
    }

    @Override
    public int getDialogLayoutResource() {
        return mDialogLayoutResId;
    }
}
