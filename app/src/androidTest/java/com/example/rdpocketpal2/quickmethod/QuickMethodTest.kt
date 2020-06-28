package com.example.rdpocketpal2.quickmethod

import android.view.View
import android.widget.NumberPicker
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import com.example.rdpocketpal2.testutil.TestUtil
import org.hamcrest.Matcher
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class QuickMethodTest {

    @get:Rule
    var activityRule: ActivityTestRule<QuickMethodActivity> =
            ActivityTestRule(QuickMethodActivity::class.java)

    companion object {
        @BeforeClass
        @JvmStatic
        fun before() {
            TestUtil.setDecimalReductionMethodPref(
                    InstrumentationRegistry.getInstrumentation().targetContext
                    , R.string.key_rounding)
            TestUtil.setNumericScalePref(
                    InstrumentationRegistry.getInstrumentation().targetContext, 2)
        }
    }

    @Test
    fun unitRadioGroup_changesUnitLabels_standard() {
        // set unit to standard
        setStandard()

        // check unit label text
        onView(withId(R.id.qm_weight_unit_label))
                .check(matches(withText(TestUtil.getString(activityRule, R.string.text_lb))))
    }

    @Test
    fun unitRadioGroup_changesUnitLabels_metric() {
        // set unit to metric
        setMetric()

        // check unit label text
        onView(withId(R.id.qm_weight_unit_label))
                .check(matches(withText(TestUtil.getString(activityRule, R.string.text_kg))))
    }

    @Test
    fun calories_calculate_metric() {
        // enter weight
        onView(withId(R.id.qm_weight_editText))
                .perform(typeText("75"), closeSoftKeyboard())
        // enter kcal/kg min
        onView(withId(R.id.qm_calorie_kcal_per_kg_min))
                .perform(typeText("20.25"), closeSoftKeyboard())
        // enter kcal/kg max
        onView(withId(R.id.qm_calorie_kcal_per_kg_max))
                .perform(typeText("30"), closeSoftKeyboard())

        // click calculate
        onView(withId(R.id.qm_calorie_calculate_btn)).perform(click())

        // validate kcal/day min output
        onView(withId(R.id.qm_calorie_kcal_per_day_min)).check(matches(withText("1518.75")))
        // validate kcal/day max output
        onView(withId(R.id.qm_calorie_kcal_per_day_max)).check(matches(withText("2250")))
    }

    //region Helper methods
    private fun setNumericScaleViaUi(scale: Int) {
        // open preferences
        openPreferences()
        // click numeric scale setting
        onView(withText(R.string.text_numeric_scale)).perform(click())
        // set value to 2
        onView(withId(R.id.pref_dialog_number_picker)).perform(object : ViewAction {
            override fun getDescription(): String {
                return "Set the value of a NumberPicker"
            }

            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(NumberPicker::class.java)
            }

            override fun perform(uiController: UiController?, view: View?) {
                (view as NumberPicker).value = scale
            }

        })
        // click OK (positive button) to close dialog
        onView(withId(android.R.id.button1)).perform(click())
        // exit settings screen
        pressBack()
    }

    private fun setDecimalReductionMethodUi(@IdRes stringId: Int) {
        // open preferences
        openPreferences()
        // click decimal reduction setting
        onView(withText(R.string.text_decimal_reduction_method)).perform(click())
        // set value to rounding
        onView(withText(stringId))
        // click Cancel (negative button) to close dialog
        onView(withId(android.R.id.button2)).perform(click())
        // exit settings screen
        pressBack()
    }

    private fun openPreferences() {
        // open overflow menu
        openActionBarOverflowOrOptionsMenu(
                InstrumentationRegistry.getInstrumentation().targetContext)
        // open settings screen
        onView(withText(R.string.text_settings)).perform(click())
    }

    private fun setMetric() {
        // click metric RadioButton
        onView(withId(R.id.qm_unit_metric)).perform(click())
    }

    private fun setStandard() {
        // click standard RadioButton
        onView(withId(R.id.qm_unit_standard)).perform(click())
    }
    //endregion
}