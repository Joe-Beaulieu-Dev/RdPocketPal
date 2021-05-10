package com.octrobi.rdpocketpal.settings

import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.disclaimer.withDisclaimerActivityRobot
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SettingsTest {

    @get:Rule
    val activityTestRule: ActivityScenarioRule<SettingsActivity> =
        ActivityScenarioRule(SettingsActivity::class.java)

    @Test
    fun checkDisplayedDecimalPlaces_description_changesWithSettingsChange() {
        withSettingsRobot {
            setDisplayedDecimalPlaces(1)
            checkDisplayedDecimalPlacesDescription(1)
            setDisplayedDecimalPlaces(3)
            checkDisplayedDecimalPlacesDescription(3)
        }
    }

    @Test
    fun checkDecimalReductionMethod_description_changesWithSettingsChange() {
        withSettingsRobot {
            setDecimalReductionMethod(R.string.key_rounding)
            checkDecimalReductionMethodDescription(R.string.key_rounding)
            setDecimalReductionMethod(R.string.key_truncation)
            checkDecimalReductionMethodDescription(R.string.key_truncation)
        }
    }

    //region Menu
    @Test
    fun checkMenu_disclaimer_launches() {
        Intents.init()
        withSettingsRobot {
            openDisclaimer()
        }
        withDisclaimerActivityRobot {
            checkDisclaimerActivityIsDisplayed()
        }
        Intents.release()
    }
    //endregion
}