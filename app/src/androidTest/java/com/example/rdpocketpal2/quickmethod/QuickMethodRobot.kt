package com.example.rdpocketpal2.quickmethod

import android.app.Activity
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.pressBack
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.testutil.TestRobot
import com.example.rdpocketpal2.testutil.TestUtil

//region Testing constants
const val EMPTY_STRING = ""
//endregion

open class QuickMethodRobot : TestRobot() {

    //region Weight
    fun enterWeight(weight: String) {
        enterText(R.id.qm_weight_editText, weight)
    }

    fun checkWeight(weight: String) {
        checkText(R.id.qm_weight_editText, weight)
    }

    fun <T : Activity> checkWeightNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(R.id.qm_weight_editText
                , TestUtil.getString(rule, R.string.error_enter_a_number))
    }

    fun <T : Activity> checkWeightNoError(rule: ActivityTestRule<T>) {
        checkEditTextError(R.id.qm_weight_editText, null)
    }
    //endregion

    //region Preferences
    fun setNumericScaleViaUi(scale: Int) {
        // open preferences
        openPreferences()
        // click numeric scale setting
        clickViewText(R.string.text_numeric_scale)
        // set value to 2
        setNumberPickerValue(R.id.pref_dialog_number_picker, scale)
        // click OK (positive button) to close dialog
        clickViewId(android.R.id.button1)
        // exit settings screen
        pressBack()
    }

    fun setDecimalReductionMethodViaUi(@IdRes stringId: Int) {
        // open preferences
        openPreferences()
        // click decimal reduction setting
        clickViewText(R.string.text_decimal_reduction_method)
        // set value to rounding
        clickViewText(stringId)
        // exit settings screen
        pressBack()
    }
    //endregion

    //region Units
    fun setInputMetric() {
        // click metric RadioButton
        clickViewId(R.id.qm_unit_metric)
    }

    fun setInputStandard() {
        // click standard RadioButton
        clickViewId(R.id.qm_unit_standard)
    }

    fun <T : Activity> checkUnitMetric(rule: ActivityTestRule<T>) {
        checkText(R.id.qm_weight_unit_label, TestUtil.getString(rule, R.string.text_kg))
    }

    fun <T : Activity> checkUnitStandard(rule: ActivityTestRule<T>) {
        checkText(R.id.qm_weight_unit_label, TestUtil.getString(rule, R.string.text_lb))
    }
    //endregion

    //region Robot builders
    fun inCalories(fn: CalorieRobot.() -> Unit): CalorieRobot = CalorieRobot().apply(fn)

    fun inProtein(fn: ProteinRobot.() -> Unit): ProteinRobot = ProteinRobot().apply(fn)

    fun inFluids(fn: FluidsRobot.() -> Unit): FluidsRobot = FluidsRobot().apply(fn)
    //endregion
}

fun withQuickMethodRobot(fn: QuickMethodRobot.() -> Unit) = QuickMethodRobot().apply(fn)