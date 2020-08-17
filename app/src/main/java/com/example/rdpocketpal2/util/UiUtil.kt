@file:JvmName("UiUtil")

package com.example.rdpocketpal2.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.example.rdpocketpal2.R

fun clearFields(vararg fieldData: MutableLiveData<String>): Boolean {
    var anyFieldCleared = false
    fieldData.forEach { if (clearField(it)) anyFieldCleared = true }
    return anyFieldCleared
}

fun clearField(fieldData: MutableLiveData<String>): Boolean {
    if (fieldData.value != null && fieldData.value != "") {
        fieldData.value = null
        return true
    }
    return false
}

fun showToast(context: Context, @StringRes stringId: Int, duration : Int) {
    // can't reference @View.DURATION in the parameters so have to do this
    if (duration != Toast.LENGTH_SHORT && duration != Toast.LENGTH_LONG) {
        return
    }

    // show Toast
    Toast.makeText(context, context.resources.getString(stringId), duration).show()
}

fun showToast(context: Context, msg: String?, duration : Int) {
    // can't reference @View.DURATION in the parameters so have to do this
    if (duration != Toast.LENGTH_SHORT && duration != Toast.LENGTH_LONG) {
        return
    }

    // show Toast
    Toast.makeText(context, msg, duration).show()
}

fun validateFieldsAndSetErrors(context: Context, vararg fieldErrorPair: FieldErrorPair): Boolean {
    var allInputsValid = true

    // validate and set error messages for all fields. If any fields are invalid, report
    fieldErrorPair.forEach {
        if (!validateFieldAndSetError(context, it.data, it.error)) allInputsValid = false
    }

    return allInputsValid
}

fun validateFieldAndSetError(context: Context
                             , field: MutableLiveData<String>
                             , errorField: MutableLiveData<String>): Boolean {
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