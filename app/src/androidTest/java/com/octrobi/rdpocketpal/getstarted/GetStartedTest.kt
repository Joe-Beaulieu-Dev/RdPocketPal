package com.octrobi.rdpocketpal.getstarted

import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.octrobi.rdpocketpal.disclaimer.withDisclaimerDialogFragmentRobot
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class GetStartedTest {

    @get:Rule
    val activityRule: ActivityScenarioRule<GetStartedActivity> =
        ActivityScenarioRule(GetStartedActivity::class.java)

    @Test
    fun checkAllUIComponents_display() {
        withGetStartedRobot {
            checkReadAndUnderstandIsDisplayed()
            checkReadAndUnderstandText()
            checkContinueBtnIsDisplayed()
            checkContinueBtnText()
            rotateScreen(activityRule)
            checkReadAndUnderstandIsDisplayed()
            checkReadAndUnderstandText()
            checkContinueBtnIsDisplayed()
            checkContinueBtnText()
        }
    }

    @Test
    fun checkDisclaimerLink_launches_DisclaimerDialogFragment() {
        withGetStartedRobot {
            clickDisclaimerLink()
        }
        withDisclaimerDialogFragmentRobot {
            checkDisclaimerDialogIsDisplayed()
        }
    }

    @Test
    fun checkContinueBtn_launches_HomeActivity() {
        Intents.init()
        withGetStartedRobot {
            clickContinue()
            checkHomeActivityIsDisplayed()
        }
        Intents.release()
    }
}