package com.octrobi.rdpocketpal.conversions

import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.testutil.TestRobot

class ConversionsRobot : TestRobot() {

    //region Buttons
    fun clickClear() {
        clickViewId(R.id.conv_clear_btn)
    }
    //endregion

    //region Errors
    fun checkLeftFieldNanError() {
        checkEditTextError(R.id.conv_field_left, R.string.error_enter_a_number)
    }

    fun checkRightFieldNanError() {
        checkEditTextError(R.id.conv_field_right, R.string.error_enter_a_number)
    }

    fun checkLeftFieldNoError() {
        checkEditTextNoError(R.id.conv_field_left)
    }

    fun checkRightFieldNoError() {
        checkEditTextNoError(R.id.conv_field_right)
    }
    //endregion

    //region Visibility
    fun checkSettingsNoteIsShowing() {
        checkViewWithIdIsDisplayedNoScroll(R.id.conv_settings_note)
    }
    //endregion

    //region Robot builders
    fun inInCm(fn: InCmRobot.() -> Unit) = InCmRobot().apply(fn)

    fun inLbKg(fn: LbKgRobot.() -> Unit) = LbKgRobot().apply(fn)
    //endregion
}

fun withConversionsRobot(fn: ConversionsRobot.() -> Unit) = ConversionsRobot().apply(fn)