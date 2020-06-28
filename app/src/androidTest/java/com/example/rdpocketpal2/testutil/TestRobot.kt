package com.example.rdpocketpal2.testutil

import android.view.View
import android.widget.NumberPicker
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.example.rdpocketpal2.R
import org.hamcrest.Matcher

open class TestRobot {
    fun enterText(@IdRes viewId: Int, text: String): ViewInteraction =
            onView(ViewMatchers.withId(viewId))
                    .perform(ViewActions.typeText(text)
                            , ViewActions.closeSoftKeyboard())

    fun clickViewId(@IdRes viewId: Int): ViewInteraction =
            onView(ViewMatchers.withId(viewId))
                    .perform(ViewActions.click())

    fun clickViewText(@IdRes viewId: Int): ViewInteraction =
            onView(ViewMatchers.withText(viewId))
                    .perform(ViewActions.click())

    fun checkText(@IdRes viewId: Int, text: String): ViewInteraction =
            onView(ViewMatchers.withId(viewId))
                    .check(ViewAssertions.matches(ViewMatchers.withText(text)))

    fun setNumberPickerValue(@IdRes id: Int, num: Int): ViewInteraction =
            onView(ViewMatchers.withId(id)).perform(object : ViewAction {
                override fun getDescription(): String {
                    return "Set the value of a NumberPicker"
                }

                override fun getConstraints(): Matcher<View> {
                    return ViewMatchers.isAssignableFrom(NumberPicker::class.java)
                }

                override fun perform(uiController: UiController?, view: View?) {
                    (view as NumberPicker).value = num
                }
            })

    fun openPreferences() {
        // open overflow menu
        Espresso.openActionBarOverflowOrOptionsMenu(
                InstrumentationRegistry.getInstrumentation().targetContext)
        // open settings screen
        Espresso.onView(ViewMatchers.withText(R.string.text_settings)).perform(ViewActions.click())
    }
}