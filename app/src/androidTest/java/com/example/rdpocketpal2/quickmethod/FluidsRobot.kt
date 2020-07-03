package com.example.rdpocketpal2.quickmethod

import android.app.Activity
import androidx.test.espresso.Espresso.pressBack
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.testutil.TestUtil

class FluidsRobot : QuickMethodRobot() {

    fun enterMinInput(kcal: String) {
        enterText(R.id.qm_fluid_ml_per_kg_min, kcal)
    }

    fun enterMaxInput(kcal: String) {
        enterText(R.id.qm_fluid_ml_per_kg_max, kcal)
    }

    fun checkMinOutput(kcal: String) {
        checkText(R.id.qm_fluid_ml_per_day_min, kcal)
    }

    fun checkMaxOutput(kcal: String) {
        checkText(R.id.qm_fluid_ml_per_day_max, kcal)
    }

    fun <T : Activity> checkMinInputNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(R.id.qm_fluid_ml_per_kg_min
                , TestUtil.getString(rule, R.string.error_enter_a_number))
    }

    fun <T : Activity> checkMaxInputNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(R.id.qm_fluid_ml_per_kg_max
                , TestUtil.getString(rule, R.string.error_enter_a_number))
    }

    fun checkMinInputNoError() {
        checkEditTextError(R.id.qm_fluid_ml_per_kg_min, null)
    }

    fun checkMaxInputNoError() {
        checkEditTextError(R.id.qm_fluid_ml_per_kg_max, null)
    }

    fun checkFieldsClear() {
        checkText(R.id.qm_fluid_ml_per_kg_min, EMPTY_STRING)
        checkText(R.id.qm_fluid_ml_per_kg_max, EMPTY_STRING)
        checkText(R.id.qm_fluid_ml_per_day_min, EMPTY_STRING)
        checkText(R.id.qm_fluid_ml_per_day_max, EMPTY_STRING)
    }

    fun clickClear() {
        clickViewId(R.id.qm_fluid_clear_btn)
    }

    fun clickCalculate() {
        clickViewId(R.id.qm_fluid_calculate_btn)
    }

    fun leaveField() {
        clickViewId(R.id.qm_weight_editText)
        pressBack()
    }

    fun setAllFieldsProgrammatically() {
        setTextProgrammatically(R.id.qm_fluid_ml_per_kg_min, "1")
        setTextProgrammatically(R.id.qm_fluid_ml_per_kg_max, "2")
        setTextProgrammatically(R.id.qm_fluid_ml_per_day_min, "3")
        setTextProgrammatically(R.id.qm_fluid_ml_per_day_max, "4")
    }

    fun checkAllProgrammaticallySetFields() {
        checkText(R.id.qm_fluid_ml_per_kg_min, "1")
        checkText(R.id.qm_fluid_ml_per_kg_max, "2")
        checkText(R.id.qm_fluid_ml_per_day_min, "3")
        checkText(R.id.qm_fluid_ml_per_day_max, "4")
    }
}