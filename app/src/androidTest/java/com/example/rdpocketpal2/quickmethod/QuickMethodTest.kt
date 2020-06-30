package com.example.rdpocketpal2.quickmethod

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
            enterKcalPerKgMin(FACTOR_MIN)
            enterKcalPerKgMax(FACTOR_MAX)
            clickCalorieCalculate()
            // validate calculation
            checkKcalPerDayMin(OUTPUT_MIN_TWO_DECIMAL)
            checkKcalPerDayMax(OUTPUT_MAX_TWO_DECIMAL)
            // click clear and validate
            clickCalorieClear()
            checkCalorieFieldsClear()
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
            enterKcalPerKgMin(FACTOR_MIN)
            enterKcalPerKgMax(FACTOR_MAX)
            clickCalorieCalculate()
            // validate calculation
            checkKcalPerDayMin(OUTPUT_MIN_TWO_DECIMAL)
            checkKcalPerDayMax(OUTPUT_MAX_TWO_DECIMAL)
        }
    }

    @Test
    fun calories_clear_fields() {
        // set text on all Calorie fields, clear, and validate
        withQuickMethodRobot {
            // set text on all fields
            setCalorieTextProgrammatically()
            // clear fields
            clickCalorieClear()
            // validate
            checkCalorieFieldsClear()
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
            enterGramsPerKgMin(FACTOR_MIN)
            enterGramsPerKgMax(FACTOR_MAX)
            clickProteinCalculate()
            // validate calculation
            checkGramsPerDayMin(OUTPUT_MIN_TWO_DECIMAL)
            checkGramsPerDayMax(OUTPUT_MAX_TWO_DECIMAL)
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
            enterGramsPerKgMin(FACTOR_MIN)
            enterGramsPerKgMax(FACTOR_MAX)
            clickProteinCalculate()
            // validate calculation
            checkGramsPerDayMin(OUTPUT_MIN_TWO_DECIMAL)
            checkGramsPerDayMax(OUTPUT_MAX_TWO_DECIMAL)
        }
    }

    @Test
    fun protein_clear_fields() {
        // set text on all Protein fields, clear, and validate
        withQuickMethodRobot {
            // set text on all fields
            setProteinTextProgrammatically()
            // clear fields
            clickProteinClear()
            // validate
            checkProteinFieldsClear()
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
            enterMlPerKgMin(FACTOR_MIN)
            enterMlPerKgMax(FACTOR_MAX)
            clickFluidCalculate()
            // validate calculation
            checkMlPerDayMin(OUTPUT_MIN_TWO_DECIMAL)
            checkMlPerDayMax(OUTPUT_MAX_TWO_DECIMAL)
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
            enterMlPerKgMin(FACTOR_MIN)
            enterMlPerKgMax(FACTOR_MAX)
            clickFluidCalculate()
            // validate calculation
            checkMlPerDayMin(OUTPUT_MIN_TWO_DECIMAL)
            checkMlPerDayMax(OUTPUT_MAX_TWO_DECIMAL)
        }
    }

    @Test
    fun fluid_clear_fields() {
        // set text on all Fluid fields, clear, and validate
        withQuickMethodRobot {
            // set text on all fields
            setFluidTextProgrammatically()
            // clear fields
            clickFluidClear()
            // validate
            checkFluidFieldsClear()
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
            enterKcalPerKgMin(FACTOR_MIN)
            enterKcalPerKgMax(FACTOR_MAX)
            clickCalorieCalculate()
            // validate rounded int results
            checkKcalPerDayMin(OUTPUT_MIN_ONE_DECIMAL)
            checkKcalPerDayMax(OUTPUT_MAX_TWO_DECIMAL)

            // set prefs via UI back to default
            setNumericScaleViaUi(2)
            // calculate
            clickCalorieCalculate()
            // validate rounded double results
            checkKcalPerDayMin(OUTPUT_MIN_TWO_DECIMAL)
            checkKcalPerDayMax(OUTPUT_MAX_TWO_DECIMAL)
        }
    }
    //endregion

    @Test
    fun orientationChange_fieldPersistence() {
        withQuickMethodRobot {
            // set all fields
            enterWeight(WEIGHT_METRIC)
            setCalorieTextProgrammatically()
            setProteinTextProgrammatically()
            setFluidTextProgrammatically()
            // rotate screen
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            // validate all fields
            checkWeight(WEIGHT_METRIC)
            checkProgrammaticallySetCalorieFields()
            checkProgrammaticallySetProteinFields()
            checkProgrammaticallySetFluidFields()
        }
    }

    private fun withQuickMethodRobot(fn: QuickMethodRobot.() -> Unit) = QuickMethodRobot().apply(fn)
}