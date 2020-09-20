package com.example.rdpocketpal2.conversions

import android.app.Activity
import android.widget.Spinner
import androidx.annotation.IdRes
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.testutil.TestRobot

class ConversionsRobot : TestRobot() {

    //region Buttons
    fun clickClear() {
        clickViewId(R.id.conv_clear_btn)
    }
    //endregion

    //region Spinners
    fun <T : Activity> selectRandomElement(activityRule: ActivityTestRule<T>) {
        clickSpinnerItem(R.id.conv_element_spinner_meq
                , getNewSpinnerSelection(activityRule, R.id.conv_element_spinner_meq))
    }

    private fun <T : Activity> getNewSpinnerSelection(activityRule: ActivityTestRule<T>
                                                      , @IdRes spinnerId: Int): String {
        return activityRule.activity
                .findViewById<Spinner>(spinnerId)
                .adapter.getItem(getNewSpinnerSelectionIndex(activityRule, spinnerId)).toString()
    }

    private fun <T : Activity> getNewSpinnerSelectionIndex(activityRule: ActivityTestRule<T>
                                                           , @IdRes spinnerId: Int): Int {
        val spinnerPosition = activityRule.activity
                .findViewById<Spinner>(spinnerId).selectedItemPosition
        val spinnerListSize = activityRule.activity
                .findViewById<Spinner>(spinnerId).adapter.count

        // safely calculate new index for Spinner
        return if (spinnerPosition in 0 until spinnerListSize - 1) {
            spinnerPosition + 1
        } else {
            spinnerPosition - 1
        }
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

    fun inGmMeq(fn: GmMeqRobot.() -> Unit) = GmMeqRobot().apply(fn)

    fun inMgMeq(fn: MgMeqRobot.() -> Unit) = MgMeqRobot().apply(fn)

    fun inGmMmol(fn: GmMmolRobot.() -> Unit) = GmMmolRobot().apply(fn)

    fun inMgMmol(fn: MgMmolRobot.() -> Unit) = MgMmolRobot().apply(fn)
    //endregion
}

fun withConversionsRobot(fn: ConversionsRobot.() -> Unit) = ConversionsRobot().apply(fn)