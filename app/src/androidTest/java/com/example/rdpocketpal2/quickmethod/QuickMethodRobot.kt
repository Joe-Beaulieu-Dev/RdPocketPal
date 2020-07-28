package com.example.rdpocketpal2.quickmethod

import android.app.Activity
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.pressBack
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.testutil.TestRobot
import com.example.rdpocketpal2.testutil.TestUtil

open class QuickMethodRobot : TestRobot() {

    //region Weight
    fun enterWeight(weight: String) {
        enterText(R.id.qm_weight_editText, weight)
    }

    fun checkWeight(weight: String) {
        checkText(R.id.qm_weight_editText, weight)
    }

    fun <T : Activity> checkWeightNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(rule, R.id.qm_weight_editText, R.string.error_enter_a_number)
    }

    fun checkWeightNoError() {
        checkEditTextNoError(R.id.qm_weight_editText)
    }

    fun leaveField() {
        clickViewId(R.id.qm_calorie_kcal_per_kg_min)
        pressBack()
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

    fun setAllFieldsProgrammatically(text: String) {
        setTextProgrammatically(R.id.qm_weight_editText, text)
        inCalories {
            setAllFieldsProgrammatically(text)
        }
        inProtein {
            setAllFieldsProgrammatically(text)
        }
        inFluids {
            setAllFieldsProgrammatically(text)
        }
    }

    fun checkAllProgrammaticallySetFields(text: String) {
        checkWeight(text)
        inCalories {
            checkAllProgrammaticallySetFields(text)
        }
        inProtein {
            checkAllProgrammaticallySetFields(text)
        }
        inFluids {
            checkAllProgrammaticallySetFields(text)
        }
    }

    //region Robot builders
    fun inCalories(fn: CalorieRobot.() -> Unit) = CalorieRobot().apply(fn)

    fun inProtein(fn: ProteinRobot.() -> Unit) = ProteinRobot().apply(fn)

    fun inFluids(fn: FluidsRobot.() -> Unit) = FluidsRobot().apply(fn)
    //endregion
}

fun withQuickMethodRobot(fn: QuickMethodRobot.() -> Unit) = QuickMethodRobot().apply(fn)