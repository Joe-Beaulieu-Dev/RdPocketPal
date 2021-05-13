package com.octrobi.rdpocketpal.suite

import com.octrobi.rdpocketpal.anthropometrics.AnthropometricsTest
import com.octrobi.rdpocketpal.conversions.ConversionsTest
import com.octrobi.rdpocketpal.disclaimer.DisclaimerActivityTest
import com.octrobi.rdpocketpal.disclaimer.DisclaimerDialogFragmentTest
import com.octrobi.rdpocketpal.getstarted.GetStartedTest
import com.octrobi.rdpocketpal.home.HomeTest
import com.octrobi.rdpocketpal.predictiveequations.PredictiveEquationsTest
import com.octrobi.rdpocketpal.quickmethod.QuickMethodTest
import com.octrobi.rdpocketpal.settings.SettingsTest
import com.octrobi.rdpocketpal.splash.SplashTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    AnthropometricsTest::class,
    ConversionsTest::class,
    DisclaimerActivityTest::class,
    DisclaimerDialogFragmentTest::class,
    GetStartedTest::class,
    HomeTest::class,
    PredictiveEquationsTest::class,
    QuickMethodTest::class,
    SettingsTest::class,
    SplashTest::class,
)
class UiTestSuite