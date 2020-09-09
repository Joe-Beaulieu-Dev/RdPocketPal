package com.example.rdpocketpal2.conversions

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R

class MgMmolRobot : MmolRobot() {

    //region Entry
    fun <T : Activity> navigateToMgMmol(activityRule: ActivityTestRule<T>) {
        clickSpinnerItem(activityRule, R.id.conv_conversion_spinner, R.string.text_mg_to_mmol)
    }

    fun enterMilligrams(milligrams: String) {
        enterText(R.id.conv_field_left, milligrams)
    }
    //endregion

    //region Validation
    fun checkMilligrams(milligrams: String) {
        checkText(R.id.conv_field_left, milligrams)
    }
    //endregion
}