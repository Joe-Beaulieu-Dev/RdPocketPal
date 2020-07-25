package com.example.rdpocketpal2.predictiveequations

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.testutil.EMPTY_STRING
import com.example.rdpocketpal2.testutil.TestUtil
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//region Test data
// User input values
const val WEIGHT_METRIC = "75.0"
const val WEIGHT_STANDARD = "165.3466966387"
const val HEIGHT_METRIC = "175.0"
const val HEIGHT_STANDARD = "68.8976377953"
const val AGE = "25"
const val TMAX_METRIC = "37.0"
const val TMAX_STANDARD = "98.6"
const val HEART_RATE = "75.0"
const val VE_METRIC = "7.0"
const val ACTIVITY_FACTOR_MIN = "1"
const val ACTIVITY_FACTOR_MAX = "2"

// Mifflin values
const val MIFFLIN_MALE_ANSWER_BMR = "1723.75"
const val MIFFLIN_MALE_ANSWER_MIN = "1723.75"
const val MIFFLIN_MALE_ANSWER_MAX = "3447.5"
const val MIFFLIN_FEMALE_ANSWER_BMR = "1557.75"
const val MIFFLIN_FEMALE_ANSWER_MIN = "1557.75"
const val MIFFLIN_FEMALE_ANSWER_MAX = "3115.5"

// Benedict values
const val BENEDICT_MALE_ANSWER_BMR = "1798.5"
const val BENEDICT_MALE_ANSWER_MIN = "1798.5"
const val BENEDICT_MALE_ANSWER_MAX = "3597"
const val BENEDICT_FEMALE_ANSWER_BMR = "1572.5"
const val BENEDICT_FEMALE_ANSWER_MIN = "1572.5"
const val BENEDICT_FEMALE_ANSWER_MAX = "3145"

// Penn State 2003b values
const val PENN_STATE_2003b_MALE_ANSWER_BMR = "1838.8"
const val PENN_STATE_2003b_MALE_ANSWER_MIN = "1838.8"
const val PENN_STATE_2003b_MALE_ANSWER_MAX = "3677.6"
const val PENN_STATE_2003b_FEMALE_ANSWER_BMR = "1679.44"
const val PENN_STATE_2003b_FEMALE_ANSWER_MIN = "1679.44"
const val PENN_STATE_2003b_FEMALE_ANSWER_MAX = "3358.88"

// Penn State 2010 values
const val PENN_STATE_2010_MALE_ANSWER_BMR = "1731.86"
const val PENN_STATE_2010_MALE_ANSWER_MIN = "1731.86"
const val PENN_STATE_2010_MALE_ANSWER_MAX = "3463.73"
const val PENN_STATE_2010_FEMALE_ANSWER_BMR = "1614"
const val PENN_STATE_2010_FEMALE_ANSWER_MIN = "1614"
const val PENN_STATE_2010_FEMALE_ANSWER_MAX = "3228.01"

// Brandi values
const val BRANDI_ANSWER_MALE_BMR = "1885.56"
const val BRANDI_ANSWER_MALE_MIN = "1885.56"
const val BRANDI_ANSWER_MALE_MAX = "3771.12"
const val BRANDI_ANSWER_FEMALE_BMR = "1668.6"
const val BRANDI_ANSWER_FEMALE_MIN = "1668.6"
const val BRANDI_ANSWER_FEMALE_MAX = "3337.2"
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
            enterWeight(WEIGHT_METRIC)
            enterHeight(WEIGHT_METRIC)
            enterAge(AGE)
            enterTmax(WEIGHT_METRIC)
            enterVe(WEIGHT_METRIC)
            enterActivityFactorMin(WEIGHT_METRIC)
            enterActivityFactorMax(WEIGHT_METRIC)
            programmaticallySetBmr(WEIGHT_METRIC)
            programmaticallySetCalorieMin(WEIGHT_METRIC)
            programmaticallySetCalorieMax(WEIGHT_METRIC)
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
            enterHeartRate(WEIGHT_METRIC)
            clickClear()
            // check that fields are empty
            checkHeartRate(EMPTY_STRING)
        }
    }

    @Test
    fun clearFields_sexRadioBtnPress() {
        withPredictiveEquationsRobot {
            setSexMale()
            // entry
            programmaticallySetBmr(WEIGHT_METRIC)
            programmaticallySetCalorieMin(WEIGHT_METRIC)
            programmaticallySetCalorieMax(WEIGHT_METRIC)
            // change sex to female
            setSexFemale()
            // validate fields are empty
            checkBmr(EMPTY_STRING)
            checkCalorieMin(EMPTY_STRING)
            checkCalorieMax(EMPTY_STRING)
            // entry
            programmaticallySetBmr(WEIGHT_METRIC)
            programmaticallySetCalorieMin(WEIGHT_METRIC)
            programmaticallySetCalorieMax(WEIGHT_METRIC)
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
            programmaticallySetBmr(WEIGHT_METRIC)
            programmaticallySetCalorieMin(WEIGHT_METRIC)
            programmaticallySetCalorieMax(WEIGHT_METRIC)
            // change sex to female
            setUnitStandard()
            // validate fields are empty
            checkBmr(EMPTY_STRING)
            checkCalorieMin(EMPTY_STRING)
            checkCalorieMax(EMPTY_STRING)
            // entry
            programmaticallySetBmr(WEIGHT_METRIC)
            programmaticallySetCalorieMin(WEIGHT_METRIC)
            programmaticallySetCalorieMax(WEIGHT_METRIC)
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
            programmaticallySetBmr(WEIGHT_METRIC)
            programmaticallySetCalorieMin(WEIGHT_METRIC)
            programmaticallySetCalorieMax(WEIGHT_METRIC)
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
//
//    //region Errors
//    @Test
//    fun checkError_weight_notANumber_displays() {
//        withPredictiveEquationsRobot {
//
//        }
//    }
//
//    @Test
//    fun checkError_height_notANumber_displays() {
//        withPredictiveEquationsRobot {
//
//        }
//    }
//
//    @Test
//    fun checkError_age_notANumber_displays() {
//        withPredictiveEquationsRobot {
//
//        }
//    }
//
//    @Test
//    fun checkError_tmax_notANumber_displays() {
//        withPredictiveEquationsRobot {
//
//        }
//    }
//
//    @Test
//    fun checkError_ve_notANumber_displays() {
//        withPredictiveEquationsRobot {
//
//        }
//    }
//
//    @Test
//    fun checkError_heartRate_notANumber_displays() {
//        withPredictiveEquationsRobot {
//
//        }
//    }
//
//    @Test
//    fun checkError_activityFactorMin_notANumber_displays() {
//        withPredictiveEquationsRobot {
//
//        }
//    }
//
//    @Test
//    fun checkError_activityFactorMax_notANumber_displays() {
//        withPredictiveEquationsRobot {
//
//        }
//    }
//
//    @Test
//    fun checkError_weight_noError() {
//        withPredictiveEquationsRobot {
//
//        }
//    }
//
//    @Test
//    fun checkError_height_noError() {
//        withPredictiveEquationsRobot {
//
//        }
//    }
//
//    @Test
//    fun checkError_age_noError() {
//        withPredictiveEquationsRobot {
//
//        }
//    }
//
//    @Test
//    fun checkError_tmax_noError() {
//        withPredictiveEquationsRobot {
//
//        }
//    }
//
//    @Test
//    fun checkError_ve_noError() {
//        withPredictiveEquationsRobot {
//
//        }
//    }
//
//    @Test
//    fun checkError_heartRate_noError() {
//        withPredictiveEquationsRobot {
//
//        }
//    }
//
//    @Test
//    fun checkError_activityFactorMin_noError() {
//        withPredictiveEquationsRobot {
//
//        }
//    }
//
//    @Test
//    fun checkError_activityFactorMax_noError() {
//        withPredictiveEquationsRobot {
//
//        }
//    }
//    //endregion
//
//    //region Field persistence
//    @Test
//    fun fieldPersistence_orientationChange() {
//        withPredictiveEquationsRobot {
//
//        }
//    }
//    //endregion
//
//    //region Preference reaction
//    @Test
//    fun preferenceChangeRefresh() {
//        withPredictiveEquationsRobot {
//
//        }
//    }
//    //endregion
}