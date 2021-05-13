package com.octrobi.rdpocketpal.quickmethod

import androidx.annotation.StringRes
import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.settings.withSettingsRobot
import com.octrobi.rdpocketpal.testutil.TestRobot
import com.octrobi.rdpocketpal.testutil.TestUtil

open class QuickMethodRobot : TestRobot() {

    //region Weight
    fun enterWeight(weight: String) {
        enterText(R.id.qm_weight_editText, weight)
    }

    private fun checkWeight(weight: String) {
        checkText(R.id.qm_weight_editText, weight)
    }

    fun checkWeightNanError() {
        checkEditTextError(R.id.qm_weight_editText, R.string.error_enter_a_number)
    }

    fun checkWeightNoError() {
        checkEditTextNoError(R.id.qm_weight_editText)
    }

    fun leaveField() {
        clickView(R.id.qm_calorie_kcal_per_kg_min)
        pressBackButton()
    }
    //endregion

    //region Units
    fun setUnitMetric() {
        // click metric RadioButton
        clickView(R.id.qm_unit_metric)
    }

    fun setUnitStandard() {
        // click standard RadioButton
        clickView(R.id.qm_unit_standard)
    }

    fun checkUnitMetric() {
        checkRadioBtnIsChecked(R.id.qm_unit_metric)
    }

    fun checkUnitStandard() {
        checkRadioBtnIsChecked(R.id.qm_unit_standard)
    }

    fun checkUnitMetricWeight() {
        checkText(R.id.qm_weight_unit_label, TestUtil.getString(R.string.text_kg))
    }

    fun checkUnitStandardWeight() {
        checkText(R.id.qm_weight_unit_label, TestUtil.getString(R.string.text_lb))
    }
    //endregion

    //region Mass field entry/validation
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
    //endregion

    //region Preferences
    fun setNumericScaleViaUi(scale: Int) {
        // open settings
        openSettings()
        // set settings value
        withSettingsRobot {
            setDisplayedDecimalPlaces(scale)
        }
        // exit settings screen
        pressBackButton()
    }

    fun setDecimalReductionMethodViaUi(@StringRes stringId: Int) {
        // open settings
        openSettings()
        // set settings value
        withSettingsRobot {
            setDecimalReductionMethod(stringId)
        }
        // exit settings screen
        pressBackButton()
    }
    //endregion

    //region Robot builders
    fun inCalories(fn: CalorieRobot.() -> Unit) = CalorieRobot().apply(fn)

    fun inProtein(fn: ProteinRobot.() -> Unit) = ProteinRobot().apply(fn)

    fun inFluids(fn: FluidsRobot.() -> Unit) = FluidsRobot().apply(fn)
    //endregion
}

fun withQuickMethodRobot(fn: QuickMethodRobot.() -> Unit) = QuickMethodRobot().apply(fn)