package com.octrobi.rdpocketpal.util

import android.view.View
import android.widget.*
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.textfield.TextInputLayout
import com.octrobi.rdpocketpal.R

//region TextInputLayout
@BindingAdapter("errorMsg")
fun TextInputLayout.setErrorMsg(errorMsg: String?) {
    // break infinite loops
    if (error?.toString() != errorMsg) {
        error = errorMsg
    }
}

@InverseBindingAdapter(attribute = "errorMsg")
fun TextInputLayout.getErrorMsg(): String? {
    val input = editText?.text?.toString()
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
fun TextInputLayout.setErrorListener(listener: InverseBindingListener) {
    // set listener and fire
    editText?.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus -> if (!hasFocus) listener.onChange() }
}
//endregion

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
fun RadioGroup.setSelectedBtnText(selection: String?) {
    // set initial value
    if (selection == null) {
        check(getChildAt(DEFAULT_RADIO_BTN_INDEX).id)
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
fun RadioGroup.getSelectedBtnText(): String {
    return findViewById<RadioButton>(checkedRadioButtonId)?.text.toString()
}

@BindingAdapter("selectedRadioBtnAttrChanged")
fun RadioGroup.setRadioGroupListener(listener: InverseBindingListener) {
    setOnCheckedChangeListener { _, _ -> listener.onChange() }
}
//endregion

//region Spinner
@InverseBindingAdapter(attribute = "selectedSpinnerItem")
fun Spinner.getSelectionString(): String? {
    return selectedItem?.toString()
}

@BindingAdapter(value = ["selectedSpinnerItem", "selectedSpinnerItemAttrChanged"], requireAll = false)
fun Spinner.setSpinnerListener(selection: String?, listener: InverseBindingListener) {
    //for selectedSpinnerItemAttrChanged
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            // prevent calls to listener.onChange() that were happening on orientation change
            if (selection == parent?.selectedItem) {
                return
            }
            listener.onChange()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // empty
        }
    }

    // for selectedSpinnerItem
    if (selection != null && selection != selectedItem) {
        // TODO sort this out
        val pos = (adapter as? ArrayAdapter<String>)!!.getPosition(selection)
        setSelection(pos, true)
    }
}
//endregion