package com.octrobi.rdpocketpal.disclaimer

import com.octrobi.rdpocketpal.testutil.TestRobot

class DisclaimerActivityRobot : TestRobot() {

    fun checkDisclaimerActivityIsDisplayed() {
        checkActivityIsDisplayed(DisclaimerActivity::class.java.name)
    }
}

fun withDisclaimerActivityRobot(fn: DisclaimerActivityRobot.() -> Unit) =
        DisclaimerActivityRobot().apply(fn)