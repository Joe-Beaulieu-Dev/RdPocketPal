package com.octrobi.rdpocketpal.quickmethod

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

//region Test constants
// Input
private const val WEIGHT_METRIC = "75"
private const val WEIGHT_STANDARD = "165.3466966387"
private const val FACTOR_MIN = "20.25"
private const val FACTOR_MAX = "30"
// Min output
private const val OUTPUT_MIN_ZERO_DECIMALS_ROUNDED = "1519"
private const val OUTPUT_MIN_ONE_DECIMAL_ROUNDED = "1518.8"
private const val OUTPUT_MIN_ONE_DECIMAL_TRUNCATED = "1518.7"
private const val OUTPUT_MIN_TWO_DECIMALS_ROUNDED = "1518.75"
// Max output
private const val OUTPUT_MAX_ZERO_DECIMALS_ROUNDED = "2250"
private const val OUTPUT_MAX_ONE_DECIMAL_ROUNDED = "2250"
private const val OUTPUT_MAX_ONE_DECIMAL_TRUNCATED = "2250"
private const val OUTPUT_MAX_TWO_DECIMALS_ROUNDED = "2250"
//endregion

@RunWith(AndroidJUnit4::class)
@LargeTest
class QuickMethodTest {

    @get:Rule
    var activityRule: ActivityTestRule<QuickMethodActivity> =
            ActivityTestRule(QuickMethodActivity::class.java)

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
    fun unitRadioGroup_changesUnitLabels_metric() {
        // set input units and validate unit labels
        withQuickMethodRobot {
            // set to metric
            setUnitMetric()
            // validate
            checkUnitMetricWeight(activityRule)
        }
    }

    @Test
    fun unitRadioGroup_changesUnitLabels_standard() {
        // set input units and validate unit labels
        withQuickMethodRobot {
            // set to metric
            setUnitStandard()
            // validate
            checkUnitStandardWeight(activityRule)
        }
    }
    //endregion

    //region Calories
    @Test
    fun calories_calculate_metric() {
        // enter metric data into Calorie section, calculate, and verify
        withQuickMethodRobot {
            // set to metric
            setUnitMetric()
            // input and calculate
            enterWeight(WEIGHT_METRIC)
            inCalories {
                enterMinInput(FACTOR_MIN)
                enterMaxInput(FACTOR_MAX)
                clickCalculate()
                // validate calculation
                checkMinOutput(OUTPUT_MIN_TWO_DECIMALS_ROUNDED)
                checkMaxOutput(OUTPUT_MAX_TWO_DECIMALS_ROUNDED)
            }
        }
    }

    @Test
    fun calories_calculate_standard() {
        // enter standard data into Calorie section, calculate, and verify
        withQuickMethodRobot {
            // set to metric
            setUnitStandard()
            // input and calculate
            enterWeight(WEIGHT_STANDARD)
            inCalories {
                enterMinInput(FACTOR_MIN)
                enterMaxInput(FACTOR_MAX)
                clickCalculate()
                // validate calculation
                checkMinOutput(OUTPUT_MIN_TWO_DECIMALS_ROUNDED)
                checkMaxOutput(OUTPUT_MAX_TWO_DECIMALS_ROUNDED)
            }
        }
    }

    @Test
    fun calories_clear_fields() {
        // set text on all Calorie fields, clear, and validate
        withQuickMethodRobot {
            inCalories {
                // set text on all fields
                setAllFieldsProgrammatically(VALID_ENTRY_INT_STRING)
                // clear fields
                clickClear()
                // validate
                checkFieldsClear()
            }
        }
    }
    //endregion

    //region Protein
    @Test
    fun protein_calculate_metric() {
        // enter metric data into Protein section, calculate, and verify
        withQuickMethodRobot {
            // set to metric
            setUnitMetric()
            // input and calculate
            enterWeight(WEIGHT_METRIC)
            inProtein {
                enterMinInput(FACTOR_MIN)
                enterMaxInput(FACTOR_MAX)
                clickCalculate()
                // validate calculation
                checkMinOutput(OUTPUT_MIN_TWO_DECIMALS_ROUNDED)
                checkMaxOutput(OUTPUT_MAX_TWO_DECIMALS_ROUNDED)
            }
        }
    }

    @Test
    fun protein_calculate_standard() {
        // enter standard data into Protein section, calculate, and verify
        withQuickMethodRobot {
            // set to metric
            setUnitStandard()
            // input and calculate
            enterWeight(WEIGHT_STANDARD)
            inProtein {
                enterMinInput(FACTOR_MIN)
                enterMaxInput(FACTOR_MAX)
                clickCalculate()
                // validate calculation
                checkMinOutput(OUTPUT_MIN_TWO_DECIMALS_ROUNDED)
                checkMaxOutput(OUTPUT_MAX_TWO_DECIMALS_ROUNDED)
            }
        }
    }

    @Test
    fun protein_clear_fields() {
        // set text on all Protein fields, clear, and validate
        withQuickMethodRobot {
            inProtein {
                // set text on all fields
                setAllFieldsProgrammatically(VALID_ENTRY_INT_STRING)
                // clear fields
                clickClear()
                // validate
                checkFieldsClear()
            }
        }
    }
    //endregion

    //region Fluid
    @Test
    fun fluid_calculate_metric() {
        // enter metric data into Fluids section, calculate, and verify
        withQuickMethodRobot {
            // set to metric
            setUnitMetric()
            // input and calculate
            enterWeight(WEIGHT_METRIC)
            inFluids {
                enterMinInput(FACTOR_MIN)
                enterMaxInput(FACTOR_MAX)
                clickCalculate()
                // validate calculation
                checkMinOutput(OUTPUT_MIN_TWO_DECIMALS_ROUNDED)
                checkMaxOutput(OUTPUT_MAX_TWO_DECIMALS_ROUNDED)
            }
        }
    }

    @Test
    fun fluid_calculate_standard() {
        // enter standard data into Fluids section, calculate, and verify
        withQuickMethodRobot {
            // set to metric
            setUnitStandard()
            // input and calculate
            enterWeight(WEIGHT_STANDARD)
            inFluids {
                enterMinInput(FACTOR_MIN)
                enterMaxInput(FACTOR_MAX)
                clickCalculate()
                // validate calculation
                checkMinOutput(OUTPUT_MIN_TWO_DECIMALS_ROUNDED)
                checkMaxOutput(OUTPUT_MAX_TWO_DECIMALS_ROUNDED)
            }
        }
    }

    @Test
    fun fluid_clear_fields() {
        // set text on all Fluid fields, clear, and validate
        withQuickMethodRobot {
            inFluids {
                // set text on all fields
                setAllFieldsProgrammatically(VALID_ENTRY_INT_STRING)
                // clear fields
                clickClear()
                // validate
                checkFieldsClear()
            }
        }
    }
    //endregion

    //region Clear fields
    @Test
    fun clearFields_unitRadioBtnPress() {
        withQuickMethodRobot {
            // input
            setAllFieldsProgrammatically(VALID_ENTRY_INT_STRING)

            // change units
            setUnitStandard()

            // check that outputs cleared and inputs remained
            inCalories {
                checkMinInput(VALID_ENTRY_INT_STRING)
                checkMaxInput(VALID_ENTRY_INT_STRING)
                checkMinOutput(EMPTY_STRING)
                checkMaxOutput(EMPTY_STRING)
            }
            inProtein {
                checkMinInput(VALID_ENTRY_INT_STRING)
                checkMaxInput(VALID_ENTRY_INT_STRING)
                checkMinOutput(EMPTY_STRING)
                checkMaxOutput(EMPTY_STRING)
            }
            inFluids {
                checkMinInput(VALID_ENTRY_INT_STRING)
                checkMaxInput(VALID_ENTRY_INT_STRING)
                checkMinOutput(EMPTY_STRING)
                checkMaxOutput(EMPTY_STRING)
            }

            // check that correct Toast is displayed
            checkToastDisplayedWithMessage(R.string.toast_results_cleared_unit_change)
        }
    }
    //endregion

    //region Errors
    @Test
    fun checkWeightError_notANumber_displays() {
        // enter invalid input into Weight field, then check for NaN error on EditText
        withQuickMethodRobot {
            // input
            enterWeight(INVALID_ENTRY_NOT_A_NUMBER)
            // leave weight field and close keyboard
            leaveField()

            // validate error
            checkWeightNanError(activityRule)
        }
    }

    @Test
    fun checkCalorieError_notANumber_displays() {
        // enter invalid input into Calorie fields, then check for NaN error on EditText
        withQuickMethodRobot {
            inCalories {
                // input
                enterMinInput(INVALID_ENTRY_NOT_A_NUMBER)
                enterMaxInput(INVALID_ENTRY_NOT_A_NUMBER)
                // leave final field and close keyboard
                leaveField()

                // validate errors
                checkMinInputNanError(activityRule)
                checkMaxInputNanError(activityRule)
            }
        }
    }

    @Test
    fun checkProteinError_notANumber_displays() {
        // enter invalid input into Protein fields, then check for NaN error on EditText
        withQuickMethodRobot {
            inProtein {
                // input
                enterMinInput(INVALID_ENTRY_NOT_A_NUMBER)
                enterMaxInput(INVALID_ENTRY_NOT_A_NUMBER)
                // leave final field and close keyboard
                leaveField()

                // validate errors
                checkMinInputNanError(activityRule)
                checkMaxInputNanError(activityRule)
            }
        }
    }

    @Test
    fun checkFluidsError_notANumber_displays() {
        // enter invalid input into Fluids fields, then check for NaN error on EditText
        withQuickMethodRobot {
            inFluids {
                // input
                enterMinInput(INVALID_ENTRY_NOT_A_NUMBER)
                enterMaxInput(INVALID_ENTRY_NOT_A_NUMBER)
                // leave final field and close keyboard
                leaveField()

                // validate errors
                checkMinInputNanError(activityRule)
                checkMaxInputNanError(activityRule)
            }
        }
    }

    @Test
    fun checkWeightError_noError() {
        // enter valid input into Weight field, then check that EditText doesn't display an error
        withQuickMethodRobot {
            // input
            enterWeight(WEIGHT_METRIC)
            // leave weight field and close keyboard
            leaveField()

            // validate error
            checkWeightNoError()
        }
    }

    @Test
    fun checkCalorieError_noError() {
        // enter valid input into Calorie fields, then check that EditText doesn't display an error
        withQuickMethodRobot {
            inCalories {
                // input
                enterMinInput(FACTOR_MIN)
                enterMaxInput(FACTOR_MAX)
                // leave final field and close keyboard
                leaveField()

                // validate errors
                checkMinInputNoError()
                checkMaxInputNoError()
            }
        }
    }

    @Test
    fun checkProteinError_noError() {
        // enter valid input into Protein fields, then check that EditText doesn't display an error
        withQuickMethodRobot {
            inProtein {
                // input
                enterMinInput(FACTOR_MIN)
                enterMaxInput(FACTOR_MAX)
                // leave final field and close keyboard
                leaveField()

                // validate errors
                checkMinInputNoError()
                checkMaxInputNoError()
            }
        }
    }

    @Test
    fun checkFluidsError_noError() {
        // enter valid input into Fluids fields, then check that EditText doesn't display an error
        withQuickMethodRobot {
            inFluids {
                // input
                enterMinInput(FACTOR_MIN)
                enterMaxInput(FACTOR_MAX)
                // leave final field and close keyboard
                leaveField()

                // validate errors
                checkMinInputNoError()
                checkMaxInputNoError()
            }
        }
    }
    //endregion

    //region Field persistence
    @Test
    fun fieldPersistence_orientationChange() {
        // enter data into all fields, change orientation twice, and check that input persists
        withQuickMethodRobot {
            // entry
            setUnitMetric()
            setAllFieldsProgrammatically(WEIGHT_METRIC)
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            // validation
            checkUnitMetric()
            checkAllProgrammaticallySetFields(WEIGHT_METRIC)
            // entry
            setUnitStandard()
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            // validation
            checkUnitStandard()
        }
    }

    @Test
    fun orientationChange_repeatUnitSelection() {
        withQuickMethodRobot {
            // entry
            setUnitMetric()
            setAllFieldsProgrammatically(VALID_ENTRY_INT_STRING)
            setUnitMetric()
            // validation
            checkAllProgrammaticallySetFields(VALID_ENTRY_INT_STRING)
            // check Toast not displayed
//            checkToastNotDisplayedWithMessage(R.string.toast_results_cleared_unit_change)
        }
    }
    //endregion

    //region Menu
    @Test
    fun checkMenu_disclaimer_launches() {
        Intents.init()
        withQuickMethodRobot {
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
        withQuickMethodRobot {
            openSettings()
        }
        withSettingsRobot {
            checkSettingsActivityIsDisplayed()
        }
        Intents.release()
    }
    //endregion

    //region Preferences
    @Test
    fun preferenceChangeRefresh() {
        withQuickMethodRobot {
            // set prefs via UI
            setNumericScaleViaUi(0)
            setDecimalReductionMethodViaUi(R.string.key_rounding)
            // input and calculate
            enterWeight(WEIGHT_METRIC)
            inCalories {
                enterMinInput(FACTOR_MIN)
                enterMaxInput(FACTOR_MAX)
                clickCalculate()
                // validate rounded int results
                checkMinOutput(OUTPUT_MIN_ZERO_DECIMALS_ROUNDED)
                checkMaxOutput(OUTPUT_MAX_ZERO_DECIMALS_ROUNDED)
            }

            // set prefs via UI
            setNumericScaleViaUi(1)
            // input and calculate
            inCalories {
                clickCalculate()
                // validate rounded int results
                checkMinOutput(OUTPUT_MIN_ONE_DECIMAL_ROUNDED)
                checkMaxOutput(OUTPUT_MAX_ONE_DECIMAL_ROUNDED)
            }

            // set prefs via UI
            setDecimalReductionMethodViaUi(R.string.key_truncation)
            // input and calculate
            inCalories {
                clickCalculate()
                // validate rounded int results
                checkMinOutput(OUTPUT_MIN_ONE_DECIMAL_TRUNCATED)
                checkMaxOutput(OUTPUT_MAX_ONE_DECIMAL_TRUNCATED)
            }

            // set prefs via UI back to default
            setNumericScaleViaUi(2)
            setDecimalReductionMethodViaUi(R.string.key_rounding)
            // input and calculate
            inCalories {
                // calculate
                clickCalculate()
                // validate rounded double results
                checkMinOutput(OUTPUT_MIN_TWO_DECIMALS_ROUNDED)
                checkMaxOutput(OUTPUT_MAX_TWO_DECIMALS_ROUNDED)
            }
        }
    }
    //endregion
}