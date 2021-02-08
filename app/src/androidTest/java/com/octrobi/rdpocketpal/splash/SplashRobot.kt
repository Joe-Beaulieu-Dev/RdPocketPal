package com.octrobi.rdpocketpal.splash

import com.octrobi.rdpocketpal.testutil.TestRobot

class SplashRobot : TestRobot()

fun withSplashRobot(fn: SplashRobot.() -> Unit): TestRobot = SplashRobot().apply(fn)