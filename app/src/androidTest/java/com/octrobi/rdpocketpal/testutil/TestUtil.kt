package com.octrobi.rdpocketpal.testutil

import android.app.Activity
import android.content.Context
import androidx.annotation.IdRes
import androidx.preference.PreferenceManager
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.octrobi.rdpocketpal.R

object TestUtil {

    fun <T : Activity> getString(rule: ActivityTestRule<T>, @IdRes id: Int): String {
        return rule.activity.getString(id)
    }

    fun getString(@IdRes resId: Int): String =
            getString(InstrumentationRegistry.getInstrumentation().targetContext, resId)

    private fun getString(context: Context, @IdRes id: Int): String {
        return context.getString(id)
    }

    fun setDecimalReductionMethodPref(@IdRes stringId: Int) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(
                InstrumentationRegistry.getInstrumentation().targetContext)

        // set pref
        with(prefs.edit()) {
            putString(getString(InstrumentationRegistry.getInstrumentation().targetContext,
                    R.string.key_decimal_reduction_method),
                    getString(InstrumentationRegistry.getInstrumentation().targetContext,
                            stringId))
            commit()
        }
    }

    fun setNumericScalePref(scale: Int) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(
                InstrumentationRegistry.getInstrumentation().targetContext)

        // set pref
        with(prefs.edit()) {
            putInt(getString(InstrumentationRegistry.getInstrumentation().targetContext,
                    R.string.key_numeric_scale), scale)
            commit()
        }
    }

    fun setIfDisclaimerAccepted(context: Context, disclaimerAccepted: Boolean) {
        val prefs = context.getSharedPreferences(
                context.getString(R.string.key_disclaimer_pref_file)
                , Context.MODE_PRIVATE)

        // set pref
        with(prefs.edit()) {
            putBoolean(context.getString(R.string.key_disclaimer_accepted), disclaimerAccepted)
            commit()
        }
    }
}