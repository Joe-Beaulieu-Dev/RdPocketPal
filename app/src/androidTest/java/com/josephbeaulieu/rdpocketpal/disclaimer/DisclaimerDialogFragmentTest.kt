package com.josephbeaulieu.rdpocketpal.disclaimer

import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.josephbeaulieu.rdpocketpal.home.HomeActivity
import com.josephbeaulieu.rdpocketpal.home.withHomeRobot
import com.josephbeaulieu.rdpocketpal.testutil.TestUtil
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class DisclaimerDialogFragmentTest {

    @get:Rule
    var activityTestRule: ActivityTestRule<HomeActivity> =
            ActivityTestRule(HomeActivity::class.java)

    @Before
    fun before() {
        TestUtil.setIfDisclaimerAccepted(
                InstrumentationRegistry.getInstrumentation().targetContext, false)
    }

    @Test
    fun checkDisclaimerDisplays_initialCreation() {
        withDisclaimerDialogFragmentRobot {
            checkDisclaimerDialogIsShowing()
        }
    }

    @Test
    fun checkDisclaimerComponents_display() {
        withDisclaimerDialogFragmentRobot {
            checkDisclaimerDialogIsShowing()
            checkTitleIsShowing()
            checkDisclaimerTextIsShowing()
            checkReadAnUnderstandTextIsShowing()
            checkExitBtnIsShowing()
            checkContinueBtnIsShowing()
        }
    }

    @Test
    fun checkDisclaimerDisplays_rotation() {
        withDisclaimerDialogFragmentRobot {
            checkDisclaimerDialogIsShowing()
            rotateScreen(activityTestRule, InstrumentationRegistry.getInstrumentation())
            checkDisclaimerDialogIsShowing()
            rotateScreen(activityTestRule, InstrumentationRegistry.getInstrumentation())
            checkDisclaimerDialogIsShowing()
        }
    }

    @Test
    fun checkDisclaimerDoesNotDisplay_afterContinue_rotation() {
        withDisclaimerDialogFragmentRobot {
            checkDisclaimerDialogIsShowing()
            clickContinue()
            rotateScreen(activityTestRule, InstrumentationRegistry.getInstrumentation())
            checkDisclaimerDialogIsNotShowing()
            rotateScreen(activityTestRule, InstrumentationRegistry.getInstrumentation())
            checkDisclaimerDialogIsNotShowing()
        }
    }

    @Test
    fun checkDisclaimerDoesNotDisplay_afterContinue_leaveActivityAndReturn() {
        Intents.init()
        withDisclaimerDialogFragmentRobot {
            checkDisclaimerDialogIsShowing()
            clickContinue()
            withHomeRobot {
                launchAnthropometrics(activityTestRule)
                checkAnthropometricsActivityIsDisplayed()
                pressBackButton()
                checkHomeActivityIsDisplayed(InstrumentationRegistry.getInstrumentation())
            }
            checkDisclaimerDialogIsNotShowing()
        }
        Intents.release()
    }

    @Test
    fun checkDisclaimerDialog_isNotCancellable() {
        withDisclaimerDialogFragmentRobot {
            checkDisclaimerDialogIsShowing()
            pressBackButton()
            pressBackButton()
            checkDisclaimerDialogIsShowing()
        }
    }
}