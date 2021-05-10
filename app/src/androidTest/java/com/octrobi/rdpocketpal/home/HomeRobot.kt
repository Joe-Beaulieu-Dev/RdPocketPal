package com.octrobi.rdpocketpal.home

import android.app.Instrumentation
import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.anthropometrics.AnthropometricsActivity
import com.octrobi.rdpocketpal.conversions.ConversionActivity
import com.octrobi.rdpocketpal.predictiveequations.PredictiveEquationsActivity
import com.octrobi.rdpocketpal.quickmethod.QuickMethodActivity
import com.octrobi.rdpocketpal.testutil.TestRobot

class HomeRobot : TestRobot() {

    //region Launch Activities
    fun launchPredictiveEquations() {
        clickRecyclerViewItem(R.id.home_buttons_recyclerView, R.string.btn_predictive_equations)
    }

    fun launchQuickMethod() {
        clickRecyclerViewItem(R.id.home_buttons_recyclerView, R.string.btn_quick_method)
    }

    fun launchAnthropometrics() {
        clickRecyclerViewItem(R.id.home_buttons_recyclerView, R.string.btn_anthropometrics)
    }

    fun launchConversions() {
        clickRecyclerViewItem(R.id.home_buttons_recyclerView, R.string.btn_conversions)
    }
    //endregion

    //region Check Activities
    fun checkHomeActivityIsDisplayed() {
        checkActivityIsDisplayed(HomeActivity::class.java.name)
    }

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