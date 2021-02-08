package com.octrobi.rdpocketpal.splash

import androidx.test.core.app.launchActivity
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.octrobi.rdpocketpal.getstarted.withGetStartedRobot
import com.octrobi.rdpocketpal.home.withHomeRobot
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SplashTest {

    @Test fun checkGetStartedActivityLaunches_ifDisclaimerNotAccepted() {
        // set disclaimer flag to true
        withSplashRobot {
            setDisclaimerToNotAccepted()
        }

        // check GetStartedActivity starts
        Intents.init()
        val scenario = launchActivity<SplashActivity>()
        withGetStartedRobot {
            checkGetStartedActivityIsDisplayed()
        }
        scenario.close()
        Intents.release()
    }

    @Test fun checkHomeActivityLaunches_ifDisclaimerIsAccepted() {
        // set disclaimer flag to false
        withSplashRobot {
            setDisclaimerToAccepted()
        }

        // check HomeActivity starts
        Intents.init()
        val scenario = launchActivity<SplashActivity>()
        withHomeRobot {
            checkHomeActivityIsDisplayed()
        }
        scenario.close()
        Intents.release()
    }
}