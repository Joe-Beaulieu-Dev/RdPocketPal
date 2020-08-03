package com.example.rdpocketpal2.quickmethod

import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.settings.withSettingsRobot
import com.example.rdpocketpal2.testutil.EMPTY_STRING
import com.example.rdpocketpal2.testutil.INVALID_ENTRY_NOT_A_NUMBER
import com.example.rdpocketpal2.testutil.TestUtil
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//region Test constants
// input
const val WEIGHT_METRIC: String = "75"
const val WEIGHT_STANDARD: String = "165.346565409"
const val FACTOR_MIN: String = "20.25"
const val FACTOR_MAX: String = "30"
// min output
const val OUTPUT_MIN_ZERO_DECIMALS_ROUNDED: String = "1519"
const val OUTPUT_MIN_ONE_DECIMAL_ROUNDED: String = "1518.8"
const val OUTPUT_MIN_ONE_DECIMAL_TRUNCATED: String = "1518.7"
const val OUTPUT_MIN_TWO_DECIMALS_ROUNDED: String = "1518.75"
// max output
const val OUTPUT_MAX_ZERO_DECIMALS_ROUNDED: String = "2250"
const val OUTPUT_MAX_ONE_DECIMAL_ROUNDED: String = "2250"
const val OUTPUT_MAX_ONE_DECIMAL_TRUNCATED: String = "2250"
const val OUTPUT_MAX_TWO_DECIMALS_ROUNDED: String = "2250"
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
            setInputMetric()
            // validate
            checkUnitMetric(activityRule)
        }
    }

    @Test
    fun unitRadioGroup_changesUnitLabels_standard() {
        // set input units and validate unit labels
        withQuickMethodRobot {
            // set to metric
            setInputStandard()
            // validate
            checkUnitStandard(activityRule)
        }
    }
    //endregion

    //region Calories
    @Test
    fun calories_calculate_metric() {
        // enter metric data into Calorie section, calculate, and verify
        withQuickMethodRobot {
            // set to metric
            setInputMetric()
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
            setInputStandard()
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
                setAllFieldsProgrammatically(WEIGHT_METRIC)
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
            setInputMetric()
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
            setInputStandard()
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
                setAllFieldsProgrammatically(WEIGHT_METRIC)
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
            setInputMetric()
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
            setInputStandard()
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
                setAllFieldsProgrammatically(WEIGHT_METRIC)
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
            setAllFieldsProgrammatically(WEIGHT_METRIC)

            // change units
            setInputStandard()

            // check that outputs cleared and inputs remained
            inCalories {
                checkMinInput(WEIGHT_METRIC)
                checkMaxInput(WEIGHT_METRIC)
                checkMinOutput(EMPTY_STRING)
                checkMaxOutput(EMPTY_STRING)
            }
            inProtein {
                checkMinInput(WEIGHT_METRIC)
                checkMaxInput(WEIGHT_METRIC)
                checkMinOutput(EMPTY_STRING)
                checkMaxOutput(EMPTY_STRING)
            }
            inFluids {
                checkMinInput(WEIGHT_METRIC)
                checkMaxInput(WEIGHT_METRIC)
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
    fun orientationChange_fieldPersistence() {
        // enter data into all fields, change orientation twice, and check that input persists
        withQuickMethodRobot {
            // set all fields
            setAllFieldsProgrammatically(WEIGHT_METRIC)

            // rotate screen
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())

            // validate all fields
            checkAllProgrammaticallySetFields(WEIGHT_METRIC)
        }
    }
    //endregion

    //region Preferences
    @Test
    fun checkPreferences_areAccessible() {
        Intents.init()
        withQuickMethodRobot {
            openPreferences()
        }
        withSettingsRobot {
            checkSettingsActivityIsDisplayed()
        }
        Intents.release()
    }

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