package com.octrobi.rdpocketpal.disclaimer

import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.testutil.TestRobot

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
        checkViewWithTextIsDisplayedNoScroll(R.string.title_disclaimer_dialog)
    }

    fun checkDisclaimerTextIsShowing() {
        checkViewWithIdIsDisplayedNoScroll(R.id.disc_dialog_text)
        checkViewWithTextIsDisplayedNoScroll(R.string.text_disclaimer)
    }

    fun checkReadAnUnderstandTextIsShowing() {
        checkViewWithIdIsDisplayedNoScroll(R.id.disc_dialog_read_and_understand)
        checkViewWithTextIsDisplayedNoScroll(R.string.text_read_and_understand)
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