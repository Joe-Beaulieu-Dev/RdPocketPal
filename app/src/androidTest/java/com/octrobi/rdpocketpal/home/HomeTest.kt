package com.octrobi.rdpocketpal.home

import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.octrobi.rdpocketpal.settings.withSettingsRobot
import com.octrobi.rdpocketpal.testutil.TestUtil
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeTest {

    @get:Rule
    var activityTestRule: ActivityTestRule<HomeActivity> =
            ActivityTestRule(HomeActivity::class.java)

    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            TestUtil.setIfDisclaimerAccepted(
                    InstrumentationRegistry.getInstrumentation().targetContext, true)
        }
    }

    @Before
    fun before() {
        Intents.init()
    }

    @After
    fun after() {
        Intents.release()
    }

    //region Activity launching
    @Test
    fun predictiveEquationsButton_launchesPredictiveEquationsActivity() {
        withHomeRobot {
            launchPredictiveEquations(activityTestRule)
            checkPredictiveEquationsActivityIsDisplayed()
        }
    }

    @Test
    fun quickMethodButton_launchesQuickMethodActivity() {
        withHomeRobot {
            launchQuickMethod(activityTestRule)
            checkQuickMethodIsActivityDisplayed()
        }
    }

    @Test
    fun anthropometricsButton_launchesAnthropometricsActivity() {
        withHomeRobot {
            launchAnthropometrics(activityTestRule)
            checkAnthropometricsActivityIsDisplayed()
        }
    }

    @Test
    fun conversionsButton_launchesConversionActivity() {
        withHomeRobot {
            launchConversions(activityTestRule)
            checkConversionsActivityIsDisplayed()
        }
    }
    //endregion

    //region Preferences
    @Test
    fun checkPreferences_areAccessible() {
        withHomeRobot {
            openPreferences()
        }
        withSettingsRobot {
            checkSettingsActivityIsDisplayed()
        }
    }
    //endregion
}