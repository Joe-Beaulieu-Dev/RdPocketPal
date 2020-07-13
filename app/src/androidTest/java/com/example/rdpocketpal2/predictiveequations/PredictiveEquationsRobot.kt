package com.example.rdpocketpal2.predictiveequations

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.testutil.TestRobot

class PredictiveEquationsRobot : TestRobot() {

    //region Spinner
    fun <T : Activity> selectMifflin(activityRule: ActivityTestRule<T>) {
        clickSpinnerItem(activityRule, R.id.pe_equation_spinner, R.string.mifflin_st_jear)
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
    //endregion

    //region Units
    fun setUnitMetric() {
        clickViewId(R.id.pe_unit_metric)
    }

    fun setUnitStandard() {
        clickViewId(R.id.pe_unit_standard)
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
    //endregion

    //region Validation
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
}

fun withPredictiveEquationsRobot(fn: PredictiveEquationsRobot.() -> Unit)
        = PredictiveEquationsRobot().apply(fn)