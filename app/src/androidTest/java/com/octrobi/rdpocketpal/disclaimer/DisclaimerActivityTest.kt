package com.octrobi.rdpocketpal.disclaimer

import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.octrobi.rdpocketpal.settings.withSettingsRobot
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class DisclaimerActivityTest {

    @get:Rule
    var activityTestRule: ActivityTestRule<DisclaimerActivity> =
            ActivityTestRule(DisclaimerActivity::class.java)

    //region Visibility
    @Test
    fun checkDisclaimerTitle_isShowing() {
        withDisclaimerActivityRobot {
            checkActionBarSaysDisclaimer(InstrumentationRegistry.getInstrumentation())
        }
    }

    @Test
    fun checkDisclaimerText_isShowing() {
        withDisclaimerActivityRobot {
            checkDisclaimerTextDisplays()
        }
    }
    //endregion

    //region Menu
    @Test
    fun checkMenu_settings_launches() {
        Intents.init()
        withDisclaimerActivityRobot {
            openSettings()
        }
        withSettingsRobot {
            checkSettingsActivityIsDisplayed()
        }
        Intents.release()
    }
    //endregion
}