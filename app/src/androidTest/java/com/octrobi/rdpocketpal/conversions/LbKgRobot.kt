package com.octrobi.rdpocketpal.conversions

import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.testutil.TestRobot

class LbKgRobot : TestRobot() {

    //region Entry
    fun navigateToLbKg() {
        clickSpinnerItem(R.id.conv_conversion_spinner, R.string.text_lb_to_kg)
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