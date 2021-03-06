package com.octrobi.rdpocketpal.predictiveequations

import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.disclaimer.withDisclaimerActivityRobot
import com.octrobi.rdpocketpal.settings.withSettingsRobot
import com.octrobi.rdpocketpal.testutil.EMPTY_STRING
import com.octrobi.rdpocketpal.testutil.INVALID_ENTRY_NOT_A_NUMBER
import com.octrobi.rdpocketpal.testutil.TestUtil
import com.octrobi.rdpocketpal.testutil.VALID_ENTRY_INT_STRING
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//region Test data
// Input values
private const val WEIGHT_METRIC = "75.0"
private const val WEIGHT_STANDARD = "165.3466966387"
private const val HEIGHT_METRIC = "175.0"
private const val HEIGHT_STANDARD = "68.8976377953"
private const val AGE = "25"
private const val TMAX_METRIC = "37.0"
private const val TMAX_STANDARD = "98.6"
private const val HEART_RATE = "75.0"
private const val VE_METRIC = "7.0"
private const val ACTIVITY_FACTOR_MIN = "1"
private const val ACTIVITY_FACTOR_MAX = "2"

// Mifflin values
private const val MIFFLIN_MALE_ANSWER_BMR = "1723.75"
private const val MIFFLIN_MALE_ANSWER_MIN = "1723.75"
private const val MIFFLIN_MALE_ANSWER_MAX = "3447.5"
private const val MIFFLIN_FEMALE_ANSWER_BMR = "1557.75"
private const val MIFFLIN_FEMALE_ANSWER_MIN = "1557.75"
private const val MIFFLIN_FEMALE_ANSWER_MAX = "3115.5"

// Benedict values
private const val BENEDICT_MALE_ANSWER_BMR = "1802.25"
private const val BENEDICT_MALE_ANSWER_MIN = "1802.25"
private const val BENEDICT_MALE_ANSWER_MAX = "3604.5"
private const val BENEDICT_FEMALE_ANSWER_BMR = "1572.5"
private const val BENEDICT_FEMALE_ANSWER_MIN = "1572.5"
private const val BENEDICT_FEMALE_ANSWER_MAX = "3145"

// Penn State 2003b values
private const val PENN_STATE_2003b_MALE_ANSWER_BMR = "1838.8"
private const val PENN_STATE_2003b_MALE_ANSWER_MIN = "1838.8"
private const val PENN_STATE_2003b_MALE_ANSWER_MAX = "3677.6"
private const val PENN_STATE_2003b_FEMALE_ANSWER_BMR = "1679.44"
private const val PENN_STATE_2003b_FEMALE_ANSWER_MIN = "1679.44"
private const val PENN_STATE_2003b_FEMALE_ANSWER_MAX = "3358.88"

// Penn State 2010 values
private const val PENN_STATE_2010_MALE_ANSWER_BMR = "1731.86"
private const val PENN_STATE_2010_MALE_ANSWER_MIN = "1731.86"
private const val PENN_STATE_2010_MALE_ANSWER_MAX = "3463.73"
private const val PENN_STATE_2010_FEMALE_ANSWER_BMR = "1614"
private const val PENN_STATE_2010_FEMALE_ANSWER_MIN = "1614"
private const val PENN_STATE_2010_FEMALE_ANSWER_MAX = "3228.01"

// Brandi values
private const val BRANDI_ANSWER_MALE_BMR = "1889.16"
private const val BRANDI_ANSWER_MALE_MIN = "1889.16"
private const val BRANDI_ANSWER_MALE_MAX = "3778.32"
private const val BRANDI_ANSWER_FEMALE_BMR = "1668.6"
private const val BRANDI_ANSWER_FEMALE_MIN = "1668.6"
private const val BRANDI_ANSWER_FEMALE_MAX = "3337.2"
//endregion

@RunWith(AndroidJUnit4::class)
@LargeTest
class PredictiveEquationsTest {

    @get:Rule
    val activityRule: ActivityTestRule<PredictiveEquationsActivity> =
            ActivityTestRule(PredictiveEquationsActivity::class.java)

    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            // set preferences
            TestUtil.setDecimalReductionMethodPref(
                    InstrumentationRegistry.getInstrumentation().targetContext
                    , R.string.key_rounding)
            TestUtil.setNumericScalePref(
                    InstrumentationRegistry.getInstrumentation().targetContext, 2)
        }
    }

    //region Units
    @Test
    fun changeUnitLabels_metric() {
        withPredictiveEquationsRobot {
            selectPennState2003b(activityRule)
            setUnitMetric()
            checkUnitMetricWeight(activityRule)
            checkUnitMetricHeight(activityRule)
            checkUnitMetricTmax(activityRule)
        }
    }

    @Test
    fun changeUnitLabels_standard() {
        withPredictiveEquationsRobot {
            selectPennState2003b(activityRule)
            setUnitStandard()
            checkUnitStandardWeight(activityRule)
            checkUnitStandardHeight(activityRule)
            checkUnitStandardTmax(activityRule)
        }
    }
    //endregion

    //region Calculation
    @Test
    fun checkCalculation_mifflin_metric_male() {
        withPredictiveEquationsRobot {
            // entry
            selectMifflin(activityRule)
            setSexMale()
            setUnitMetric()
            enterWeight(WEIGHT_METRIC)
            enterHeight(HEIGHT_METRIC)
            enterAge(AGE)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(MIFFLIN_MALE_ANSWER_BMR)
            checkCalorieMin(MIFFLIN_MALE_ANSWER_MIN)
            checkCalorieMax(MIFFLIN_MALE_ANSWER_MAX)
        }
    }

    fun checkCalculation_mifflin_metric_female() {
        withPredictiveEquationsRobot {
            // entry
            selectMifflin(activityRule)
            setSexFemale()
            setUnitMetric()
            enterWeight(WEIGHT_METRIC)
            enterHeight(HEIGHT_METRIC)
            enterAge(AGE)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(MIFFLIN_FEMALE_ANSWER_BMR)
            checkCalorieMin(MIFFLIN_FEMALE_ANSWER_MIN)
            checkCalorieMax(MIFFLIN_FEMALE_ANSWER_MAX)
        }
    }

    fun checkCalculation_mifflin_standard_male() {
        withPredictiveEquationsRobot {
            // entry
            selectMifflin(activityRule)
            setSexMale()
            setUnitStandard()
            enterWeight(WEIGHT_STANDARD)
            enterHeight(HEIGHT_STANDARD)
            enterAge(AGE)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(MIFFLIN_MALE_ANSWER_BMR)
            checkCalorieMin(MIFFLIN_MALE_ANSWER_MIN)
            checkCalorieMax(MIFFLIN_MALE_ANSWER_MAX)
        }
    }

    fun checkCalculation_mifflin_standard_female() {
        withPredictiveEquationsRobot {
            // entry
            selectMifflin(activityRule)
            setSexFemale()
            setUnitStandard()
            enterWeight(WEIGHT_STANDARD)
            enterHeight(HEIGHT_STANDARD)
            enterAge(AGE)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(MIFFLIN_FEMALE_ANSWER_BMR)
            checkCalorieMin(MIFFLIN_FEMALE_ANSWER_MIN)
            checkCalorieMax(MIFFLIN_FEMALE_ANSWER_MAX)
        }
    }

    @Test
    fun checkCalculation_benedict_metric_male() {
        withPredictiveEquationsRobot {
            // entry
            selectBenedict(activityRule)
            setSexMale()
            setUnitMetric()
            enterWeight(WEIGHT_METRIC)
            enterHeight(HEIGHT_METRIC)
            enterAge(AGE)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(BENEDICT_MALE_ANSWER_BMR)
            checkCalorieMin(BENEDICT_MALE_ANSWER_MIN)
            checkCalorieMax(BENEDICT_MALE_ANSWER_MAX)
        }
    }

    @Test
    fun checkCalculation_benedict_metric_female() {
        withPredictiveEquationsRobot {
            // entry
            selectBenedict(activityRule)
            setSexFemale()
            setUnitMetric()
            enterWeight(WEIGHT_METRIC)
            enterHeight(HEIGHT_METRIC)
            enterAge(AGE)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(BENEDICT_FEMALE_ANSWER_BMR)
            checkCalorieMin(BENEDICT_FEMALE_ANSWER_MIN)
            checkCalorieMax(BENEDICT_FEMALE_ANSWER_MAX)
        }
    }

    @Test
    fun checkCalculation_benedict_standard_male() {
        withPredictiveEquationsRobot {
            // entry
            selectBenedict(activityRule)
            setSexMale()
            setUnitStandard()
            enterWeight(WEIGHT_STANDARD)
            enterHeight(HEIGHT_STANDARD)
            enterAge(AGE)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(BENEDICT_MALE_ANSWER_BMR)
            checkCalorieMin(BENEDICT_MALE_ANSWER_MIN)
            checkCalorieMax(BENEDICT_MALE_ANSWER_MAX)
        }
    }

    @Test
    fun checkCalculation_benedict_standard_female() {
        withPredictiveEquationsRobot {
            // entry
            selectBenedict(activityRule)
            setSexFemale()
            setUnitStandard()
            enterWeight(WEIGHT_STANDARD)
            enterHeight(HEIGHT_STANDARD)
            enterAge(AGE)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(BENEDICT_FEMALE_ANSWER_BMR)
            checkCalorieMin(BENEDICT_FEMALE_ANSWER_MIN)
            checkCalorieMax(BENEDICT_FEMALE_ANSWER_MAX)
        }
    }

    @Test
    fun checkCalculation_pennState2003b_metric_male() {
        withPredictiveEquationsRobot {
            // entry
            selectPennState2003b(activityRule)
            setSexMale()
            setUnitMetric()
            enterWeight(WEIGHT_METRIC)
            enterHeight(HEIGHT_METRIC)
            enterAge(AGE)
            enterTmax(TMAX_METRIC)
            enterVe(VE_METRIC)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(PENN_STATE_2003b_MALE_ANSWER_BMR)
            checkCalorieMin(PENN_STATE_2003b_MALE_ANSWER_MIN)
            checkCalorieMax(PENN_STATE_2003b_MALE_ANSWER_MAX)
        }
    }

    @Test
    fun checkCalculation_pennState2003b_metric_female() {
        withPredictiveEquationsRobot {
            // entry
            selectPennState2003b(activityRule)
            setSexFemale()
            setUnitMetric()
            enterWeight(WEIGHT_METRIC)
            enterHeight(HEIGHT_METRIC)
            enterAge(AGE)
            enterTmax(TMAX_METRIC)
            enterVe(VE_METRIC)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(PENN_STATE_2003b_FEMALE_ANSWER_BMR)
            checkCalorieMin(PENN_STATE_2003b_FEMALE_ANSWER_MIN)
            checkCalorieMax(PENN_STATE_2003b_FEMALE_ANSWER_MAX)
        }
    }

    @Test
    fun checkCalculation_pennState2003b_standard_male() {
        withPredictiveEquationsRobot {
            // entry
            selectPennState2003b(activityRule)
            setSexMale()
            setUnitStandard()
            enterWeight(WEIGHT_STANDARD)
            enterHeight(HEIGHT_STANDARD)
            enterAge(AGE)
            enterTmax(TMAX_STANDARD)
            enterVe(VE_METRIC)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(PENN_STATE_2003b_MALE_ANSWER_BMR)
            checkCalorieMin(PENN_STATE_2003b_MALE_ANSWER_MIN)
            checkCalorieMax(PENN_STATE_2003b_MALE_ANSWER_MAX)
        }
    }

    @Test
    fun checkCalculation_pennState2003b_standard_female() {
        withPredictiveEquationsRobot {
            // entry
            selectPennState2003b(activityRule)
            setSexFemale()
            setUnitStandard()
            enterWeight(WEIGHT_STANDARD)
            enterHeight(HEIGHT_STANDARD)
            enterAge(AGE)
            enterTmax(TMAX_STANDARD)
            enterVe(VE_METRIC)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(PENN_STATE_2003b_FEMALE_ANSWER_BMR)
            checkCalorieMin(PENN_STATE_2003b_FEMALE_ANSWER_MIN)
            checkCalorieMax(PENN_STATE_2003b_FEMALE_ANSWER_MAX)
        }
    }

    @Test
    fun checkCalculation_pennState2010_metric_male() {
        withPredictiveEquationsRobot {
            // entry
            selectPennState2010(activityRule)
            setSexMale()
            setUnitMetric()
            enterWeight(WEIGHT_METRIC)
            enterHeight(HEIGHT_METRIC)
            enterAge(AGE)
            enterTmax(TMAX_METRIC)
            enterVe(VE_METRIC)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(PENN_STATE_2010_MALE_ANSWER_BMR)
            checkCalorieMin(PENN_STATE_2010_MALE_ANSWER_MIN)
            checkCalorieMax(PENN_STATE_2010_MALE_ANSWER_MAX)
        }
    }

    @Test
    fun checkCalculation_pennState2010_metric_female() {
        withPredictiveEquationsRobot {
            // entry
            selectPennState2010(activityRule)
            setSexFemale()
            setUnitMetric()
            enterWeight(WEIGHT_METRIC)
            enterHeight(HEIGHT_METRIC)
            enterAge(AGE)
            enterTmax(TMAX_METRIC)
            enterVe(VE_METRIC)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(PENN_STATE_2010_FEMALE_ANSWER_BMR)
            checkCalorieMin(PENN_STATE_2010_FEMALE_ANSWER_MIN)
            checkCalorieMax(PENN_STATE_2010_FEMALE_ANSWER_MAX)
        }
    }

    @Test
    fun checkCalculation_pennState2010_standard_male() {
        withPredictiveEquationsRobot {
            // entry
            selectPennState2010(activityRule)
            setSexMale()
            setUnitStandard()
            enterWeight(WEIGHT_STANDARD)
            enterHeight(HEIGHT_STANDARD)
            enterAge(AGE)
            enterTmax(TMAX_STANDARD)
            enterVe(VE_METRIC)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(PENN_STATE_2010_MALE_ANSWER_BMR)
            checkCalorieMin(PENN_STATE_2010_MALE_ANSWER_MIN)
            checkCalorieMax(PENN_STATE_2010_MALE_ANSWER_MAX)
        }
    }

    @Test
    fun checkCalculation_pennState2010_standard_female() {
        withPredictiveEquationsRobot {
            // entry
            selectPennState2010(activityRule)
            setSexFemale()
            setUnitStandard()
            enterWeight(WEIGHT_STANDARD)
            enterHeight(HEIGHT_STANDARD)
            enterAge(AGE)
            enterTmax(TMAX_STANDARD)
            enterVe(VE_METRIC)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(PENN_STATE_2010_FEMALE_ANSWER_BMR)
            checkCalorieMin(PENN_STATE_2010_FEMALE_ANSWER_MIN)
            checkCalorieMax(PENN_STATE_2010_FEMALE_ANSWER_MAX)
        }
    }

    @Test
    fun checkCalculation_brandi_metric_male() {
        withPredictiveEquationsRobot {
            // entry
            selectBrandi(activityRule)
            setSexMale()
            setUnitMetric()
            enterWeight(WEIGHT_METRIC)
            enterHeight(HEIGHT_METRIC)
            enterAge(AGE)
            enterHeartRate(HEART_RATE)
            enterVe(VE_METRIC)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(BRANDI_ANSWER_MALE_BMR)
            checkCalorieMin(BRANDI_ANSWER_MALE_MIN)
            checkCalorieMax(BRANDI_ANSWER_MALE_MAX)
        }
    }

    @Test
    fun checkCalculation_brandi_metric_female() {
        withPredictiveEquationsRobot {
            // entry
            selectBrandi(activityRule)
            setSexFemale()
            setUnitMetric()
            enterWeight(WEIGHT_METRIC)
            enterHeight(HEIGHT_METRIC)
            enterAge(AGE)
            enterHeartRate(HEART_RATE)
            enterVe(VE_METRIC)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(BRANDI_ANSWER_FEMALE_BMR)
            checkCalorieMin(BRANDI_ANSWER_FEMALE_MIN)
            checkCalorieMax(BRANDI_ANSWER_FEMALE_MAX)
        }
    }

    @Test
    fun checkCalculation_brandi_standard_male() {
        withPredictiveEquationsRobot {
            // entry
            selectBrandi(activityRule)
            setSexMale()
            setUnitStandard()
            enterWeight(WEIGHT_STANDARD)
            enterHeight(HEIGHT_STANDARD)
            enterAge(AGE)
            enterHeartRate(HEART_RATE)
            enterVe(VE_METRIC)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(BRANDI_ANSWER_MALE_BMR)
            checkCalorieMin(BRANDI_ANSWER_MALE_MIN)
            checkCalorieMax(BRANDI_ANSWER_MALE_MAX)
        }
    }

    @Test
    fun checkCalculation_brandi_standard_female() {
        withPredictiveEquationsRobot {
            // entry
            selectBrandi(activityRule)
            setSexFemale()
            setUnitStandard()
            enterWeight(WEIGHT_STANDARD)
            enterHeight(HEIGHT_STANDARD)
            enterAge(AGE)
            enterHeartRate(HEART_RATE)
            enterVe(VE_METRIC)
            enterActivityFactorMin(ACTIVITY_FACTOR_MIN)
            enterActivityFactorMax(ACTIVITY_FACTOR_MAX)
            clickCalculate()
            // validation
            checkBmr(BRANDI_ANSWER_FEMALE_BMR)
            checkCalorieMin(BRANDI_ANSWER_FEMALE_MIN)
            checkCalorieMax(BRANDI_ANSWER_FEMALE_MAX)
        }
    }
    //endregion

    //region Clear fields
    @Test
    fun clearFields_ClearBtn() {
        withPredictiveEquationsRobot {
            // initial entry
            selectPennState2003b(activityRule)
            enterWeight(VALID_ENTRY_INT_STRING)
            enterHeight(VALID_ENTRY_INT_STRING)
            enterAge(VALID_ENTRY_INT_STRING)
            enterTmax(VALID_ENTRY_INT_STRING)
            enterVe(VALID_ENTRY_INT_STRING)
            enterActivityFactorMin(VALID_ENTRY_INT_STRING)
            enterActivityFactorMax(VALID_ENTRY_INT_STRING)
            programmaticallySetBmr(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMin(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMax(VALID_ENTRY_INT_STRING)
            clickClear()
            // initial check that fields are empty
            checkWeight(EMPTY_STRING)
            checkHeight(EMPTY_STRING)
            checkAge(EMPTY_STRING)
            checkTmax(EMPTY_STRING)
            checkVe(EMPTY_STRING)
            checkActivityFactorMin(EMPTY_STRING)
            checkActivityFactorMax(EMPTY_STRING)
            checkBmr(EMPTY_STRING)
            checkCalorieMin(EMPTY_STRING)
            checkCalorieMax(EMPTY_STRING)

            // enter fields that don't display in penn state equations
            selectBrandi(activityRule)
            enterHeartRate(VALID_ENTRY_INT_STRING)
            clickClear()
            // check that fields are empty
            checkHeartRate(EMPTY_STRING)
        }
    }

    // TODO fails when run with whole class, but not when run on its own. Doesn't detect Toast
    @Test
    fun clearFields_sexRadioBtnPress() {
        withPredictiveEquationsRobot {
            setSexMale()
            // entry
            programmaticallySetBmr(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMin(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMax(VALID_ENTRY_INT_STRING)
            // change sex to female
            setSexFemale()
            // validate fields are empty
            checkBmr(EMPTY_STRING)
            checkCalorieMin(EMPTY_STRING)
            checkCalorieMax(EMPTY_STRING)
            // check that correct Toast is displayed
            checkToastDisplayedWithMessage(R.string.toast_results_cleared_sex_change)

            // entry
            programmaticallySetBmr(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMin(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMax(VALID_ENTRY_INT_STRING)
            // change sex to male
            setSexMale()
            // validate fields are empty
            checkBmr(EMPTY_STRING)
            checkCalorieMin(EMPTY_STRING)
            checkCalorieMax(EMPTY_STRING)
            // check that correct Toast is displayed
            checkToastDisplayedWithMessage(R.string.toast_results_cleared_sex_change)
        }
    }

    @Test
    fun clearFields_unitRadioBtnPress() {
        withPredictiveEquationsRobot {
            setUnitMetric()
            // entry
            programmaticallySetBmr(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMin(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMax(VALID_ENTRY_INT_STRING)
            // change sex to female
            setUnitStandard()
            // validate fields are empty
            checkBmr(EMPTY_STRING)
            checkCalorieMin(EMPTY_STRING)
            checkCalorieMax(EMPTY_STRING)
            // check that correct Toast is displayed
            checkToastDisplayedWithMessage(R.string.toast_results_cleared_unit_change)

            // entry
            programmaticallySetBmr(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMin(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMax(VALID_ENTRY_INT_STRING)
            // change sex to male
            setUnitMetric()
            // validate fields are empty
            checkBmr(EMPTY_STRING)
            checkCalorieMin(EMPTY_STRING)
            checkCalorieMax(EMPTY_STRING)
            // check that correct Toast is displayed
            checkToastDisplayedWithMessage(R.string.toast_results_cleared_unit_change)
        }
    }

    @Test
    fun clearFields_equationSpinnerSelection() {
        withPredictiveEquationsRobot {
            selectMifflin(activityRule)
            // entry
            programmaticallySetBmr(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMin(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMax(VALID_ENTRY_INT_STRING)
            // change equation
            selectBenedict(activityRule)
            // validate fields are empty
            checkBmr(EMPTY_STRING)
            checkCalorieMin(EMPTY_STRING)
            checkCalorieMax(EMPTY_STRING)
            // check that correct Toast is displayed
            checkToastDisplayedWithMessage(R.string.toast_results_cleared_equation_change)
        }
    }
    //endregion

    //region Errors
    @Test
    fun checkError_noInput_calculationBtn_pennState2003b_allFields_displays() {
        withPredictiveEquationsRobot {
            // setup and calculate
            selectPennState2003b(activityRule)
            clickCalculate()
            // validation
            checkWeightNanError(activityRule)
            checkHeightNanError(activityRule)
            checkAgeNanError(activityRule)
            checkTmaxNanError(activityRule)
            checkVeNanError(activityRule)
            checkActivityFactorMinNanError(activityRule)
            checkActivityFactorMaxNanError(activityRule)
            checkToastDisplayedWithMessage(R.string.toast_invalid_fields)
            // check results
            checkBmr(EMPTY_STRING)
            checkCalorieMin(EMPTY_STRING)
            checkCalorieMax(EMPTY_STRING)
        }
    }

    // TODO fails when run with whole class, but not when run on its own. Doesn't detect Toast
    @Test
    fun checkError_noInput_calculationBtn_brandi_allFields_displays() {
        withPredictiveEquationsRobot {
            // setup and calculate
            selectBrandi(activityRule)
            clickCalculate()
            // validation
            checkWeightNanError(activityRule)
            checkHeightNanError(activityRule)
            checkAgeNanError(activityRule)
            checkHeartRateNanError(activityRule)
            checkVeNanError(activityRule)
            checkActivityFactorMinNanError(activityRule)
            checkActivityFactorMaxNanError(activityRule)
            checkToastDisplayedWithMessage(R.string.toast_invalid_fields)
            // check results
            checkBmr(EMPTY_STRING)
            checkCalorieMin(EMPTY_STRING)
            checkCalorieMax(EMPTY_STRING)
        }
    }

    @Test
    fun checkError_invalidInput_calculationBtn_pennState2003b_allFields_displays() {
        withPredictiveEquationsRobot {
            // setup and calculate
            selectPennState2003b(activityRule)
            enterWeight(INVALID_ENTRY_NOT_A_NUMBER)
            enterHeight(INVALID_ENTRY_NOT_A_NUMBER)
            enterAge(INVALID_ENTRY_NOT_A_NUMBER)
            enterTmax(INVALID_ENTRY_NOT_A_NUMBER)
            enterVe(INVALID_ENTRY_NOT_A_NUMBER)
            enterActivityFactorMin(INVALID_ENTRY_NOT_A_NUMBER)
            enterActivityFactorMax(INVALID_ENTRY_NOT_A_NUMBER)
            clickCalculate()
            // validation
            checkWeightNanError(activityRule)
            checkHeightNanError(activityRule)
            checkAgeNanError(activityRule)
            checkTmaxNanError(activityRule)
            checkVeNanError(activityRule)
            checkActivityFactorMinNanError(activityRule)
            checkActivityFactorMaxNanError(activityRule)
            checkToastDisplayedWithMessage(R.string.toast_invalid_fields)
            // check results
            checkBmr(EMPTY_STRING)
            checkCalorieMin(EMPTY_STRING)
            checkCalorieMax(EMPTY_STRING)
        }
    }

    @Test
    fun checkError_invalidInput_calculationBtn_brandi_allFields_displays() {
        withPredictiveEquationsRobot {
            // setup and calculate
            selectBrandi(activityRule)
            enterWeight(INVALID_ENTRY_NOT_A_NUMBER)
            enterHeight(INVALID_ENTRY_NOT_A_NUMBER)
            enterAge(INVALID_ENTRY_NOT_A_NUMBER)
            enterHeartRate(INVALID_ENTRY_NOT_A_NUMBER)
            enterVe(INVALID_ENTRY_NOT_A_NUMBER)
            enterActivityFactorMin(INVALID_ENTRY_NOT_A_NUMBER)
            enterActivityFactorMax(INVALID_ENTRY_NOT_A_NUMBER)
            clickCalculate()
            // validation
            checkWeightNanError(activityRule)
            checkHeightNanError(activityRule)
            checkAgeNanError(activityRule)
            checkHeartRateNanError(activityRule)
            checkVeNanError(activityRule)
            checkActivityFactorMinNanError(activityRule)
            checkActivityFactorMaxNanError(activityRule)
            checkToastDisplayedWithMessage(R.string.toast_invalid_fields)
            // check results
            checkBmr(EMPTY_STRING)
            checkCalorieMin(EMPTY_STRING)
            checkCalorieMax(EMPTY_STRING)
        }
    }

    @Test
    fun checkError_weight_notANumber_displays() {
        withPredictiveEquationsRobot {
            enterWeight(INVALID_ENTRY_NOT_A_NUMBER)
            loseFocusToAge()
            checkWeightNanError(activityRule)
        }
    }

    @Test
    fun checkError_height_notANumber_displays() {
        withPredictiveEquationsRobot {
            enterHeight(INVALID_ENTRY_NOT_A_NUMBER)
            loseFocusToAge()
            checkHeightNanError(activityRule)
        }
    }

    @Test
    fun checkError_age_notANumber_displays() {
        withPredictiveEquationsRobot {
            // Age field has inputType: number which does not allow non-integer input
            // on the number pad, so invalid input must be set programmatically.
            // Binding adapter validates input on focus change, so focus must be given to
            // the field for the error message to have the potential to be set.
            programmaticallySetAge(".")
            giveFocusToAge()
            loseFocusToWeight()
            checkAgeNanError(activityRule)
        }
    }

    @Test
    fun checkError_tmax_notANumber_displays() {
        withPredictiveEquationsRobot {
            selectPennState2003b(activityRule)
            enterTmax(INVALID_ENTRY_NOT_A_NUMBER)
            loseFocusToAge()
            checkTmaxNanError(activityRule)
        }
    }

    @Test
    fun checkError_ve_notANumber_displays() {
        withPredictiveEquationsRobot {
            selectPennState2003b(activityRule)
            enterVe(INVALID_ENTRY_NOT_A_NUMBER)
            loseFocusToAge()
            checkVeNanError(activityRule)
        }
    }

    @Test
    fun checkError_heartRate_notANumber_displays() {
        withPredictiveEquationsRobot {
            selectBrandi(activityRule)
            enterHeartRate(INVALID_ENTRY_NOT_A_NUMBER)
            loseFocusToAge()
            checkHeartRateNanError(activityRule)
        }
    }

    @Test
    fun checkError_activityFactorMin_notANumber_displays() {
        withPredictiveEquationsRobot {
            enterActivityFactorMin(INVALID_ENTRY_NOT_A_NUMBER)
            loseFocusToAge()
            checkActivityFactorMinNanError(activityRule)
        }
    }

    @Test
    fun checkError_activityFactorMax_notANumber_displays() {
        withPredictiveEquationsRobot {
            enterActivityFactorMax(INVALID_ENTRY_NOT_A_NUMBER)
            loseFocusToAge()
            checkActivityFactorMaxNanError(activityRule)
        }
    }

    @Test
    fun checkError_weight_noError() {
        withPredictiveEquationsRobot {
            enterWeight(VALID_ENTRY_INT_STRING)
            loseFocusToAge()
            checkWeightNoError()
        }
    }

    @Test
    fun checkError_height_noError() {
        withPredictiveEquationsRobot {
            enterHeight(VALID_ENTRY_INT_STRING)
            loseFocusToAge()
            checkHeightNoError()
        }
    }

    @Test
    fun checkError_age_noError() {
        withPredictiveEquationsRobot {
            enterAge(VALID_ENTRY_INT_STRING)
            loseFocusToWeight()
            checkAgeNoError()
        }
    }

    @Test
    fun checkError_tmax_noError() {
        withPredictiveEquationsRobot {
            selectPennState2003b(activityRule)
            enterTmax(VALID_ENTRY_INT_STRING)
            loseFocusToAge()
            checkTmaxNoError()
        }
    }

    @Test
    fun checkError_ve_noError() {
        withPredictiveEquationsRobot {
            selectPennState2003b(activityRule)
            enterVe(VALID_ENTRY_INT_STRING)
            loseFocusToAge()
            checkVeNoError()
        }
    }

    @Test
    fun checkError_heartRate_noError() {
        withPredictiveEquationsRobot {
            selectBrandi(activityRule)
            enterHeartRate(VALID_ENTRY_INT_STRING)
            loseFocusToAge()
            checkHeartRateNoError()
        }
    }

    @Test
    fun checkError_activityFactorMin_noError() {
        withPredictiveEquationsRobot {
            enterActivityFactorMin(VALID_ENTRY_INT_STRING)
            loseFocusToAge()
            checkActivityFactorMinNoError()
        }
    }

    @Test
    fun checkError_activityFactorMax_noError() {
        withPredictiveEquationsRobot {
            enterActivityFactorMax(VALID_ENTRY_INT_STRING)
            loseFocusToAge()
            checkActivityFactorMaxNoError()
        }
    }
    //endregion

    //region Field persistence
    @Test
    fun fieldPersistence_orientationChange() {
        withPredictiveEquationsRobot {
            // entry
            setSexMale()
            setUnitMetric()
            selectPennState2003b(activityRule)
            enterWeight(VALID_ENTRY_INT_STRING)
            enterHeight(VALID_ENTRY_INT_STRING)
            enterAge(VALID_ENTRY_INT_STRING)
            enterTmax(VALID_ENTRY_INT_STRING)
            enterVe(VALID_ENTRY_INT_STRING)
            enterActivityFactorMin(VALID_ENTRY_INT_STRING)
            enterActivityFactorMax(VALID_ENTRY_INT_STRING)
            programmaticallySetBmr(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMin(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMax(VALID_ENTRY_INT_STRING)
            // rotate screen
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            // validation
            checkSexMale()
            checkUnitMetric()
            checkWeight(VALID_ENTRY_INT_STRING)
            checkHeight(VALID_ENTRY_INT_STRING)
            checkAge(VALID_ENTRY_INT_STRING)
            checkTmax(VALID_ENTRY_INT_STRING)
            checkVe(VALID_ENTRY_INT_STRING)
            checkActivityFactorMin(VALID_ENTRY_INT_STRING)
            checkActivityFactorMax(VALID_ENTRY_INT_STRING)
            checkBmr(VALID_ENTRY_INT_STRING)
            checkCalorieMin(VALID_ENTRY_INT_STRING)
            checkCalorieMax(VALID_ENTRY_INT_STRING)

            // enter fields that don't display in penn state equations
            setSexFemale()
            setUnitStandard()
            selectBrandi(activityRule)
            enterHeartRate(VALID_ENTRY_INT_STRING)
            // rotate screen
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            // validation
            checkSexFemale()
            checkUnitStandard()
            checkHeartRate(VALID_ENTRY_INT_STRING)
        }
    }

    @Test
    fun fieldPersistence_openAndCloseSettings() {
        withPredictiveEquationsRobot {
            // entry
            setSexMale()
            setUnitMetric()
            selectPennState2003b(activityRule)
            enterWeight(VALID_ENTRY_INT_STRING)
            enterHeight(VALID_ENTRY_INT_STRING)
            enterAge(VALID_ENTRY_INT_STRING)
            enterTmax(VALID_ENTRY_INT_STRING)
            enterVe(VALID_ENTRY_INT_STRING)
            enterActivityFactorMin(VALID_ENTRY_INT_STRING)
            enterActivityFactorMax(VALID_ENTRY_INT_STRING)
            programmaticallySetBmr(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMin(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMax(VALID_ENTRY_INT_STRING)
            // open and close Settings
            Intents.init()
            openSettings()
            withSettingsRobot {
                checkSettingsActivityIsDisplayed()
                pressBackButton()
            }
            Intents.release()
            // validation
            checkSexMale()
            checkUnitMetric()
            checkWeight(VALID_ENTRY_INT_STRING)
            checkHeight(VALID_ENTRY_INT_STRING)
            checkAge(VALID_ENTRY_INT_STRING)
            checkTmax(VALID_ENTRY_INT_STRING)
            checkVe(VALID_ENTRY_INT_STRING)
            checkActivityFactorMin(VALID_ENTRY_INT_STRING)
            checkActivityFactorMax(VALID_ENTRY_INT_STRING)
            checkBmr(VALID_ENTRY_INT_STRING)
            checkCalorieMin(VALID_ENTRY_INT_STRING)
            checkCalorieMax(VALID_ENTRY_INT_STRING)

            // enter fields that don't display in penn state equations
            setSexFemale()
            setUnitStandard()
            selectBrandi(activityRule)
            enterHeartRate(VALID_ENTRY_INT_STRING)
            // open and close Settings
            Intents.init()
            openSettings()
            withSettingsRobot {
                checkSettingsActivityIsDisplayed()
                pressBackButton()
            }
            Intents.release()
            // validation
            checkSexFemale()
            checkUnitStandard()
            checkHeartRate(VALID_ENTRY_INT_STRING)
        }
    }

    @Test
    fun fieldPersistence_repeatSexSelection() {
        withPredictiveEquationsRobot {
            // entry
            setSexMale()
            selectPennState2003b(activityRule)
            enterWeight(VALID_ENTRY_INT_STRING)
            enterHeight(VALID_ENTRY_INT_STRING)
            enterAge(VALID_ENTRY_INT_STRING)
            enterTmax(VALID_ENTRY_INT_STRING)
            enterVe(VALID_ENTRY_INT_STRING)
            enterActivityFactorMin(VALID_ENTRY_INT_STRING)
            enterActivityFactorMax(VALID_ENTRY_INT_STRING)
            programmaticallySetBmr(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMin(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMax(VALID_ENTRY_INT_STRING)
            setSexMale()
            // validation
            checkWeight(VALID_ENTRY_INT_STRING)
            checkHeight(VALID_ENTRY_INT_STRING)
            checkAge(VALID_ENTRY_INT_STRING)
            checkTmax(VALID_ENTRY_INT_STRING)
            checkVe(VALID_ENTRY_INT_STRING)
            checkActivityFactorMin(VALID_ENTRY_INT_STRING)
            checkActivityFactorMax(VALID_ENTRY_INT_STRING)
            checkBmr(VALID_ENTRY_INT_STRING)
            checkCalorieMin(VALID_ENTRY_INT_STRING)
            checkCalorieMax(VALID_ENTRY_INT_STRING)
            // check Toast not displayed
//            checkToastNotDisplayedWithMessage(R.string.toast_results_cleared_sex_change)

            // enter fields that don't display in penn state equations
            setSexFemale()
            selectBrandi(activityRule)
            enterHeartRate(VALID_ENTRY_INT_STRING)
            setSexFemale()
            //validation
            checkHeartRate(VALID_ENTRY_INT_STRING)
            // check Toast not displayed
//            checkToastNotDisplayedWithMessage(R.string.toast_results_cleared_sex_change)
        }
    }

    @Test
    fun fieldPersistence_repeatUnitSelection() {
        withPredictiveEquationsRobot {
            // entry
            setUnitMetric()
            selectPennState2003b(activityRule)
            enterWeight(VALID_ENTRY_INT_STRING)
            enterHeight(VALID_ENTRY_INT_STRING)
            enterAge(VALID_ENTRY_INT_STRING)
            enterTmax(VALID_ENTRY_INT_STRING)
            enterVe(VALID_ENTRY_INT_STRING)
            enterActivityFactorMin(VALID_ENTRY_INT_STRING)
            enterActivityFactorMax(VALID_ENTRY_INT_STRING)
            programmaticallySetBmr(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMin(VALID_ENTRY_INT_STRING)
            programmaticallySetCalorieMax(VALID_ENTRY_INT_STRING)
            setUnitMetric()
            // validation
            checkWeight(VALID_ENTRY_INT_STRING)
            checkHeight(VALID_ENTRY_INT_STRING)
            checkAge(VALID_ENTRY_INT_STRING)
            checkTmax(VALID_ENTRY_INT_STRING)
            checkVe(VALID_ENTRY_INT_STRING)
            checkActivityFactorMin(VALID_ENTRY_INT_STRING)
            checkActivityFactorMax(VALID_ENTRY_INT_STRING)
            checkBmr(VALID_ENTRY_INT_STRING)
            checkCalorieMin(VALID_ENTRY_INT_STRING)
            checkCalorieMax(VALID_ENTRY_INT_STRING)
            // check Toast not displayed
//            checkToastNotDisplayedWithMessage(R.string.toast_results_cleared_sex_change)

            // enter fields that don't display in penn state equations
            setUnitStandard()
            selectBrandi(activityRule)
            enterHeartRate(VALID_ENTRY_INT_STRING)
            setUnitStandard()
            //validation
            checkHeartRate(VALID_ENTRY_INT_STRING)
            // check Toast not displayed
//            checkToastNotDisplayedWithMessage(R.string.toast_results_cleared_sex_change)
        }
    }
    //endregion

    //region Menu
    @Test
    fun checkMenu_disclaimer_launches() {
        Intents.init()
        withPredictiveEquationsRobot {
            openDisclaimer()
        }
        withDisclaimerActivityRobot {
            checkDisclaimerActivityIsDisplayed()
        }
        Intents.release()
    }

    @Test
    fun checkMenu_settings_launches() {
        Intents.init()
        withPredictiveEquationsRobot {
            openSettings()
        }
        withSettingsRobot {
            checkSettingsActivityIsDisplayed()
        }
        Intents.release()
    }
    //endregion
}