package com.josephbeaulieu.rdpocketpal.disclaimer

import com.josephbeaulieu.rdpocketpal.R
import com.josephbeaulieu.rdpocketpal.testutil.TestRobot

class DisclaimerDialogFragmentRobot : TestRobot() {

    //region Buttons
    fun clickContinue() {
        clickViewIdNoScroll(R.id.disc_dialog_continue)
    }

    fun clickExit() {
        clickViewIdNoScroll(R.id.disc_dialog_exit)
    }
    //endregion

    //Region Visibility
    fun checkDisclaimerDialogIsShowing() {
        checkViewWithTextIsDisplayedNoScroll(R.string.title_disclaimer_dialog)
    }

    fun checkDisclaimerDialogIsNotShowing() {
        checkViewWithTextIsNotDisplayedNoScroll(R.string.title_disclaimer_dialog)
    }

    fun checkTitleIsShowing() {
        checkViewWithIdIsDisplayedNoScroll(R.id.disc_dialog_title)
    }

    fun checkDisclaimerTextIsShowing() {
        checkViewWithIdIsDisplayedNoScroll(R.id.disc_dialog_text)
    }

    fun checkReadAnUnderstandTextIsShowing() {
        checkViewWithIdIsDisplayedNoScroll(R.id.disc_dialog_read_and_understand)
    }

    fun checkExitBtnIsShowing() {
        checkViewWithIdIsDisplayedNoScroll(R.id.disc_dialog_exit)
    }

    fun checkContinueBtnIsShowing() {
        checkViewWithIdIsDisplayedNoScroll(R.id.disc_dialog_continue)
    }
    //endregion
}

fun withDisclaimerDialogFragmentRobot(fn: DisclaimerDialogFragmentRobot.() -> Unit) =
        DisclaimerDialogFragmentRobot().apply(fn)