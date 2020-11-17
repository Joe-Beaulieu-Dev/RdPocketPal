package com.josephbeaulieu.rdpocketpal.disclaimer

import com.josephbeaulieu.rdpocketpal.R
import com.josephbeaulieu.rdpocketpal.testutil.TestRobot

class DisclaimerDialogFragmentRobot : TestRobot() {

    //region Buttons
    fun clickAgree() {
        clickViewIdNoScroll(R.id.disc_dialog_agree)
    }

    fun clickDecline() {
        clickViewIdNoScroll(R.id.disc_dialog_decline)
    }
    //endregion

    //Region Visibility
    fun checkDisclaimerDialogIsShowing() {
        checkViewWithTextIsDisplayedNoScroll(R.string.title_disclaimer_dialog)
    }

    fun checkDisclaimerDialogIsNotShowing() {
        checkViewWithTextIsNotDisplayedNoScroll(R.string.title_disclaimer_dialog)
    }
    //endregion
}

fun withDisclaimerDialogFragmentRobot(fn: DisclaimerDialogFragmentRobot.() -> Unit) =
        DisclaimerDialogFragmentRobot().apply(fn)