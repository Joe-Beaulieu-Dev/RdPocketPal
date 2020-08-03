package com.example.rdpocketpal2.home

import android.app.Activity
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.conversions.ConversionActivity
import com.example.rdpocketpal2.predictiveequations.PredictiveEquationsActivity
import com.example.rdpocketpal2.quickmethod.QuickMethodActivity
import com.example.rdpocketpal2.testutil.TestRobot

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
    fun checkPredictiveEquationsActivityIsDisplayed() {
        checkActivityIsDisplayed(PredictiveEquationsActivity::class.java.name)
    }

    fun checkQuickMethodIsActivityDisplayed() {
        checkActivityIsDisplayed(QuickMethodActivity::class.java.name)
    }

    fun checkAnthropometricsActivityIsDisplayed() {
        // TODO change this once this Activity is actually created
        checkActivityIsDisplayed(PredictiveEquationsActivity::class.java.name)
    }

    fun checkConversionsActivityIsDisplayed() {
        checkActivityIsDisplayed(ConversionActivity::class.java.name)
    }
    //endregion
}

fun withHomeRobot(fn: HomeRobot.() -> Unit) = HomeRobot().apply(fn)