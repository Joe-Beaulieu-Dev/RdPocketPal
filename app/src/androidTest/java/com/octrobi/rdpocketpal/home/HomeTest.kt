package com.octrobi.rdpocketpal.home

import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.octrobi.rdpocketpal.disclaimer.withDisclaimerActivityRobot
import com.octrobi.rdpocketpal.settings.withSettingsRobot
import com.octrobi.rdpocketpal.testutil.TestUtil
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<HomeActivity> =
        ActivityScenarioRule(HomeActivity::class.java)

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
            launchPredictiveEquations()
            checkPredictiveEquationsActivityIsDisplayed()
        }
    }

    @Test
    fun quickMethodButton_launchesQuickMethodActivity() {
        withHomeRobot {
            launchQuickMethod()
            checkQuickMethodIsActivityDisplayed()
        }
    }

    @Test
    fun anthropometricsButton_launchesAnthropometricsActivity() {
        withHomeRobot {
            launchAnthropometrics()
            checkAnthropometricsActivityIsDisplayed()
        }
    }

    @Test
    fun conversionsButton_launchesConversionActivity() {
        withHomeRobot {
            launchConversions()
            checkConversionsActivityIsDisplayed()
        }
    }
    //endregion

    //region Menu
    @Test
    fun checkMenu_disclaimer_launches() {
        withHomeRobot {
            openDisclaimer()
        }
        withDisclaimerActivityRobot {
            checkDisclaimerActivityIsDisplayed()
        }
    }

    @Test
    fun checkMenu_settings_launches() {
        withHomeRobot {
            openSettings()
        }
        withSettingsRobot {
            checkSettingsActivityIsDisplayed()
        }
    }
    //endregion
}