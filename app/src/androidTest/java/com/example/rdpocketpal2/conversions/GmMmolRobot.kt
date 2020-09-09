package com.example.rdpocketpal2.conversions

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R

class GmMmolRobot : MmolRobot() {

    //region Entry
    fun <T : Activity> navigateToGmMmol(activityRule: ActivityTestRule<T>) {
        clickSpinnerItem(activityRule, R.id.conv_conversion_spinner, R.string.text_gm_to_mmol)
    }

    fun enterGrams(grams: String) {
        enterText(R.id.conv_field_left, grams)
    }
    //endregion

    //region Validation
    fun checkGrams(grams: String) {
        checkText(R.id.conv_field_left, grams)
    }
    //endregion
}