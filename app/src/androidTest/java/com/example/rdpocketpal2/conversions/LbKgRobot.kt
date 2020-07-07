package com.example.rdpocketpal2.conversions

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.testutil.TestRobot

class LbKgRobot : TestRobot() {

    //region Entry
    fun <T : Activity> navigateToLbKg(activityRule: ActivityTestRule<T>) {
        clickSpinnerItem(activityRule, R.id.conv_conversion_spinner, R.string.text_lb_to_kg)
    }

    fun enterPounds(pounds: String) {
        enterText(R.id.conv_field_left, pounds)
    }

    fun enterKilograms(kilograms: String) {
        enterText(R.id.conv_field_right, kilograms)
    }
    //endregion

    //region Validation
    fun checkPounds(pounds: String) {
        checkText(R.id.conv_field_left, pounds)
    }

    fun checkKilograms(kilograms: String) {
        checkText(R.id.conv_field_right, kilograms)
    }
    //endregion
}