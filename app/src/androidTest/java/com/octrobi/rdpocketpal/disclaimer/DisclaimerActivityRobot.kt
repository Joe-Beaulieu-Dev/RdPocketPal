package com.octrobi.rdpocketpal.disclaimer

import android.app.Instrumentation
import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.testutil.TestRobot

class DisclaimerActivityRobot : TestRobot() {

    //region Visibility
    fun checkActionBarSaysDisclaimer(instrumentation: Instrumentation) {
        checkActionBarTitle(instrumentation, R.string.title_disclaimer)
    }

    fun checkDisclaimerTextDisplays() {
        checkViewWithIdIsDisplayedNoScroll(R.id.disclaimer_text)
        checkViewWithTextIsDisplayedNoScroll(R.string.text_disclaimer)
    }
    //endregion

    //region Activity check
    fun checkDisclaimerActivityIsDisplayed() {
        checkActivityIsDisplayed(DisclaimerActivity::class.java.name)
    }
    //endregion
}

fun withDisclaimerActivityRobot(fn: DisclaimerActivityRobot.() -> Unit) =
        DisclaimerActivityRobot().apply(fn)