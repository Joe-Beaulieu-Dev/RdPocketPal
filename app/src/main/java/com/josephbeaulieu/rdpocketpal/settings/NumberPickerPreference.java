package com.josephbeaulieu.rdpocketpal.settings;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.josephbeaulieu.rdpocketpal.R;

import androidx.annotation.Nullable;
import androidx.preference.DialogPreference;

public class NumberPickerPreference extends DialogPreference {
    // default values
    private static final int DEFAULT_MIN_VALUE = 0;
    private static final int DEFAULT_MAX_VALUE = 5;
    private static final boolean DEFAULT_WRAP_SELECTOR_WHEEL = true;

    // values from xml
    private final int mMinValue;
    private final int mMaxValue;
    private final boolean mWrapSelectorWheel;

    // flag for if value not saved
    private static final int NO_PERSISTED_VALUE = -1;

    private int mValue;
    private int mDialogLayoutResId = R.layout.pref_dialog_number_picker;

    public NumberPickerPreference(Context context) {
        this(context, null);
    }

    public NumberPickerPreference(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.dialogPreferenceStyle);
    }

    public NumberPickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public NumberPickerPreference(Context context, AttributeSet attrs,
                                  int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        // apply custom attributes
        TypedArray array = context.obtainStyledAttributes(attrs
                , R.styleable.NumberPickerPreference);
        try {
            mMinValue = array.getInt(R.styleable.NumberPickerPreference_minValue
                    , DEFAULT_MIN_VALUE);
            mMaxValue = array.getInt(R.styleable.NumberPickerPreference_maxValue
                    , DEFAULT_MAX_VALUE);
            mWrapSelectorWheel = array.getBoolean(
                    R.styleable.NumberPickerPreference_wrapSelectorWheel
                    , DEFAULT_WRAP_SELECTOR_WHEEL);
        } finally {
            array.recycle();
        }
    }

    //region Getters and Setters
    public int getValue() {
        return mValue;
    }

    int getMinValue() {
        return mMinValue;
    }

    int getMaxValue() {
        return mMaxValue;
    }

    boolean getWrapSelectorWheel() {
        return mWrapSelectorWheel;
    }

    void setValue(int value) {
        mValue = value;
        // save the new value
        persistInt(mValue);
        // required for the summary to change
        notifyChanged();
    }
    //endregion

    //region Overridden methods
    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        // default value from xml attribute, with fallback
        return a.getInt(index, mMinValue);
    }

    @Override
    protected void onSetInitialValue(@Nullable Object defaultValue) {
        // onSetInitialValue (boolean restorePersistedValue, Object defaultValue) is deprecated,
        // so we have to do this. NO_PERSISTED_VALUE is the fallback for getPersistedInt()

        int value = defaultValue == null ? mMinValue : (int) defaultValue;
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
    //endregion
}
