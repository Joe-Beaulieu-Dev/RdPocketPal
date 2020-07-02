package com.example.rdpocketpal2.quickmethod

import android.app.Activity
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.pressBack
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.testutil.TestRobot
import com.example.rdpocketpal2.testutil.TestUtil

//region Testing constants
const val EMPTY_STRING = ""
//endregion

class QuickMethodRobot : TestRobot() {

    //region Weight
    fun enterWeight(weight: String) {
        enterText(R.id.qm_weight_editText, weight)
    }

    fun checkWeight(weight: String) {
        checkText(R.id.qm_weight_editText, weight)
    }

    fun <T : Activity> checkWeightNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(R.id.qm_weight_editText
                , TestUtil.getString(rule, R.string.error_enter_a_number))
    }

    fun <T : Activity> checkWeightNoError(rule: ActivityTestRule<T>) {
        checkEditTextError(R.id.qm_weight_editText, null)
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

    //region Calories
    fun enterKcalPerKgMin(kcal: String) {
        enterText(R.id.qm_calorie_kcal_per_kg_min, kcal)
    }

    fun enterKcalPerKgMax(kcal: String) {
        enterText(R.id.qm_calorie_kcal_per_kg_max, kcal)
    }

    fun checkKcalPerDayMin(kcal: String) {
        checkText(R.id.qm_calorie_kcal_per_day_min, kcal)
    }

    fun checkKcalPerDayMax(kcal: String) {
        checkText(R.id.qm_calorie_kcal_per_day_max, kcal)
    }

    fun <T : Activity> checkKcalMinNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(R.id.qm_calorie_kcal_per_kg_min
                , TestUtil.getString(rule, R.string.error_enter_a_number))
    }

    fun <T : Activity> checkKcalMaxNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(R.id.qm_calorie_kcal_per_kg_max
                , TestUtil.getString(rule, R.string.error_enter_a_number))
    }

    fun <T : Activity> checkKcalMinNoError(rule: ActivityTestRule<T>) {
        checkEditTextError(R.id.qm_calorie_kcal_per_kg_min, null)
    }

    fun <T : Activity> checkKcalMaxNoError(rule: ActivityTestRule<T>) {
        checkEditTextError(R.id.qm_calorie_kcal_per_kg_max, null)
    }

    fun setCalorieTextProgrammatically() {
        setTextProgrammatically(R.id.qm_calorie_kcal_per_kg_min, "1")
        setTextProgrammatically(R.id.qm_calorie_kcal_per_kg_max, "2")
        setTextProgrammatically(R.id.qm_calorie_kcal_per_day_min, "3")
        setTextProgrammatically(R.id.qm_calorie_kcal_per_day_max, "4")
    }

    fun checkProgrammaticallySetCalorieFields() {
        checkText(R.id.qm_calorie_kcal_per_kg_min, "1")
        checkText(R.id.qm_calorie_kcal_per_kg_max, "2")
        checkText(R.id.qm_calorie_kcal_per_day_min, "3")
        checkText(R.id.qm_calorie_kcal_per_day_max, "4")
    }

    fun checkCalorieFieldsClear() {
        checkText(R.id.qm_calorie_kcal_per_kg_min, EMPTY_STRING)
        checkText(R.id.qm_calorie_kcal_per_kg_max, EMPTY_STRING)
        checkText(R.id.qm_calorie_kcal_per_day_min, EMPTY_STRING)
        checkText(R.id.qm_calorie_kcal_per_day_max, EMPTY_STRING)
    }

    fun clickCalorieClear() {
        clickViewId(R.id.qm_calorie_clear_btn)
    }

    fun clickCalorieCalculate() {
        clickViewId(R.id.qm_calorie_calculate_btn)
    }
    //endregion

    //region Protein
    fun enterGramsPerKgMin(kcal: String) {
        enterText(R.id.qm_protein_grams_per_kg_min, kcal)
    }

    fun enterGramsPerKgMax(kcal: String) {
        enterText(R.id.qm_protein_grams_per_kg_max, kcal)
    }

    fun checkGramsPerDayMin(kcal: String) {
        checkText(R.id.qm_protein_grams_per_day_min, kcal)
    }

    fun checkGramsPerDayMax(kcal: String) {
        checkText(R.id.qm_protein_grams_per_day_max, kcal)
    }

    fun <T : Activity> checkGramsMinNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(R.id.qm_protein_grams_per_kg_min
                , TestUtil.getString(rule, R.string.error_enter_a_number))
    }

    fun <T : Activity> checkGramsMaxNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(R.id.qm_protein_grams_per_kg_max
                , TestUtil.getString(rule, R.string.error_enter_a_number))
    }

    fun <T : Activity> checkGramsMinNoError(rule: ActivityTestRule<T>) {
        checkEditTextError(R.id.qm_protein_grams_per_kg_min, null)
    }

    fun <T : Activity> checkGramsMaxNoError(rule: ActivityTestRule<T>) {
        checkEditTextError(R.id.qm_protein_grams_per_kg_max, null)
    }

    fun setProteinTextProgrammatically() {
        setTextProgrammatically(R.id.qm_protein_grams_per_kg_min, "1")
        setTextProgrammatically(R.id.qm_protein_grams_per_kg_max, "2")
        setTextProgrammatically(R.id.qm_protein_grams_per_day_min, "3")
        setTextProgrammatically(R.id.qm_protein_grams_per_day_max, "4")
    }

    fun checkProgrammaticallySetProteinFields() {
        checkText(R.id.qm_protein_grams_per_kg_min, "1")
        checkText(R.id.qm_protein_grams_per_kg_max, "2")
        checkText(R.id.qm_protein_grams_per_day_min, "3")
        checkText(R.id.qm_protein_grams_per_day_max, "4")
    }

    fun checkProteinFieldsClear() {
        checkText(R.id.qm_protein_grams_per_kg_min, EMPTY_STRING)
        checkText(R.id.qm_protein_grams_per_kg_max, EMPTY_STRING)
        checkText(R.id.qm_protein_grams_per_day_min, EMPTY_STRING)
        checkText(R.id.qm_protein_grams_per_day_max, EMPTY_STRING)
    }

    fun clickProteinClear() {
        clickViewId(R.id.qm_protein_clear_btn)
    }

    fun clickProteinCalculate() {
        clickViewId(R.id.qm_protein_calculate_btn)
    }
    //endregion

    //region Fluids
    fun enterMlPerKgMin(kcal: String) {
        enterText(R.id.qm_fluid_ml_per_kg_min, kcal)
    }

    fun enterMlPerKgMax(kcal: String) {
        enterText(R.id.qm_fluid_ml_per_kg_max, kcal)
    }

    fun checkMlPerDayMin(kcal: String) {
        checkText(R.id.qm_fluid_ml_per_day_min, kcal)
    }

    fun checkMlPerDayMax(kcal: String) {
        checkText(R.id.qm_fluid_ml_per_day_max, kcal)
    }

    fun <T : Activity> checkMlMinNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(R.id.qm_fluid_ml_per_kg_min
                , TestUtil.getString(rule, R.string.error_enter_a_number))
    }

    fun <T : Activity> checkMlMaxNanError(rule: ActivityTestRule<T>) {
        checkEditTextError(R.id.qm_fluid_ml_per_kg_max
                , TestUtil.getString(rule, R.string.error_enter_a_number))
    }

    fun <T : Activity> checkMlMinNoError(rule: ActivityTestRule<T>) {
        checkEditTextError(R.id.qm_fluid_ml_per_kg_min, null)
    }

    fun <T : Activity> checkMlMaxNoError(rule: ActivityTestRule<T>) {
        checkEditTextError(R.id.qm_fluid_ml_per_kg_max, null)
    }

    fun setFluidTextProgrammatically() {
        setTextProgrammatically(R.id.qm_fluid_ml_per_kg_min, "1")
        setTextProgrammatically(R.id.qm_fluid_ml_per_kg_max, "2")
        setTextProgrammatically(R.id.qm_fluid_ml_per_day_min, "3")
        setTextProgrammatically(R.id.qm_fluid_ml_per_day_max, "4")
    }

    fun checkProgrammaticallySetFluidFields() {
        checkText(R.id.qm_fluid_ml_per_kg_min, "1")
        checkText(R.id.qm_fluid_ml_per_kg_max, "2")
        checkText(R.id.qm_fluid_ml_per_day_min, "3")
        checkText(R.id.qm_fluid_ml_per_day_max, "4")
    }

    fun checkFluidFieldsClear() {
        checkText(R.id.qm_fluid_ml_per_kg_min, EMPTY_STRING)
        checkText(R.id.qm_fluid_ml_per_kg_max, EMPTY_STRING)
        checkText(R.id.qm_fluid_ml_per_day_min, EMPTY_STRING)
        checkText(R.id.qm_fluid_ml_per_day_max, EMPTY_STRING)
    }

    fun clickFluidClear() {
        clickViewId(R.id.qm_fluid_clear_btn)
    }

    fun clickFluidCalculate() {
        clickViewId(R.id.qm_fluid_calculate_btn)
    }
    //endregion
}