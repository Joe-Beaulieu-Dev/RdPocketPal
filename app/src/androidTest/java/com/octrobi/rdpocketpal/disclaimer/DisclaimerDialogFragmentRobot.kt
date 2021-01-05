package com.octrobi.rdpocketpal.disclaimer

import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.testutil.TestRobot

class DisclaimerDialogFragmentRobot : TestRobot() {

    //region Buttons
    fun clickClose() {
        clickViewIdNoScroll(R.id.disc_dialog_close)
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

    fun checkCloseBtnIsShowing() {
        checkViewWithIdIsDisplayedNoScroll(R.id.disc_dialog_close)
    }
    //endregion
}

fun withDisclaimerDialogFragmentRobot(fn: DisclaimerDialogFragmentRobot.() -> Unit) =
        DisclaimerDialogFragmentRobot().apply(fn)