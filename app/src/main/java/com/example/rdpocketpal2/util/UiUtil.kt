@file:JvmName("UiUtil")

package com.example.rdpocketpal2.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData

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