package com.josephbeaulieu.rdpocketpal.home

import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.josephbeaulieu.rdpocketpal.disclaimer.withDisclaimerDialogFragmentRobot
import com.josephbeaulieu.rdpocketpal.settings.withSettingsRobot
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeTest {

    @get:Rule
    var activityTestRule: ActivityTestRule<HomeActivity> =
            ActivityTestRule(HomeActivity::class.java)

    @Before
    fun before() {
        Intents.init()
        withDisclaimerDialogFragmentRobot {
            clickAgree()
        }
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