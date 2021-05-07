package com.octrobi.rdpocketpal.anthropometrics

import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
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

//region Test constants
// Input
private const val WEIGHT_METRIC = "75.0"
private const val WEIGHT_STANDARD = "165.3466966387"
private const val HEIGHT_METRIC = "175.0"
private const val HEIGHT_STANDARD = "68.8976377953"
private const val HEIGHT_CHILD_METRIC = "138"
private const val HEIGHT_CHILD_STANDARD = "54.330708661"

// BMI
private const val BMI_ANSWER = "24.49"
// IBW Metric
private const val IBW_MALE_METRIC_ANSWER = "72.3"
private const val IBW_FEMALE_METRIC_ANSWER = "65.54"
private const val IBW_MALE_CHILD_METRIC_ANSWER = "40.37"
private const val IBW_FEMALE_CHILD_METRIC_ANSWER = "38.93"
// IBW Standard
private const val IBW_MALE_STANDARD_ANSWER = "159.39"
private const val IBW_FEMALE_STANDARD_ANSWER = "144.49"
private const val IBW_MALE_CHILD_STANDARD_ANSWER = "88.99"
private const val IBW_FEMALE_CHILD_STANDARD_ANSWER = "85.83"
// Percent IBW
private const val PERCENT_IBW_ANSWER = "103.74"
// Adjusted IBW
private const val ADJUSTED_BW_METRIC_ANSWER = "72.97"
private const val ADJUSTED_BW_STANDARD_ANSWER = "160.88"
//endregion

@RunWith(AndroidJUnit4::class)
@LargeTest
class AnthropometricsTest {

    @get:Rule
    val activityTestRule: ActivityTestRule<AnthropometricsActivity> =
            ActivityTestRule(AnthropometricsActivity::class.java)

    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            // set preferences
            TestUtil.setDecimalReductionMethodPref(R.string.key_rounding)
            TestUtil.setNumericScalePref(2)
        }
    }

    //region Units
    @Test
    fun changeUnitLabels_metric() {
        withAnthropometricsRobot {
            setUnitStandard()
            setUnitMetric()
            checkUnitMetricWeight()
            checkUnitMetricHeight()
            checkUnitMetricBmi()
            checkUnitMetricIbw()
            checkUnitMetricPercentIbw()
            checkUnitMetricAdjustedBw()
        }
    }

    @Test
    fun changeUnitLabels_standard() {
        withAnthropometricsRobot {
            setUnitMetric()
            setUnitStandard()
            checkUnitStandardHeight()
            checkUnitStandardWeight()
            checkUnitStandardBmi()
            checkUnitStandardIbw()
            checkUnitStandardPercentIbw()
            checkUnitStandardAdjustedBw()
        }
    }
    //endregion

    //region Calculation
    @Test
    fun calculate_bmi_metric() {
        // sex not relevant for bmi, use default
        withAnthropometricsRobot {
            setUnitMetric()
            enterWeight(WEIGHT_METRIC)
            enterHeight(HEIGHT_METRIC)
            clickCalculate()
            checkBmi(BMI_ANSWER)
        }
    }

    @Test
    fun calculate_bmi_standard() {
        // sex not relevant for bmi, use default
        withAnthropometricsRobot {
            setUnitStandard()
            enterWeight(WEIGHT_STANDARD)
            enterHeight(HEIGHT_STANDARD)
            clickCalculate()
            checkBmi(BMI_ANSWER)
        }
    }

    @Test
    fun calculate_ibw_metric_male_adult() {
        withAnthropometricsRobot {
            setSexMale()
            setUnitMetric()
            enterWeight(WEIGHT_METRIC)
            enterHeight(HEIGHT_METRIC)
            clickCalculate()
            checkIbw(IBW_MALE_METRIC_ANSWER)
        }
    }

    @Test
    fun calculate_ibw_metric_male_child() {
        withAnthropometricsRobot {
            setSexMale()
            setUnitMetric()
            enterWeight(WEIGHT_METRIC)
            enterHeight(HEIGHT_CHILD_METRIC)
            clickCalculate()
            checkIbw(IBW_MALE_CHILD_METRIC_ANSWER)
        }
    }

    @Test
    fun calculate_ibw_metric_female_adult() {
        withAnthropometricsRobot {
            setSexFemale()
            setUnitMetric()
            enterWeight(WEIGHT_METRIC)
            enterHeight(HEIGHT_METRIC)
            clickCalculate()
            checkIbw(IBW_FEMALE_METRIC_ANSWER)
        }
    }

    @Test
    fun calculate_ibw_metric_female_child() {
        withAnthropometricsRobot {
            setSexFemale()
            setUnitMetric()
            enterWeight(WEIGHT_METRIC)
            enterHeight(HEIGHT_CHILD_METRIC)
            clickCalculate()
            checkIbw(IBW_FEMALE_CHILD_METRIC_ANSWER)
        }
    }

    @Test
    fun calculate_ibw_standard_male_adult() {
        withAnthropometricsRobot {
            setSexMale()
            setUnitStandard()
            enterWeight(WEIGHT_STANDARD)
            enterHeight(HEIGHT_STANDARD)
            clickCalculate()
            checkIbw(IBW_MALE_STANDARD_ANSWER)
        }
    }

    @Test
    fun calculate_ibw_standard_male_child() {
        withAnthropometricsRobot {
            setSexMale()
            setUnitStandard()
            enterWeight(WEIGHT_STANDARD)
            enterHeight(HEIGHT_CHILD_STANDARD)
            clickCalculate()
            checkIbw(IBW_MALE_CHILD_STANDARD_ANSWER)
        }
    }

    @Test
    fun calculate_ibw_standard_female_adult() {
        withAnthropometricsRobot {
            setSexFemale()
            setUnitStandard()
            enterWeight(WEIGHT_STANDARD)
            enterHeight(HEIGHT_STANDARD)
            clickCalculate()
            checkIbw(IBW_FEMALE_STANDARD_ANSWER)
        }
    }

    @Test
    fun calculate_ibw_standard_female_child() {
        withAnthropometricsRobot {
            setSexFemale()
            setUnitStandard()
            enterWeight(WEIGHT_STANDARD)
            enterHeight(HEIGHT_CHILD_STANDARD)
            clickCalculate()
            checkIbw(IBW_FEMALE_CHILD_STANDARD_ANSWER)
        }
    }

    @Test
    fun calculate_percentIbw_metric() {
        withAnthropometricsRobot {
            // sex not relevant for percent ibw, use default
            setUnitMetric()
            enterWeight(WEIGHT_METRIC)
            enterHeight(HEIGHT_METRIC)
            clickCalculate()
            checkPercentIbw(PERCENT_IBW_ANSWER)
        }
    }

    @Test
    fun calculate_percentIbw_standard() {
        withAnthropometricsRobot {
            // sex not relevant for percent ibw, use default
            setUnitStandard()
            enterWeight(WEIGHT_STANDARD)
            enterHeight(HEIGHT_STANDARD)
            clickCalculate()
            checkPercentIbw(PERCENT_IBW_ANSWER)
        }
    }

    @Test
    fun calculate_adjustedBw_metric() {
        withAnthropometricsRobot {
            // sex not relevant for adjusted ibw, use default
            setUnitMetric()
            enterWeight(WEIGHT_METRIC)
            enterHeight(HEIGHT_METRIC)
            clickCalculate()
            checkAdjustedBw(ADJUSTED_BW_METRIC_ANSWER)
        }
    }

    @Test
    fun calculate_adjustedBw_standard() {
        withAnthropometricsRobot {
            // sex not relevant for adjusted ibw, use default
            setUnitStandard()
            enterWeight(WEIGHT_STANDARD)
            enterHeight(HEIGHT_STANDARD)
            clickCalculate()
            checkAdjustedBw(ADJUSTED_BW_STANDARD_ANSWER)
        }
    }
    //endregion

    //region Clear fields
    @Test
    fun clearFields_clearBtn() {
        withAnthropometricsRobot {
            // field entry
            setAllFieldsProgrammatically(VALID_ENTRY_INT_STRING)
            checkWeight(VALID_ENTRY_INT_STRING)
            checkHeight(VALID_ENTRY_INT_STRING)
            checkBmi(VALID_ENTRY_INT_STRING)
            checkIbw(VALID_ENTRY_INT_STRING)
            checkPercentIbw(VALID_ENTRY_INT_STRING)
            checkAdjustedBw(VALID_ENTRY_INT_STRING)
            // clear and validate
            clickClear()
            checkWeight(EMPTY_STRING)
            checkHeight(EMPTY_STRING)
            checkBmi(EMPTY_STRING)
            checkIbw(EMPTY_STRING)
            checkPercentIbw(EMPTY_STRING)
            checkAdjustedBw(EMPTY_STRING)
        }
    }

    @Test
    fun clearFields_sexRadioBtnPress() {
        withAnthropometricsRobot {
            // field entry
            setSexMale()
            setAllFieldsProgrammatically(VALID_ENTRY_INT_STRING)
            checkWeight(VALID_ENTRY_INT_STRING)
            checkHeight(VALID_ENTRY_INT_STRING)
            checkBmi(VALID_ENTRY_INT_STRING)
            checkIbw(VALID_ENTRY_INT_STRING)
            checkPercentIbw(VALID_ENTRY_INT_STRING)
            checkAdjustedBw(VALID_ENTRY_INT_STRING)
            // clear and validate
            setSexFemale()
            checkBmi(EMPTY_STRING)
            checkIbw(EMPTY_STRING)
            checkPercentIbw(EMPTY_STRING)
            checkAdjustedBw(EMPTY_STRING)
            // check Toast
//            checkToastDisplayedWithMessage(R.string.toast_results_cleared_sex_change)
        }
    }

    @Test
    fun clearFields_unitRadioBtnPress() {
        withAnthropometricsRobot {
            // field entry
            setUnitMetric()
            setAllFieldsProgrammatically(VALID_ENTRY_INT_STRING)
            checkWeight(VALID_ENTRY_INT_STRING)
            checkHeight(VALID_ENTRY_INT_STRING)
            checkBmi(VALID_ENTRY_INT_STRING)
            checkIbw(VALID_ENTRY_INT_STRING)
            checkPercentIbw(VALID_ENTRY_INT_STRING)
            checkAdjustedBw(VALID_ENTRY_INT_STRING)
            // clear and validate
            setUnitStandard()
            checkBmi(EMPTY_STRING)
            checkIbw(EMPTY_STRING)
            checkPercentIbw(EMPTY_STRING)
            checkAdjustedBw(EMPTY_STRING)
            // check Toast
//            checkToastDisplayedWithMessage(R.string.toast_results_cleared_unit_change)
        }
    }
    //endregion

    //region Errors
    @Test
    fun checkWeightError_notANumber_displays() {
        withAnthropometricsRobot {
            enterWeight(INVALID_ENTRY_NOT_A_NUMBER)
            leaveWeightField()
            checkWeightNanError()
        }
    }

    @Test
    fun checkHeightError_notANumber_displays() {
        withAnthropometricsRobot {
            enterHeight(INVALID_ENTRY_NOT_A_NUMBER)
            leaveHeightField()
            checkHeightNanError()
        }
    }

    @Test
    fun checkWeightError_noError() {
        withAnthropometricsRobot {
            enterWeight(VALID_ENTRY_INT_STRING)
            leaveWeightField()
            checkWeightNoError()
        }
    }

    @Test
    fun checkHeightError_noError() {
        withAnthropometricsRobot {
            enterHeight(VALID_ENTRY_INT_STRING)
            leaveHeightField()
            checkHeightNoError()
        }
    }
    //endregion

    //region Field persistence
    @Test
    fun fieldPersistence_orientationChange() {
        withAnthropometricsRobot {
            // entry
            setSexMale()
            setUnitMetric()
            setAllFieldsProgrammatically(VALID_ENTRY_INT_STRING)
            rotateScreen(activityTestRule)
            rotateScreen(activityTestRule)
            // validation
            checkSexMale()
            checkUnitMetric()
            checkWeight(VALID_ENTRY_INT_STRING)
            checkHeight(VALID_ENTRY_INT_STRING)
            checkBmi(VALID_ENTRY_INT_STRING)
            checkIbw(VALID_ENTRY_INT_STRING)
            checkPercentIbw(VALID_ENTRY_INT_STRING)
            checkAdjustedBw(VALID_ENTRY_INT_STRING)
            //entry
            setSexFemale()
            setUnitStandard()
            rotateScreen(activityTestRule)
            rotateScreen(activityTestRule)
            checkSexFemale()
            checkUnitStandard()
        }
    }

    @Test
    fun fieldPersistence_repeatSexSelection() {
        withAnthropometricsRobot {
            // entry
            setSexMale()
            setAllFieldsProgrammatically(VALID_ENTRY_INT_STRING)
            setSexMale()
            // validation
            checkWeight(VALID_ENTRY_INT_STRING)
            checkHeight(VALID_ENTRY_INT_STRING)
            checkBmi(VALID_ENTRY_INT_STRING)
            checkIbw(VALID_ENTRY_INT_STRING)
            checkPercentIbw(VALID_ENTRY_INT_STRING)
            checkAdjustedBw(VALID_ENTRY_INT_STRING)
            // check Toast not displayed
//            checkToastNotDisplayedWithMessage(R.string.toast_results_cleared_sex_change)
        }
    }

    @Test
    fun fieldPersistence_repeatUnitSelection() {
        withAnthropometricsRobot {
            // entry
            setUnitMetric()
            setAllFieldsProgrammatically(VALID_ENTRY_INT_STRING)
            setUnitMetric()
            // validation
            checkWeight(VALID_ENTRY_INT_STRING)
            checkHeight(VALID_ENTRY_INT_STRING)
            checkBmi(VALID_ENTRY_INT_STRING)
            checkIbw(VALID_ENTRY_INT_STRING)
            checkPercentIbw(VALID_ENTRY_INT_STRING)
            checkAdjustedBw(VALID_ENTRY_INT_STRING)
            // check Toast not displayed
//            checkToastNotDisplayedWithMessage(R.string.toast_results_cleared_unit_change)
        }
    }
    //endregion

    //region Menu
    @Test
    fun checkMenu_disclaimer_launches() {
        Intents.init()
        withAnthropometricsRobot {
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
        withAnthropometricsRobot {
            openSettings()
        }
        withSettingsRobot {
            checkSettingsActivityIsDisplayed()
        }
        Intents.release()
    }
    //endregion
}