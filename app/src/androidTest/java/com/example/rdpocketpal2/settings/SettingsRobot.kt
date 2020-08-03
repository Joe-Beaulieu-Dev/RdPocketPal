package com.example.rdpocketpal2.settings

import android.app.Activity
import androidx.annotation.StringRes
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.testutil.TestRobot
import com.example.rdpocketpal2.testutil.TestUtil

class SettingsRobot : TestRobot() {

    //region Settings values
    fun setDisplayedDecimalPlaces(scale: Int) {
        // click numeric scale setting
        clickViewText(R.string.text_numeric_scale)
        // set value
        setNumberPickerValue(R.id.pref_dialog_number_picker, scale)
        // click OK (positive button) to close dialog
        clickViewId(android.R.id.button1)
    }

    fun setDecimalReductionMethod(@StringRes stringId: Int) {
        // click decimal reduction setting
        clickViewText(R.string.text_decimal_reduction_method)
        // set value
        clickViewText(stringId)
    }

    fun <T : Activity> checkDisplayedDecimalPlacesDescription(rule: ActivityTestRule<T>
                                                              , numPlaces: Int) {
        val desc = buildDisplayedDecimalPlacesDescriptionString(rule, numPlaces)
        checkViewWithTextIsDisplayed(desc)
    }

    fun checkDecimalReductionMethodDescription(@StringRes stringId: Int) {
        checkViewWithTextIsDisplayed(stringId)
    }

    private fun <T : Activity> buildDisplayedDecimalPlacesDescriptionString(rule: ActivityTestRule<T>
                                                                    , numPlaces: Int): String {
        return "$numPlaces ${TestUtil.getString(rule, R.string.text_numeric_scale_desc)}"
    }
    //endregion

    //region Activity check
    fun checkSettingsActivityIsDisplayed() {
        checkActivityIsDisplayed(SettingsActivity::class.java.name)
    }
    //endregion
}

fun withSettingsRobot(fn: SettingsRobot.() -> Unit) = SettingsRobot().apply(fn)