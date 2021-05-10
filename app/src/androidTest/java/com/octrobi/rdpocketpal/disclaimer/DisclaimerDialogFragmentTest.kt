package com.octrobi.rdpocketpal.disclaimer

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.octrobi.rdpocketpal.getstarted.GetStartedActivity
import com.octrobi.rdpocketpal.getstarted.withGetStartedRobot
import com.octrobi.rdpocketpal.testutil.TestUtil
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class DisclaimerDialogFragmentTest {

    @get:Rule
    var activityTestRule: ActivityScenarioRule<GetStartedActivity> =
        ActivityScenarioRule(GetStartedActivity::class.java)

    @Before
    fun before() {
        TestUtil.setIfDisclaimerAccepted(
                InstrumentationRegistry.getInstrumentation().targetContext, false)
        withGetStartedRobot {
            clickDisclaimerLink()
        }
    }

    @Test
    fun checkDisclaimerDisplays_initialCreation() {
        withDisclaimerDialogFragmentRobot {
            checkDisclaimerDialogIsDisplayed()
        }
    }

    @Test
    fun checkDisclaimerComponents_display() {
        withDisclaimerDialogFragmentRobot {
            checkDisclaimerDialogIsDisplayed()
            checkTitleIsDisplayed()
            checkDisclaimerTextIsDisplayed()
            checkCloseBtnIsShowing()
            // rotate screen and check again
            rotateScreen(activityTestRule)
            checkDisclaimerDialogIsDisplayed()
            checkTitleIsDisplayed()
            checkDisclaimerTextIsDisplayed()
            checkCloseBtnIsShowing()
        }
    }

    @Test
    fun checkDisclaimerDisplays_rotation() {
        withDisclaimerDialogFragmentRobot {
            checkDisclaimerDialogIsDisplayed()
            rotateScreen(activityTestRule)
            checkDisclaimerDialogIsDisplayed()
            rotateScreen(activityTestRule)
            checkDisclaimerDialogIsDisplayed()
        }
    }

    @Test
    fun checkDisclaimerDialog_isNotCancellable() {
        withDisclaimerDialogFragmentRobot {
            checkDisclaimerDialogIsDisplayed()
            pressBackButton()
            pressBackButton()
            checkDisclaimerDialogIsDisplayed()
        }
    }

    @Test
    fun checkCloseButton_dismissesDisclaimer() {
        withDisclaimerDialogFragmentRobot {
            checkDisclaimerDialogIsDisplayed()
            clickClose()
            checkDisclaimerDialogIsNotDisplayed()
        }
    }
}