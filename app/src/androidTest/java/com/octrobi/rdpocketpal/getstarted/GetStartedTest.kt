package com.octrobi.rdpocketpal.getstarted

import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.octrobi.rdpocketpal.disclaimer.withDisclaimerDialogFragmentRobot
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class GetStartedTest {

    @get:Rule
    val activityRule: ActivityTestRule<GetStartedActivity> =
            ActivityTestRule(GetStartedActivity::class.java)

    @Test
    fun checkAllUIComponents_display() {
        withGetStartedRobot {
            checkReadAndUnderstandTextIsShowing()
            checkContinueBtnIsShowing()
            checkContinueBtnHasProperText(activityRule)
            rotateScreen(activityRule, InstrumentationRegistry.getInstrumentation())
            checkReadAndUnderstandTextIsShowing()
            checkContinueBtnIsShowing()
            checkContinueBtnHasProperText(activityRule)
        }
    }

    @Test
    fun checkDisclaimerLink_launches_DisclaimerDialogFragment() {
        withGetStartedRobot {
            clickDisclaimerLink()
        }
        withDisclaimerDialogFragmentRobot {
            checkDisclaimerDialogIsShowing()
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