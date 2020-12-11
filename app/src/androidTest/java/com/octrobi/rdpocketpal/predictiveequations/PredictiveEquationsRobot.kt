package com.octrobi.rdpocketpal.predictiveequations

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.testutil.TestRobot

class PredictiveEquationsRobot : TestRobot() {

    //region Spinner
    fun <T : Activity> selectMifflin(activityRule: ActivityTestRule<T>) {
        clickSpinnerItem(activityRule, R.id.pe_equation_spinner, R.string.mifflin_st_jeor)
    }

    fun <T : Activity> selectBenedict(activityRule: ActivityTestRule<T>) {
        clickSpinnerItem(activityRule, R.id.pe_equation_spinner, R.string.harris_benedict)
    }

    fun <T : Activity> selectPennState2003b(activityRule: ActivityTestRule<T>) {
        clickSpinnerItem(activityRule, R.id.pe_equation_spinner, R.string.penn_state_2003b)
    }

    fun <T : Activity> selectPennState2010(activityRule: ActivityTestRule<T>) {
        clickSpinnerItem(activityRule, R.id.pe_equation_spinner, R.string.penn_state_2010)
    }

    fun <T : Activity> selectBrandi(activityRule: ActivityTestRule<T>) {
        clickSpinnerItem(activityRule, R.id.pe_equation_spinner, R.string.brandi)
    }
    //endregion

    //region Sex
    fun setSexMale() {
        clickViewId(R.id.pe_sex_male)
    }

    fun setSexFemale() {
        clickViewId(R.id.pe_sex_female)
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
        clickViewId(R.id.pe_unit_metric)
    }

    fun setUnitStandard() {
        clickViewId(R.id.pe_unit_standard)
    }

    fun checkUnitMetric() {
        checkRadioBtnIsChecked(R.id.pe_unit_metric)
    }

    fun checkUnitStandard() {
        checkRadioBtnIsChecked(R.id.pe_unit_standard)
    }

    fun <T : Activity> checkUnitMetricWeight(activityRule: ActivityTestRule<T>) {
        checkText(activityRule, R.id.pe_weight_unit_label, R.string.text_kg)
    }

    fun <T : Activity> checkUnitMetricHeight(activityRule: ActivityTestRule<T>) {
        checkText(activityRule, R.id.pe_height_unit_label, R.string.text_cm)
    }

    fun <T : Activity> checkUnitMetricTmax(activityRule: ActivityTestRule<T>) {
        checkText(activityRule, R.id.pe_tmax_unit_label, R.string.text_celsius)
    }

    fun <T : Activity> checkUnitStandardWeight(activityRule: ActivityTestRule<T>) {
        checkText(activityRule, R.id.pe_weight_unit_label, R.string.text_lb)
    }

    fun <T : Activity> checkUnitStandardHeight(activityRule: ActivityTestRule<T>) {
        checkText(activityRule, R.id.pe_height_unit_label, R.string.text_in)
    }

    fun <T : Activity> checkUnitStandardTmax(activityRule: ActivityTestRule<T>) {
        checkText(activityRule, R.id.pe_tmax_unit_label, R.string.text_fahrenheit)
    }
    //endregion

    //region Buttons
    fun clickClear() {
        clickViewId(R.id.pe_clear_btn)
    }

    fun clickCalculate() {
        clickViewId(R.id.pe_calculate_btn)
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
        clickViewId(R.id.pe_age_editText)
    }

    fun loseFocusToWeight() {
        clickViewId(R.id.pe_weight_editText)
    }

    fun loseFocusToAge() {
        clickViewId(R.id.pe_age_editText)
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
    fun <T : Activity> checkWeightNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(rule, R.id.pe_weight_editText, R.string.error_enter_a_number)
    }

    fun <T : Activity> checkHeightNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(rule, R.id.pe_height_editText, R.string.error_enter_a_number)
    }

    fun <T : Activity> checkAgeNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(rule, R.id.pe_age_editText, R.string.error_enter_a_number)
    }

    fun <T : Activity> checkTmaxNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(rule, R.id.pe_tmax_editText, R.string.error_enter_a_number)
    }

    fun <T : Activity> checkVeNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(rule, R.id.pe_ve_editText, R.string.error_enter_a_number)
    }

    fun <T : Activity> checkHeartRateNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(rule, R.id.pe_heart_rate_editText, R.string.error_enter_a_number)
    }

    fun <T : Activity> checkActivityFactorMinNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(rule, R.id.pe_activity_factor_min_editText, R.string.error_enter_a_number)
    }

    fun <T : Activity> checkActivityFactorMaxNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(rule, R.id.pe_activity_factor_max_editText, R.string.error_enter_a_number)
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