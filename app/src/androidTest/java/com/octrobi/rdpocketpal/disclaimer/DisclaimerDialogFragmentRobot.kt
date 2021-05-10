package com.octrobi.rdpocketpal.disclaimer

import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.testutil.TestRobot

class DisclaimerDialogFragmentRobot : TestRobot() {

    //region Buttons
    fun clickClose() {
        clickViewInDialogNoScroll(R.id.disc_dialog_close)
    }
    //endregion

    //Region Visibility
    fun checkDisclaimerDialogIsDisplayed() {
        checkViewIsDisplayedInDialog(R.id.disc_dialog_title)
        checkTextInDialog(R.id.disc_dialog_title, R.string.title_disclaimer_dialog)
    }

    fun checkDisclaimerDialogIsNotDisplayed() {
        checkViewIsNotDisplayedNoScroll(R.id.disc_dialog_title)
    }

    fun checkTitleIsDisplayed() {
        checkViewIsDisplayedInDialog(R.id.disc_dialog_title)
        checkTextInDialog(R.id.disc_dialog_title, R.string.title_disclaimer_dialog)
    }

    fun checkDisclaimerTextIsDisplayed() {
        checkViewIsDisplayedInDialog(R.id.disc_dialog_text)
        checkTextInDialog(R.id.disc_dialog_text, R.string.text_disclaimer)
    }

    fun checkCloseBtnIsShowing() {
        checkViewIsDisplayedInDialog(R.id.disc_dialog_close)
    }
    //endregion
}

fun withDisclaimerDialogFragmentRobot(fn: DisclaimerDialogFragmentRobot.() -> Unit) =
        DisclaimerDialogFragmentRobot().apply(fn)