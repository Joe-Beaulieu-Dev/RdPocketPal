package com.example.rdpocketpal2.quickmethod

import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.testutil.TestUtil
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//region Test constants
const val WEIGHT_METRIC: String = "75"
const val WEIGHT_STANDARD: String = "165.346565409"
const val FACTOR_MIN: String = "20.25"
const val FACTOR_MAX: String = "30"
const val OUTPUT_MIN_ONE_DECIMAL: String = "1519"
const val OUTPUT_MIN_TWO_DECIMAL: String = "1518.75"
const val OUTPUT_MAX_TWO_DECIMAL: String = "2250"
const val INVALID_ENTRY_NOT_NUMBER: String = "."
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
        fun before() {
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
                checkMinOutput(OUTPUT_MIN_TWO_DECIMAL)
                checkMaxOutput(OUTPUT_MAX_TWO_DECIMAL)
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
                checkMinOutput(OUTPUT_MIN_TWO_DECIMAL)
                checkMaxOutput(OUTPUT_MAX_TWO_DECIMAL)
            }
        }
    }

    @Test
    fun calories_clear_fields() {
        // set text on all Calorie fields, clear, and validate
        withQuickMethodRobot {
            inCalories {
                // set text on all fields
                setAllFieldsProgrammatically()
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
                checkMinOutput(OUTPUT_MIN_TWO_DECIMAL)
                checkMaxOutput(OUTPUT_MAX_TWO_DECIMAL)
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
                checkMinOutput(OUTPUT_MIN_TWO_DECIMAL)
                checkMaxOutput(OUTPUT_MAX_TWO_DECIMAL)
            }
        }
    }

    @Test
    fun protein_clear_fields() {
        // set text on all Protein fields, clear, and validate
        withQuickMethodRobot {
            inProtein {
                // set text on all fields
                setAllFieldsProgrammatically()
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
                checkMinOutput(OUTPUT_MIN_TWO_DECIMAL)
                checkMaxOutput(OUTPUT_MAX_TWO_DECIMAL)
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
                checkMinOutput(OUTPUT_MIN_TWO_DECIMAL)
                checkMaxOutput(OUTPUT_MAX_TWO_DECIMAL)
            }
        }
    }

    @Test
    fun fluid_clear_fields() {
        // set text on all Fluid fields, clear, and validate
        withQuickMethodRobot {
            inFluids {
                // set text on all fields
                setAllFieldsProgrammatically()
                // clear fields
                clickClear()
                // validate
                checkFieldsClear()
            }
        }
    }
    //endregion

    //region Preference reaction
    @Test
    fun preferenceChangeRefresh() {
        withQuickMethodRobot {
            // set prefs via UI
            setDecimalReductionMethodViaUi(R.string.key_rounding)
            setNumericScaleViaUi(0)

            // input and calculate
            enterWeight(WEIGHT_METRIC)
            inCalories {
                enterMinInput(FACTOR_MIN)
                enterMaxInput(FACTOR_MAX)
                clickCalculate()
                // validate rounded int results
                checkMinOutput(OUTPUT_MIN_ONE_DECIMAL)
                checkMaxOutput(OUTPUT_MAX_TWO_DECIMAL)
            }

            // set prefs via UI back to default
            setNumericScaleViaUi(2)

            inCalories {
                // calculate
                clickCalculate()
                // validate rounded double results
                checkMinOutput(OUTPUT_MIN_TWO_DECIMAL)
                checkMaxOutput(OUTPUT_MAX_TWO_DECIMAL)
            }
        }
    }
    //endregion

    //region Error messages
    @Test
    fun checkWeightError_notANumber_displays() {
        // enter invalid input into Weight field, then check for NaN error on EditText
        withQuickMethodRobot {
            // input
            enterWeight(INVALID_ENTRY_NOT_NUMBER)
            // leave final field and close keyboard
            clickViewId(R.id.qm_calorie_kcal_per_kg_min)
            pressBack()

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
                enterMinInput(INVALID_ENTRY_NOT_NUMBER)
                enterMaxInput(INVALID_ENTRY_NOT_NUMBER)
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
                enterMinInput(INVALID_ENTRY_NOT_NUMBER)
                enterMaxInput(INVALID_ENTRY_NOT_NUMBER)
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
                enterMinInput(INVALID_ENTRY_NOT_NUMBER)
                enterMaxInput(INVALID_ENTRY_NOT_NUMBER)
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
            // leave final field and close keyboard
            clickViewId(R.id.qm_calorie_kcal_per_kg_min)
            pressBack()

            // validate error
            checkWeightNoError(activityRule)
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

    @Test
    fun orientationChange_fieldPersistence() {
        // enter data into all fields, change orientation twice, and check that input persists
        withQuickMethodRobot {
            // set all fields
            enterWeight(WEIGHT_METRIC)
            inCalories {
                setAllFieldsProgrammatically()
            }
            inProtein {
                setAllFieldsProgrammatically()
            }
            inFluids {
                setAllFieldsProgrammatically()
            }

            // rotate screen
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())

            // validate all fields
            checkWeight(WEIGHT_METRIC)
            inCalories {
                checkAllProgrammaticallySetFields()
            }
            inProtein {
                checkAllProgrammaticallySetFields()
            }
            inFluids {
                checkAllProgrammaticallySetFields()
            }
        }
    }
}