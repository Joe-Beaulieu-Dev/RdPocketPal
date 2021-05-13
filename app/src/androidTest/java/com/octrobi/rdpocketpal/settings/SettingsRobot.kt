package com.octrobi.rdpocketpal.settings

import androidx.annotation.StringRes
import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.testutil.TestRobot
import com.octrobi.rdpocketpal.testutil.TestUtil

class SettingsRobot : TestRobot() {

    //region Settings values
    fun setDisplayedDecimalPlaces(scale: Int) {
        // click numeric scale setting
        clickViewTextNoScroll(R.string.text_numeric_scale)
        // set value
        setNumberPickerValue(R.id.pref_dialog_number_picker, scale)
        // click OK (positive button) to close dialog
        clickView(android.R.id.button1)
    }

    fun setDecimalReductionMethod(@StringRes stringId: Int) {
        // click decimal reduction setting
        clickViewTextNoScroll(R.string.text_decimal_reduction_method)
        // set value
        clickViewTextNoScroll(stringId)
    }

    fun checkDisplayedDecimalPlacesDescription(numPlaces: Int) {
        val desc = buildDisplayedDecimalPlacesDescriptionString(numPlaces)
        checkViewWithTextIsDisplayedNoScroll(desc)
    }

    fun checkDecimalReductionMethodDescription(@StringRes stringId: Int) {
        checkViewWithTextIsDisplayedNoScroll(stringId)
    }

    private fun buildDisplayedDecimalPlacesDescriptionString(numPlaces: Int): String =
        "$numPlaces ${TestUtil.getString(R.string.text_numeric_scale_desc)}"
    //endregion

    //region Activity check
    fun checkSettingsActivityIsDisplayed() {
        checkActivityIsDisplayed(SettingsActivity::class.java.name)
    }
    //endregion
}

fun withSettingsRobot(fn: SettingsRobot.() -> Unit) = SettingsRobot().apply(fn)