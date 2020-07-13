package com.example.rdpocketpal2.predictiveequations

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
    fun checkCalculation_Mifflin() {

    }

    @Test
    fun checkCalculation_Benedict() {

    }

    @Test
    fun checkCalculation_PennState2003b() {

    }

    @Test
    fun checkCalculation_PennState2010() {

    }

    @Test
    fun checkCalculation_Brandi() {

    }
    //endregion

    //region Clear fields
    @Test
    fun clearFields_ClearBtn() {

    }

    @Test
    fun clearFields_unitRadioBtnPress() {

    }

    @Test
    fun clearFields_equationSpinnerSelection() {

    }
    //endregion

    //region Errors
    @Test
    fun checkError_weight_notANumber_displays() {

    }

    @Test
    fun checkError_height_notANumber_displays() {

    }

    @Test
    fun checkError_age_notANumber_displays() {

    }

    @Test
    fun checkError_tmax_notANumber_displays() {

    }

    @Test
    fun checkError_ve_notANumber_displays() {

    }

    @Test
    fun checkError_heartRate_notANumber_displays() {

    }

    @Test
    fun checkError_activityFactorMin_notANumber_displays() {

    }

    @Test
    fun checkError_activityFactorMax_notANumber_displays() {

    }

    @Test
    fun checkError_weight_noError() {

    }

    @Test
    fun checkError_height_noError() {

    }

    @Test
    fun checkError_age_noError() {

    }

    @Test
    fun checkError_tmax_noError() {

    }

    @Test
    fun checkError_ve_noError() {

    }

    @Test
    fun checkError_heartRate_noError() {

    }

    @Test
    fun checkError_activityFactorMin_noError() {

    }

    @Test
    fun checkError_activityFactorMax_noError() {

    }
    //endregion

    //region Field persistence
    @Test
    fun fieldPersistence_orientationChange() {

    }
    //endregion

    //region Preference reaction
    @Test
    fun preferenceChangeRefresh() {

    }
    //endregion
}