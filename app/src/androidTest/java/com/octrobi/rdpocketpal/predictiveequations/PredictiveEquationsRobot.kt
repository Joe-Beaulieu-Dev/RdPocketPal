package com.octrobi.rdpocketpal.predictiveequations

import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.testutil.TestRobot

class PredictiveEquationsRobot : TestRobot() {

    //region Spinner
    fun selectMifflin() {
        clickSpinnerItem(R.id.pe_equation_spinner, R.string.mifflin_st_jeor)
    }

    fun selectBenedict() {
        clickSpinnerItem(R.id.pe_equation_spinner, R.string.harris_benedict)
    }

    fun selectPennState2003b() {
        clickSpinnerItem(R.id.pe_equation_spinner, R.string.penn_state_2003b)
    }

    fun selectPennState2010() {
        clickSpinnerItem(R.id.pe_equation_spinner, R.string.penn_state_2010)
    }

    fun selectBrandi() {
        clickSpinnerItem(R.id.pe_equation_spinner, R.string.brandi)
    }
    //endregion

    //region Sex
    fun setSexMale() {
        clickView(R.id.pe_sex_male)
    }

    fun setSexFemale() {
        clickView(R.id.pe_sex_female)
    }

    fun checkSexMale() {
        checkRadioBtnIsChecked(R.id.pe_sex_male)
    }

    fun checkSexFemale() {
        checkRadioBtnIsChecked(R.id.pe_sex_female)
    }
    //endregion

    //region Units
    fun setUnitMetric() {
        clickView(R.id.pe_unit_metric)
    }

    fun setUnitStandard() {
        clickView(R.id.pe_unit_standard)
    }

    fun checkUnitMetric() {
        checkRadioBtnIsChecked(R.id.pe_unit_metric)
    }

    fun checkUnitStandard() {
        checkRadioBtnIsChecked(R.id.pe_unit_standard)
    }

    fun checkUnitMetricWeight() {
        checkText(R.id.pe_weight_unit_label, R.string.text_kg)
    }

    fun checkUnitMetricHeight() {
        checkText(R.id.pe_height_unit_label, R.string.text_cm)
    }

    fun checkUnitMetricTmax() {
        checkText(R.id.pe_tmax_unit_label, R.string.text_celsius)
    }

    fun checkUnitStandardWeight() {
        checkText(R.id.pe_weight_unit_label, R.string.text_lb)
    }

    fun checkUnitStandardHeight() {
        checkText(R.id.pe_height_unit_label, R.string.text_in)
    }

    fun checkUnitStandardTmax() {
        checkText(R.id.pe_tmax_unit_label, R.string.text_fahrenheit)
    }
    //endregion

    //region Buttons
    fun clickClear() {
        clickView(R.id.pe_clear_btn)
    }

    fun clickCalculate() {
        clickView(R.id.pe_calculate_btn)
    }
    //endregion

    //region Entry
    fun enterWeight(string: String) {
        enterText(R.id.pe_weight_editText, string)
    }

    fun enterHeight(string: String) {
        enterText(R.id.pe_height_editText, string)
    }

    fun enterAge(string: String) {
        enterText(R.id.pe_age_editText, string)
    }

    fun enterTmax(string: String) {
        enterText(R.id.pe_tmax_editText, string)
    }

    fun enterVe(string: String) {
        enterText(R.id.pe_ve_editText, string)
    }

    fun enterHeartRate(string: String) {
        enterText(R.id.pe_heart_rate_editText, string)
    }

    fun enterActivityFactorMin(string: String) {
        enterText(R.id.pe_activity_factor_min_editText, string)
    }

    fun enterActivityFactorMax(string: String) {
        enterText(R.id.pe_activity_factor_max_editText, string)
    }

    fun programmaticallySetAge(string: String) {
        setTextProgrammatically(R.id.pe_age_editText, string)
    }

    fun programmaticallySetBmr(string: String) {
        setTextProgrammatically(R.id.pe_bmr_editText, string)
    }

    fun programmaticallySetCalorieMin(string: String) {
        setTextProgrammatically(R.id.pe_calorie_min_editText, string)
    }

    fun programmaticallySetCalorieMax(string: String) {
        setTextProgrammatically(R.id.pe_calorie_max_editText, string)
    }

    fun giveFocusToAge() {
        clickView(R.id.pe_age_editText)
    }

    fun loseFocusToWeight() {
        clickView(R.id.pe_weight_editText)
    }

    fun loseFocusToAge() {
        clickView(R.id.pe_age_editText)
    }
    //endregion

    //region Field validation
    fun checkWeight(string: String) {
        checkText(R.id.pe_weight_editText, string)
    }

    fun checkHeight(string: String) {
        checkText(R.id.pe_height_editText, string)
    }

    fun checkAge(string: String) {
        checkText(R.id.pe_age_editText, string)
    }

    fun checkTmax(string: String) {
        checkText(R.id.pe_tmax_editText, string)
    }

    fun checkVe(string: String) {
        checkText(R.id.pe_ve_editText, string)
    }

    fun checkHeartRate(string: String) {
        checkText(R.id.pe_heart_rate_editText, string)
    }

    fun checkActivityFactorMin(string: String) {
        checkText(R.id.pe_activity_factor_min_editText, string)
    }

    fun checkActivityFactorMax(string: String) {
        checkText(R.id.pe_activity_factor_max_editText, string)
    }

    fun checkBmr(string: String) {
        checkText(R.id.pe_bmr_editText, string)
    }

    fun checkCalorieMin(string: String) {
        checkText(R.id.pe_calorie_min_editText, string)
    }

    fun checkCalorieMax(string: String) {
        checkText(R.id.pe_calorie_max_editText, string)
    }
    //endregion

    //region Error validation
    fun checkWeightNanError() {
        checkEditTextError(R.id.pe_weight_editText, R.string.error_enter_a_number)
    }

    fun checkHeightNanError() {
        checkEditTextError(R.id.pe_height_editText, R.string.error_enter_a_number)
    }

    fun checkAgeNanError() {
        checkEditTextError(R.id.pe_age_editText, R.string.error_enter_a_number)
    }

    fun checkTmaxNanError() {
        checkEditTextError(R.id.pe_tmax_editText, R.string.error_enter_a_number)
    }

    fun checkVeNanError() {
        checkEditTextError(R.id.pe_ve_editText, R.string.error_enter_a_number)
    }

    fun checkHeartRateNanError() {
        checkEditTextError(R.id.pe_heart_rate_editText, R.string.error_enter_a_number)
    }

    fun checkActivityFactorMinNanError() {
        checkEditTextError(R.id.pe_activity_factor_min_editText, R.string.error_enter_a_number)
    }

    fun checkActivityFactorMaxNanError() {
        checkEditTextError(R.id.pe_activity_factor_max_editText, R.string.error_enter_a_number)
    }

    fun checkWeightNoError() {
        checkEditTextNoError(R.id.pe_weight_editText)
    }

    fun checkHeightNoError() {
        checkEditTextNoError(R.id.pe_height_editText)
    }

    fun checkAgeNoError() {
        checkEditTextNoError(R.id.pe_age_editText)
    }

    fun checkTmaxNoError() {
        checkEditTextNoError(R.id.pe_tmax_editText)
    }

    fun checkVeNoError() {
        checkEditTextNoError(R.id.pe_ve_editText)
    }

    fun checkHeartRateNoError() {
        checkEditTextNoError(R.id.pe_heart_rate_editText)
    }

    fun checkActivityFactorMinNoError() {
        checkEditTextNoError(R.id.pe_activity_factor_min_editText)
    }

    fun checkActivityFactorMaxNoError() {
        checkEditTextNoError(R.id.pe_activity_factor_max_editText)
    }
    //endregion
}

fun withPredictiveEquationsRobot(fn: PredictiveEquationsRobot.() -> Unit)
        = PredictiveEquationsRobot().apply(fn)