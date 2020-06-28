package com.example.rdpocketpal2.testutil

import android.app.Activity
import android.content.Context
import androidx.annotation.IdRes
import androidx.preference.PreferenceManager
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R

object TestUtil {

    fun <T : Activity> getString(rule: ActivityTestRule<T>, @IdRes id: Int): String {
        return rule.activity.getString(id)
    }

    private fun getString(context: Context, @IdRes id: Int): String {
        return context.getString(id)
    }

    fun setDecimalReductionMethodPref(context: Context, @IdRes stringId: Int) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        // set pref
        with(prefs.edit()) {
            putString(getString(context, R.string.key_decimal_reduction_method)
                    , getString(context, stringId))
            commit()
        }
    }

    fun setNumericScalePref(context: Context, scale: Int) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)

        // set pref
        with(prefs.edit()) {
            putInt(getString(context, R.string.key_numeric_scale), scale)
            commit()
        }
    }
}