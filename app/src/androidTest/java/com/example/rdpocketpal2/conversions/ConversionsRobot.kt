package com.example.rdpocketpal2.conversions

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.testutil.TestRobot
import com.example.rdpocketpal2.testutil.TestUtil

class ConversionsRobot : TestRobot() {

    //region Buttons
    fun clickClear() {
        clickViewId(R.id.conv_clear_btn)
    }
    //endregion

    //region Errors
    fun <T : Activity> checkLeftFieldNanError(activityRule: ActivityTestRule<T>) {
        checkEditTextError(R.id.conv_field_left
                , TestUtil.getString(activityRule, R.string.error_enter_a_number))
    }

    fun <T : Activity> checkRightFieldNanError(activityRule: ActivityTestRule<T>) {
        checkEditTextError(R.id.conv_field_right
                , TestUtil.getString(activityRule, R.string.error_enter_a_number))
    }

    fun checkLeftFieldNoError() {
        checkEditTextError(R.id.conv_field_left, null)
    }

    fun checkRightFieldNoError() {
        checkEditTextError(R.id.conv_field_right, null)
    }
    //endregion

    //region Robot builders
    fun inInCm(fn: InCmRobot.() -> Unit) = InCmRobot().apply(fn)

    fun inLbKg(fn: LbKgRobot.() -> Unit) = LbKgRobot().apply(fn)

    fun inGmMeq(fn: GmMeqRobot.() -> Unit) = GmMeqRobot().apply(fn)

    fun inMgMeq(fn: MgMeqRobot.() -> Unit) = MgMeqRobot().apply(fn)
    //endregion
}

fun withConversionsRobot(fn: ConversionsRobot.() -> Unit) = ConversionsRobot().apply(fn)