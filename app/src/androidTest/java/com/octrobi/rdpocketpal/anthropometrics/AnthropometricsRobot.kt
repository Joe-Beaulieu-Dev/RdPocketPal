package com.octrobi.rdpocketpal.anthropometrics

import androidx.annotation.IdRes
import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.testutil.TestRobot

class AnthropometricsRobot : TestRobot() {

    //region Sex
    fun setSexMale() {
        clickView(R.id.anthro_sex_male)
    }

    fun setSexFemale() {
        clickView(R.id.anthro_sex_female)
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
        clickView(R.id.anthro_unit_metric)
    }

    fun setUnitStandard() {
        clickView(R.id.anthro_unit_standard)
    }

    fun checkUnitMetric() {
        checkRadioBtnIsChecked(R.id.anthro_unit_metric)
    }

    fun checkUnitStandard() {
        checkRadioBtnIsChecked(R.id.anthro_unit_standard)
    }

    fun checkUnitMetricWeight() {
        checkSuffixText(R.id.anthro_weight_layout, R.string.text_kg)
    }

    fun checkUnitMetricHeight() {
        checkSuffixText(R.id.anthro_height_layout, R.string.text_cm)
    }

    fun checkUnitMetricBmi() {
        checkSuffixText(R.id.anthro_bmi_layout, R.string.text_kg_per_m2)
    }

    fun checkUnitMetricIbw() {
        checkSuffixText(R.id.anthro_ibw_layout, R.string.text_kg)
    }

    fun checkUnitMetricPercentIbw() {
        checkSuffixText(R.id.anthro_percent_ibw_layout, R.string.text_percent_sign)
    }

    fun checkUnitMetricAdjustedBw() {
        checkSuffixText(R.id.anthro_adjusted_bw_layout, R.string.text_kg)
    }

    fun checkUnitStandardWeight() {
        checkSuffixText(R.id.anthro_weight_layout, R.string.text_lb)
    }

    fun checkUnitStandardHeight() {
        checkSuffixText(R.id.anthro_height_layout, R.string.text_in)
    }

    fun checkUnitStandardBmi() {
        checkSuffixText(R.id.anthro_bmi_layout, R.string.text_kg_per_m2)
    }

    fun checkUnitStandardIbw() {
        checkSuffixText(R.id.anthro_ibw_layout, R.string.text_lb)
    }

    fun checkUnitStandardPercentIbw() {
        checkSuffixText(R.id.anthro_percent_ibw_layout, R.string.text_percent_sign)
    }

    fun checkUnitStandardAdjustedBw() {
        checkSuffixText(R.id.anthro_adjusted_bw_layout, R.string.text_lb)
    }
    //endregion

    //region Buttons
    fun clickClear() {
        clickView(R.id.anthro_clear_btn)
    }

    fun clickCalculate() {
        clickView(R.id.anthro_calculate_btn)
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
        clickView(R.id.anthro_height_editText)
    }

    fun leaveHeightField() {
        clickView(R.id.anthro_weight_editText)
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
    fun checkWeightNanError() {
        checkErrorText(R.id.anthro_weight_layout, R.string.error_enter_a_number)
    }

    fun checkHeightNanError() {
        checkErrorText(R.id.anthro_height_layout, R.string.error_enter_a_number)
    }

    fun checkWeightNoError() {
        checkNoErrorText(R.id.anthro_weight_layout)
    }

    fun checkHeightNoError() {
        checkNoErrorText(R.id.anthro_height_layout)
    }
    //endregion
}

fun withAnthropometricsRobot(fn: AnthropometricsRobot.() -> Unit) = AnthropometricsRobot().apply(fn)