package com.example.rdpocketpal2.conversions

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.testutil.TestRobot

open class MeqRobot : TestRobot() {

    //region Entry
    fun <T : Activity> selectCalcium(activityRule: ActivityTestRule<T>) {
        clickSpinnerItem(activityRule, R.id.conv_element_spinner_meq, R.string.text_calcium)
    }

    fun <T : Activity> selectChlorine(activityRule: ActivityTestRule<T>) {
        clickSpinnerItem(activityRule, R.id.conv_element_spinner_meq, R.string.text_chlorine)
    }

    fun <T : Activity> selectMagnesium(activityRule: ActivityTestRule<T>) {
        clickSpinnerItem(activityRule, R.id.conv_element_spinner_meq, R.string.text_magnesium)
    }

    fun <T : Activity> selectPotassium(activityRule: ActivityTestRule<T>) {
        clickSpinnerItem(activityRule, R.id.conv_element_spinner_meq, R.string.text_potassium)
    }

    fun <T : Activity> selectSodium(activityRule: ActivityTestRule<T>) {
        clickSpinnerItem(activityRule, R.id.conv_element_spinner_meq, R.string.text_sodium)
    }

    fun enterMilliequivalents(milliequivalents: String) {
        enterText(R.id.conv_field_right, milliequivalents)
    }
    //endregion

    //region Validation
    fun checkMilliequivalents(milliequivalents: String) {
        checkText(R.id.conv_field_right, milliequivalents)
    }
    //endregion
}