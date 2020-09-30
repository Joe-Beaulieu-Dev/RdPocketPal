package com.josephbeaulieu.rdpocketpal.conversions

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import com.josephbeaulieu.rdpocketpal.R
import com.josephbeaulieu.rdpocketpal.testutil.TestRobot

open class MmolRobot : TestRobot() {

    //region Entry
    fun <T : Activity> selectPhosphorus(activityRule: ActivityTestRule<T>) {
        clickSpinnerItem(activityRule, R.id.conv_element_spinner_mmol, R.string.text_phosphorus)
    }

    fun enterMillimoles(millimoles: String) {
        enterText(R.id.conv_field_right, millimoles)
    }
    //endregion

    //region Validation
    fun checkMillimoles(millimoles: String) {
        checkText(R.id.conv_field_right, millimoles)
    }
    //endregion
}