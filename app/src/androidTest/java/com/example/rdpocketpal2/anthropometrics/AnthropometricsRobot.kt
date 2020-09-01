package com.example.rdpocketpal2.anthropometrics

import android.app.Activity
import androidx.annotation.IdRes
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.testutil.TestRobot

class AnthropometricsRobot : TestRobot() {

    //region Sex
    fun setSexMale() {
        clickViewId(R.id.anthro_sex_male)
    }

    fun setSexFemale() {
        clickViewId(R.id.anthro_sex_female)
    }

    fun checkSexMale() {
        checkRadioBtnIsChecked(R.id.anthro_sex_male)
    }

    fun checkSexFemale() {
        checkRadioBtnIsChecked(R.id.anthro_sex_female)
    }
    //endregion

    //region Units
    fun setUnitMetric() {
        clickViewId(R.id.anthro_unit_metric)
    }

    fun setUnitStandard() {
        clickViewId(R.id.anthro_unit_standard)
    }

    fun checkUnitMetric() {
        checkRadioBtnIsChecked(R.id.anthro_unit_metric)
    }

    fun checkUnitStandard() {
        checkRadioBtnIsChecked(R.id.anthro_unit_standard)
    }

    fun <T : Activity> checkUnitMetricWeight(rule: ActivityTestRule<T>) {
        checkText(rule, R.id.anthro_weight_unit_label, R.string.text_kg)
    }

    fun <T : Activity> checkUnitMetricHeight(rule: ActivityTestRule<T>) {
        checkText(rule, R.id.anthro_height_unit_label, R.string.text_cm)
    }

    fun <T : Activity> checkUnitMetricBmi(rule: ActivityTestRule<T>) {
        checkText(rule, R.id.anthro_bmi_unit_label, R.string.text_kg_per_m2)
    }

    fun <T : Activity> checkUnitMetricIbw(rule: ActivityTestRule<T>) {
        checkText(rule, R.id.anthro_ibw_unit_label, R.string.text_kg)
    }

    fun <T : Activity> checkUnitMetricPercentIbw(rule: ActivityTestRule<T>) {
        checkText(rule, R.id.anthro_percent_ibw_unit_label, R.string.text_percent_sign)
    }

    fun <T : Activity> checkUnitMetricAdjustedBw(rule: ActivityTestRule<T>) {
        checkText(rule, R.id.anthro_adjusted_bw_unit_label, R.string.text_kg)
    }

    fun <T : Activity> checkUnitStandardWeight(rule: ActivityTestRule<T>) {
        checkText(rule, R.id.anthro_weight_unit_label, R.string.text_lb)
    }

    fun <T : Activity> checkUnitStandardHeight(rule: ActivityTestRule<T>) {
        checkText(rule, R.id.anthro_height_unit_label, R.string.text_in)
    }

    fun <T : Activity> checkUnitStandardBmi(rule: ActivityTestRule<T>) {
        checkText(rule, R.id.anthro_bmi_unit_label, R.string.text_kg_per_m2)
    }

    fun <T : Activity> checkUnitStandardIbw(rule: ActivityTestRule<T>) {
        checkText(rule, R.id.anthro_ibw_unit_label, R.string.text_lb)
    }

    fun <T : Activity> checkUnitStandardPercentIbw(rule: ActivityTestRule<T>) {
        checkText(rule, R.id.anthro_percent_ibw_unit_label, R.string.text_percent_sign)
    }

    fun <T : Activity> checkUnitStandardAdjustedBw(rule: ActivityTestRule<T>) {
        checkText(rule, R.id.anthro_adjusted_bw_unit_label, R.string.text_lb)
    }
    //endregion

    //region Buttons
    fun clickClear() {
        clickViewId(R.id.anthro_clear_btn)
    }

    fun clickCalculate() {
        clickViewId(R.id.anthro_calculate_btn)
    }
    //endregion

    //region Entry
    fun enterWeight(weight: String) {
        enterText(R.id.anthro_weight_editText, weight)
    }

    fun enterHeight(height: String) {
        enterText(R.id.anthro_height_editText, height)
    }

    fun setAllFieldsProgrammatically(input: String) {
        setFieldProgrammatically(input
                , R.id.anthro_weight_editText
                , R.id.anthro_height_editText
                , R.id.anthro_bmi_editText
                , R.id.anthro_ibw_editText
                , R.id.anthro_percent_ibw_editText
                , R.id.anthro_adjusted_bw_editText)
    }

    private fun setFieldProgrammatically(input: String, @IdRes vararg ids: Int) {
        ids.forEach { setTextProgrammatically(it, input) }
    }

    fun leaveWeightField() {
        clickViewId(R.id.anthro_height_editText)
    }

    fun leaveHeightField() {
        clickViewId(R.id.anthro_weight_editText)
    }
    //endregion

    //region Field validation
    fun checkWeight(weight: String) {
        checkText(R.id.anthro_weight_editText, weight)
    }

    fun checkHeight(height: String) {
        checkText(R.id.anthro_height_editText, height)
    }

    fun checkBmi(bmi: String) {
        checkText(R.id.anthro_bmi_editText, bmi)
    }

    fun checkIbw(ibw: String) {
        checkText(R.id.anthro_ibw_editText, ibw)
    }

    fun checkPercentIbw(percentIbw: String) {
        checkText(R.id.anthro_percent_ibw_editText, percentIbw)
    }

    fun checkAdjustedBw(adjustedBw: String) {
        checkText(R.id.anthro_adjusted_bw_editText, adjustedBw)
    }
    //endregion

    //region Error validation
    fun <T : Activity> checkWeightNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(rule, R.id.anthro_weight_editText, R.string.error_enter_a_number)
    }

    fun <T : Activity> checkHeightNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(rule, R.id.anthro_height_editText, R.string.error_enter_a_number)
    }

    fun checkWeightNoError() {
        checkEditTextNoError(R.id.anthro_weight_editText)
    }

    fun checkHeightNoError() {
        checkEditTextNoError(R.id.anthro_height_editText)
    }
    //endregion
}

fun withAnthropometricsRobot(fn: AnthropometricsRobot.() -> Unit) = AnthropometricsRobot().apply(fn)