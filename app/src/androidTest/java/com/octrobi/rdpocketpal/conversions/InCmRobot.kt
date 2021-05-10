package com.octrobi.rdpocketpal.conversions

import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.testutil.TestRobot

class InCmRobot : TestRobot() {

    //region Entry
    fun navigateToInCm() {
        clickSpinnerItem(R.id.conv_conversion_spinner, R.string.text_in_to_cm)
    }

    fun enterInches(inches: String) {
        enterText(R.id.conv_field_left, inches)
    }

    fun enterCentimeters(centimeters: String) {
        enterText(R.id.conv_field_right, centimeters)
    }
    //endregion

    //region Validation
    fun checkInches(inches: String) {
        checkText(R.id.conv_field_left, inches)
    }

    fun checkCentimeters(centimeters: String) {
        checkText(R.id.conv_field_right, centimeters)
    }
    //endregion
}