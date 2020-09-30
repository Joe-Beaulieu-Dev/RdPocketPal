package com.example.rdpocketpal2.quickmethod

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.testutil.EMPTY_STRING
import com.example.rdpocketpal2.testutil.TestRobot

class CalorieRobot : TestRobot() {

    fun enterMinInput(kcal: String) {
        enterText(R.id.qm_calorie_kcal_per_kg_min, kcal)
    }

    fun enterMaxInput(kcal: String) {
        enterText(R.id.qm_calorie_kcal_per_kg_max, kcal)
    }

    fun checkMinInput(kcal: String) {
        checkText(R.id.qm_calorie_kcal_per_kg_min, kcal)
    }

    fun checkMaxInput(kcal: String) {
        checkText(R.id.qm_calorie_kcal_per_kg_max, kcal)
    }

    fun checkMinOutput(kcal: String) {
        checkText(R.id.qm_calorie_kcal_per_day_min, kcal)
    }

    fun checkMaxOutput(kcal: String) {
        checkText(R.id.qm_calorie_kcal_per_day_max, kcal)
    }

    fun <T : Activity> checkMinInputNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(rule, R.id.qm_calorie_kcal_per_kg_min, R.string.error_enter_a_number)
    }

    fun <T : Activity> checkMaxInputNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(rule, R.id.qm_calorie_kcal_per_kg_max, R.string.error_enter_a_number)
    }

    fun checkMinInputNoError() {
        checkEditTextNoError(R.id.qm_calorie_kcal_per_kg_min)
    }

    fun checkMaxInputNoError() {
        checkEditTextNoError(R.id.qm_calorie_kcal_per_kg_max)
    }

    fun checkFieldsClear() {
        checkText(R.id.qm_calorie_kcal_per_kg_min, EMPTY_STRING)
        checkText(R.id.qm_calorie_kcal_per_kg_max, EMPTY_STRING)
        checkText(R.id.qm_calorie_kcal_per_day_min, EMPTY_STRING)
        checkText(R.id.qm_calorie_kcal_per_day_max, EMPTY_STRING)
    }

    fun clickClear() {
        clickViewId(R.id.qm_calorie_clear_btn)
    }

    fun clickCalculate() {
        clickViewId(R.id.qm_calorie_calculate_btn)
    }

    fun leaveField() {
        clickViewId(R.id.qm_weight_editText)
        pressBackButton()
    }

    fun setAllFieldsProgrammatically(text: String) {
        setTextProgrammatically(R.id.qm_calorie_kcal_per_kg_min, text)
        setTextProgrammatically(R.id.qm_calorie_kcal_per_kg_max, text)
        setTextProgrammatically(R.id.qm_calorie_kcal_per_day_min, text)
        setTextProgrammatically(R.id.qm_calorie_kcal_per_day_max, text)
    }

    fun checkAllProgrammaticallySetFields(text: String) {
        checkText(R.id.qm_calorie_kcal_per_kg_min, text)
        checkText(R.id.qm_calorie_kcal_per_kg_max, text)
        checkText(R.id.qm_calorie_kcal_per_day_min, text)
        checkText(R.id.qm_calorie_kcal_per_day_max, text)
    }
}