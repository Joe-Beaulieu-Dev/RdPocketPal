package com.example.rdpocketpal2.testutil

import android.app.Activity
import android.app.Instrumentation
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.View
import android.widget.EditText
import android.widget.NumberPicker
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.rdpocketpal2.R
import org.hamcrest.Matcher

@DslMarker
annotation class TestRobotMarker

@TestRobotMarker
open class TestRobot {
    protected fun enterText(@IdRes viewId: Int, text: String): ViewInteraction =
            onView(withId(viewId)).perform(typeText(text), closeSoftKeyboard())

    protected fun setTextProgrammatically(@IdRes id: Int, text: String): ViewInteraction =
            onView(withId(id)).perform(object: ViewAction {
                override fun getDescription(): String {
                    return "Set the value of an EditText programmatically"
                }

                override fun getConstraints(): Matcher<View> {
                    return isAssignableFrom(EditText::class.java)
                }

                override fun perform(uiController: UiController?, view: View?) {
                    (view as EditText).setText(text)
                }
            })

    protected fun checkEditTextError(@IdRes viewId: Int, errorText: String?): ViewInteraction =
            onView(withId(viewId)).check(matches(hasErrorText(errorText)))

    protected fun clickViewId(@IdRes viewId: Int): ViewInteraction =
            onView(withId(viewId)).perform(click())

    protected fun clickViewText(@IdRes viewId: Int): ViewInteraction =
            onView(withText(viewId)).perform(click())

    protected fun checkText(@IdRes viewId: Int, text: String): ViewInteraction =
            onView(withId(viewId)).check(matches(withText(text)))

    protected fun setNumberPickerValue(@IdRes id: Int, num: Int): ViewInteraction =
            onView(withId(id)).perform(object : ViewAction {
                override fun getDescription(): String {
                    return "Set the value of a NumberPicker"
                }

                override fun getConstraints(): Matcher<View> {
                    return isAssignableFrom(NumberPicker::class.java)
                }

                override fun perform(uiController: UiController?, view: View?) {
                    (view as NumberPicker).value = num
                }
            })

    protected fun openPreferences() {
        // open overflow menu
        openActionBarOverflowOrOptionsMenu(
                InstrumentationRegistry.getInstrumentation().targetContext)
        // open settings screen
        onView(withText(R.string.text_settings)).perform(click())
    }

    fun <T : Activity> rotateScreen(rule: ActivityTestRule<T>, instrumentation: Instrumentation) {
        // get current orientation and set value to opposite
        val orientation: Int =
                if (rule.activity.resources.configuration.orientation
                        == Configuration.ORIENTATION_PORTRAIT) {
                    ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                } else {
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }

        // set orientation on activity
        rule.activity.requestedOrientation = orientation

        // wait for screen to finish rotating
        instrumentation.waitForIdleSync()
    }
}