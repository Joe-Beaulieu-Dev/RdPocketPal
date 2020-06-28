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
    fun calories_calculate_metric() {
        // enter metric data into Calorie section, calculate, and verify
        withQuickMethodRobot {
            // input and calculate
            enterWeight("75")
            enterKcalPerKgMin("20.25")
            enterKcalPerKgMax("30")
            clickCalorieCalculate()
            // validate
            validateKcalPerDayMin("1518.75")
            validateKcalPerDayMax("2250")
        }
    }

    @Test
    fun preferenceChangeRefresh() {
        withQuickMethodRobot {
            // set prefs via UI
            setDecimalReductionMethodViaUi(R.string.key_rounding)
            setNumericScaleViaUi(0)
            // input and calculate
            enterWeight("75")
            enterKcalPerKgMin("20.25")
            enterKcalPerKgMax("30")
            clickCalorieCalculate()
            // validate rounded int results
            validateKcalPerDayMin("1519")
            validateKcalPerDayMax("2250")

            // set prefs via UI
            setNumericScaleViaUi(2)
            // calculate
            clickCalorieCalculate()
            // validate rounded double results
            validateKcalPerDayMin("1518.75")
            validateKcalPerDayMax("2250")
        }
    }

    private fun withQuickMethodRobot(fn: QuickMethodRobot.() -> Unit) = QuickMethodRobot().apply(fn)
}