package com.example.rdpocketpal2.quickmethod

import android.app.Activity
import androidx.annotation.StringRes
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.settings.withSettingsRobot
import com.example.rdpocketpal2.testutil.TestRobot
import com.example.rdpocketpal2.testutil.TestUtil

open class QuickMethodRobot : TestRobot() {

    //region Weight
    fun enterWeight(weight: String) {
        enterText(R.id.qm_weight_editText, weight)
    }

    private fun checkWeight(weight: String) {
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
        pressBackButton()
    }
    //endregion

    //region Preferences
    fun setNumericScaleViaUi(scale: Int) {
        // open preferences
        openPreferences()
        // set settings value
        withSettingsRobot {
            setDisplayedDecimalPlaces(scale)
        }
        // exit settings screen
        pressBackButton()
    }

    fun setDecimalReductionMethodViaUi(@StringRes stringId: Int) {
        // open preferences
        openPreferences()
        // set settings value
        withSettingsRobot {
            setDecimalReductionMethod(stringId)
        }
        // exit settings screen
        pressBackButton()
    }
    //endregion

    //region Units
    fun setUnitMetric() {
        // click metric RadioButton
        clickViewId(R.id.qm_unit_metric)
    }

    fun setUnitStandard() {
        // click standard RadioButton
        clickViewId(R.id.qm_unit_standard)
    }

    fun checkUnitMetric() {
        checkRadioBtnIsChecked(R.id.qm_unit_metric)
    }

    fun checkUnitStandard() {
        checkRadioBtnIsChecked(R.id.qm_unit_standard)
    }

    fun <T : Activity> checkUnitMetricWeight(rule: ActivityTestRule<T>) {
        checkText(R.id.qm_weight_unit_label, TestUtil.getString(rule, R.string.text_kg))
    }

    fun <T : Activity> checkUnitStandardWeight(rule: ActivityTestRule<T>) {
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