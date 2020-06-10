package com.example.rdpocketpal2.util

import android.view.View
import android.widget.*
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter

import androidx.databinding.InverseBindingListener
import com.example.rdpocketpal2.R
import kotlinx.android.synthetic.*

//region EditText
@BindingAdapter("errorMsg")
fun EditText.setErrorMsg(errorMsg: String?) {
    // break infinite loops
    if (error?.toString() != errorMsg) {
        error = errorMsg
    }
}

@InverseBindingAdapter(attribute = "errorMsg")
fun EditText.getErrorMsg(): String? {
    val input = text?.toString()
    var newErrMsg: String? = null

    // determine which error msg, if any, to use
    if (input == "") {
        // reset error
        return null
    } else if (!NumberUtil.isDouble(input)) {
        newErrMsg = context.getString(R.string.error_enter_a_number)
    }

    return newErrMsg
}

@BindingAdapter("errorMsgAttrChanged")
fun EditText.setErrorListener(listener: InverseBindingListener) {
    // set listener and fire
    onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus -> if (!hasFocus) listener.onChange() }
}
//endregion

//region RadioGroup
@BindingAdapter("selectedRadioBtn")
fun RadioGroup.setSelectedSexBtnText(selection: String?) {
    // set initial value
    if (selection == null) {
        check(getChildAt(0).id)
        // must be explicitly called using this method
        getChildAt(0).callOnClick()
        return
    }

    // find RadioButton with matching text and check it
    children.forEach {
        if (it is RadioButton && it.text.toString() == selection) {
            check(it.id)
            return
        }
    }
}

@InverseBindingAdapter(attribute = "selectedRadioBtn")
fun RadioGroup.getSelectedBtnText(): String? {
    return findViewById<RadioButton>(checkedRadioButtonId)?.text.toString()
}

@BindingAdapter("selectedRadioBtnAttrChanged")
fun RadioGroup.setRadioGroupListener(listener: InverseBindingListener) {
    setOnCheckedChangeListener { _, _ -> listener.onChange() }
}
//endregion

//region Spinner
@BindingAdapter("selectedSpinnerItem")
fun Spinner.setSelectionFromString(selection: String?) {
    if (selection != null && selection != selectedItem) {
        // TODO sort this out
        val pos = (adapter as? ArrayAdapter<String>)!!.getPosition(selection)
        setSelection(pos, true)
    }
}

@InverseBindingAdapter(attribute = "selectedSpinnerItem")
fun Spinner.getSelectionString(): String? {
    return selectedItem?.toString()
}

@BindingAdapter("selectedSpinnerItemAttrChanged")
fun Spinner.setSpinnerListener(listener: InverseBindingListener) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            listener.onChange()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // empty
        }
    }
}
//endregion