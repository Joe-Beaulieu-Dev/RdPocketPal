package com.josephbeaulieu.rdpocketpal.conversions

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import com.josephbeaulieu.rdpocketpal.R

class GmMeqRobot : MeqRobot() {

    //region Entry
    fun <T : Activity> navigateToGmMeq(activityRule: ActivityTestRule<T>) {
        clickSpinnerItem(activityRule, R.id.conv_conversion_spinner, R.string.text_gm_to_meq)
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