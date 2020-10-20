package com.josephbeaulieu.rdpocketpal.conversions

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import com.josephbeaulieu.rdpocketpal.R
import com.josephbeaulieu.rdpocketpal.testutil.TestRobot

class ConversionsRobot : TestRobot() {

    //region Buttons
    fun clickClear() {
        clickViewId(R.id.conv_clear_btn)
    }
    //endregion

    //region Errors
    fun <T : Activity> checkLeftFieldNanError(activityRule: ActivityTestRule<T>) {
        checkEditTextError(activityRule, R.id.conv_field_left, R.string.error_enter_a_number)
    }

    fun <T : Activity> checkRightFieldNanError(activityRule: ActivityTestRule<T>) {
        checkEditTextError(activityRule, R.id.conv_field_right, R.string.error_enter_a_number)
    }

    fun checkLeftFieldNoError() {
        checkEditTextNoError(R.id.conv_field_left)
    }

    fun checkRightFieldNoError() {
        checkEditTextNoError(R.id.conv_field_right)
    }
    //endregion

    //region Robot builders
    fun inInCm(fn: InCmRobot.() -> Unit) = InCmRobot().apply(fn)

    fun inLbKg(fn: LbKgRobot.() -> Unit) = LbKgRobot().apply(fn)
    //endregion
}

fun withConversionsRobot(fn: ConversionsRobot.() -> Unit) = ConversionsRobot().apply(fn)