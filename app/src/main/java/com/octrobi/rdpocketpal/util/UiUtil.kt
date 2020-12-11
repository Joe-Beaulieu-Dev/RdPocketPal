@file:JvmName("UiUtil")

package com.octrobi.rdpocketpal.util

import android.content.Context
import android.content.res.Resources
import android.content.res.Resources.Theme
import android.os.Build
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import com.octrobi.rdpocketpal.R

//region Navigation
fun setNextBtnBehaviorForEditText(source: EditText, target: EditText) {
    source.setOnEditorActionListener { _, actionId, _ ->
        // Next button on soft keyboard
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            target.requestFocus()
        }
        false
    }
}
//endregion

//region Logic
fun isDefaultRadioBtnChecked(btn: RadioButton): Boolean {
    // check types to be safe
    if ((btn.parent is RadioGroup)
            && ((btn.parent as RadioGroup)
                    .getChildAt(DEFAULT_RADIO_BTN_INDEX) is RadioButton)) {
        // the actual check of if the default RadioButton is selected
        return ((btn.parent as RadioGroup)
                .getChildAt(DEFAULT_RADIO_BTN_INDEX) as RadioButton)
                .isChecked
    }
    // types don't match what we need, return false
    return false
}
//endregion

//region Validation
fun validateFieldsAndSetErrors(context: Context, vararg fieldErrorPair: FieldErrorPair): Boolean {
    var allInputsValid = true

    // validate and set error messages for all fields. If any fields are invalid, report
    fieldErrorPair.forEach {
        if (!validateFieldAndSetError(context, it.data, it.error)) allInputsValid = false
    }

    return allInputsValid
}

private fun validateFieldAndSetError(context: Context, field: MutableLiveData<String>, errorField: MutableLiveData<String>): Boolean {
    return if (!isFieldValid(field)) {
        setError(context, errorField)
        false
    } else {
        true
    }
}

private fun isFieldValid(field: MutableLiveData<String>): Boolean {
    return NumberUtil.isDouble(field)
}

private fun setError(context: Context, errorField: MutableLiveData<String>) {
    errorField.value = context.getString(R.string.error_enter_a_number)
}
//endregion

//region Field clearing
@SafeVarargs
fun clearFields(vararg fieldData: MutableLiveData<String>): Boolean {
    // this is only here for @SafeVarargs, which is only there for calls from Java code
    // filter is used because you can't use continue inside a functional loop
    fieldData.filter { it.value != null }.forEach {
        if (it.value !is String) {
            return false
        }
    }

    var anyFieldCleared = false
    fieldData.forEach { if (clearField(it)) anyFieldCleared = true }
    return anyFieldCleared
}

private fun clearField(fieldData: MutableLiveData<String>): Boolean {
    if (fieldData.value != null && fieldData.value != "") {
        fieldData.value = null
        return true
    }
    return false
}
//endregion

//region Buttons
fun setUpBtnRippleOval(res: Resources, theme: Theme, btn: Button) {
    setUpBtnRipple(res, theme, btn, R.drawable.ripple_oval)
}

fun setUpBtnRippleRectangle(res: Resources, theme: Theme, btn: Button) {
    setUpBtnRipple(res, theme, btn, R.drawable.ripple_rectangle)
}

private fun setUpBtnRipple(res: Resources, theme: Theme?, btn: Button, @DrawableRes drawableId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        btn.foreground = ResourcesCompat.getDrawable(res, drawableId, theme)
    }
}
//endregion

//region Toasts
fun showToast(context: Context, @StringRes stringId: Int, duration: Int) {
    // can't reference @View.DURATION in the parameters so have to do this
    if (duration != Toast.LENGTH_SHORT && duration != Toast.LENGTH_LONG) {
        return
    }

    // show Toast
    Toast.makeText(context, context.resources.getString(stringId), duration).show()
}

fun showToast(context: Context, msg: String?, duration: Int) {
    // can't reference @View.DURATION in the parameters so have to do this
    if (duration != Toast.LENGTH_SHORT && duration != Toast.LENGTH_LONG) {
        return
    }

    // show Toast
    Toast.makeText(context, msg, duration).show()
}
//endregion