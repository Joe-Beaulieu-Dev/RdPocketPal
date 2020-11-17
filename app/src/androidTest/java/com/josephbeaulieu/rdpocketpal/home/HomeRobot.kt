package com.josephbeaulieu.rdpocketpal.home

import android.app.Activity
import android.app.Instrumentation
import androidx.test.rule.ActivityTestRule
import com.josephbeaulieu.rdpocketpal.R
import com.josephbeaulieu.rdpocketpal.anthropometrics.AnthropometricsActivity
import com.josephbeaulieu.rdpocketpal.conversions.ConversionActivity
import com.josephbeaulieu.rdpocketpal.predictiveequations.PredictiveEquationsActivity
import com.josephbeaulieu.rdpocketpal.quickmethod.QuickMethodActivity
import com.josephbeaulieu.rdpocketpal.testutil.TestRobot

class HomeRobot : TestRobot() {

    //region Launch Activities
    fun <T : Activity> launchPredictiveEquations(activityRule: ActivityTestRule<T>) {
        clickRecyclerViewItem(activityRule, R.id.home_buttons_recyclerView
                , R.string.btn_predictive_equations)
    }

    fun <T : Activity> launchQuickMethod(activityRule: ActivityTestRule<T>) {
        clickRecyclerViewItem(activityRule, R.id.home_buttons_recyclerView
                , R.string.btn_quick_method)
    }

    fun <T : Activity> launchAnthropometrics(activityRule: ActivityTestRule<T>) {
        clickRecyclerViewItem(activityRule, R.id.home_buttons_recyclerView
                , R.string.btn_anthropometrics)
    }

    fun <T : Activity> launchConversions(activityRule: ActivityTestRule<T>) {
        clickRecyclerViewItem(activityRule, R.id.home_buttons_recyclerView
                , R.string.btn_conversions)
    }
    //endregion

    //region Check Activities
    fun checkHomeActivityIsDisplayed(instrumentation: Instrumentation) {
        // have to check like this in the context of the HomeActivity because Intents.intended()
        // will not detect the "initially launched" Activity
        checkActionBarTitle(instrumentation, R.string.app_name)
    }

    fun checkPredictiveEquationsActivityIsDisplayed() {
        checkActivityIsDisplayed(PredictiveEquationsActivity::class.java.name)
    }

    fun checkQuickMethodIsActivityDisplayed() {
        checkActivityIsDisplayed(QuickMethodActivity::class.java.name)
    }

    fun checkAnthropometricsActivityIsDisplayed() {
        checkActivityIsDisplayed(AnthropometricsActivity::class.java.name)
    }

    fun checkConversionsActivityIsDisplayed() {
        checkActivityIsDisplayed(ConversionActivity::class.java.name)
    }
    //endregion
}

fun withHomeRobot(fn: HomeRobot.() -> Unit) = HomeRobot().apply(fn)