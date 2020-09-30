package com.josephbeaulieu.rdpocketpal.conversions

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import com.josephbeaulieu.rdpocketpal.R

class MgMeqRobot : MeqRobot() {

    //region Entry
    fun <T : Activity> navigateToMgMeq(activityRule: ActivityTestRule<T>) {
        clickSpinnerItem(activityRule, R.id.conv_conversion_spinner, R.string.text_mg_to_meq)
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