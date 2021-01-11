package com.octrobi.rdpocketpal.getstarted

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.home.HomeActivity
import com.octrobi.rdpocketpal.testutil.TestRobot

class GetStartedRobot : TestRobot() {

    //region Click UI Elements
    fun clickDisclaimerLink() {
        clickClickableSpan(R.id.start_read_and_understand, R.string.text_disclaimer_link)
    }

    fun clickContinue() {
        clickViewId(R.id.start_continue)
    }
    //endregion

    //region Check UI Elements
    fun checkReadAndUnderstandTextIsShowing() {
        checkViewWithTextIsDisplayed(R.string.text_read_and_understand)
    }

    fun checkContinueBtnIsShowing() {
        checkViewWithIdIsDisplayed(R.id.start_continue)
    }

    fun <T : Activity> checkContinueBtnHasProperText(rule: ActivityTestRule<T>) {
        checkText(rule, R.id.start_continue, R.string.text_continue)
    }
    //endregion

    //region Activities
    fun checkHomeActivityIsDisplayed() {
        checkActivityIsDisplayed(HomeActivity::class.java.name)
    }
    //endregion
}

fun withGetStartedRobot(fn : GetStartedRobot.() -> Unit) = GetStartedRobot().apply(fn)