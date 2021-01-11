package com.octrobi.rdpocketpal.testutil

import android.app.Activity
import android.app.Instrumentation
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.os.SystemClock
import android.view.View
import android.widget.EditText
import android.widget.NumberPicker
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.octrobi.rdpocketpal.R
import com.octrobi.rdpocketpal.action.CustomViewActions.nestedScrollTo
import com.octrobi.rdpocketpal.matcher.ToastMatcher
import com.octrobi.rdpocketpal.matcher.clickClickableSpan
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*

@DslMarker
annotation class TestRobotMarker

@TestRobotMarker
open class TestRobot {

    //region Text entry
    protected fun enterText(@IdRes viewId: Int, text: String): ViewInteraction {
        return onView(withId(viewId)).perform(nestedScrollTo(), typeText(text), closeSoftKeyboard())
    }

    protected fun setTextProgrammatically(@IdRes id: Int, text: String): ViewInteraction {
        return onView(withId(id)).perform(nestedScrollTo(),
                object : ViewAction {
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
    }
    //endregion

    //region Text validation
    protected fun <T : Activity> checkText(activityRule: ActivityTestRule<T>
                                           , @IdRes viewId: Int
                                           , @StringRes stringId: Int): ViewInteraction {
        return checkText(viewId, TestUtil.getString(activityRule, stringId))
    }

    protected fun checkText(@IdRes viewId: Int, text: String): ViewInteraction {
        return onView(withId(viewId)).perform(nestedScrollTo()).check(matches(withText(text)))
    }

    fun checkActionBarTitle(instrumentation: Instrumentation, @StringRes stringId: Int) {
        val res: Resources = instrumentation.targetContext.resources
        val actionbarId: Int = res.getIdentifier("action_bar_container"
                , "id"
                , instrumentation.targetContext.packageName)
        onView(withId(actionbarId)).check(matches(hasDescendant(withText(stringId))))
    }
    //endregion

    //region View visibility
    @Suppress("SameParameterValue")
    protected fun checkViewWithIdIsDisplayed(@IdRes viewId: Int): ViewInteraction {
        return onView(withId(viewId)).perform(nestedScrollTo()).check(matches(isDisplayed()))
    }

    protected fun checkViewWithIdIsDisplayedNoScroll(@IdRes viewId: Int): ViewInteraction {
        return onView(withId(viewId)).check(matches(isDisplayed()))
    }

    @Suppress("SameParameterValue")
    protected fun checkViewWithTextIsDisplayed(@StringRes stringId: Int): ViewInteraction {
        return onView(withText(stringId)).perform(nestedScrollTo()).check(matches(isDisplayed()))
    }

    protected fun checkViewWithTextIsDisplayedNoScroll(@StringRes stringId: Int): ViewInteraction {
        return onView(withText(stringId)).check(matches(isDisplayed()))
    }

    protected fun checkViewWithTextIsDisplayedNoScroll(text: String): ViewInteraction {
        return onView(withText(text)).check(matches(isDisplayed()))
    }

    @Suppress("SameParameterValue")
    protected fun checkViewWithTextIsNotDisplayedNoScroll(@StringRes stringId: Int): ViewInteraction {
        return onView(withText(stringId)).check(doesNotExist())
    }
    //endregion

    //region Error validation
    @Suppress("SameParameterValue")
    protected fun <T : Activity> checkEditTextError(activityRule: ActivityTestRule<T>
                                                    , @IdRes viewId: Int
                                                    , @StringRes stringId: Int): ViewInteraction {
        return onView(withId(viewId))
                .perform(nestedScrollTo())
                .check(matches(hasErrorText(TestUtil.getString(activityRule, stringId))))
    }

    protected fun checkEditTextNoError(@IdRes viewId: Int): ViewInteraction {
        // need to use this value because of overload resolution
        // ambiguity on hasErrorText() when just using null
        val nullString: String? = null
        return onView(withId(viewId))
                .perform(nestedScrollTo())
                .check(matches(hasErrorText(nullString)))
    }
    //endregion

    //region Click
    protected fun clickViewId(@IdRes viewId: Int): ViewInteraction {
        return onView(withId(viewId)).perform(nestedScrollTo(), click())
    }

    @Suppress("SameParameterValue")
    protected fun clickViewIdNoScroll(@IdRes viewId: Int): ViewInteraction {
        return onView(withId(viewId)).perform(click())
    }

    protected fun clickViewTextNoScroll(@StringRes stringId: Int): ViewInteraction {
        return onView(withText(stringId)).perform(click())
    }

    @Suppress("SameParameterValue")
    protected fun clickClickableSpan(@IdRes viewId: Int
                                     , @StringRes clickTarget: Int): ViewInteraction {
        return onView(withId(viewId)).perform(nestedScrollTo(), clickClickableSpan(clickTarget))
    }

    fun pressUpNavigation() {
        onView(withContentDescription(androidx.appcompat.R.string.abc_action_bar_up_description))
                .perform(click())
    }

    fun pressBackButton() {
        pressBack()
    }
    //endregion

    //region Toast
    fun checkToastDisplayedWithMessage(@StringRes stringId: Int): ViewInteraction {
        return onView(withText(stringId)).inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }

    // TODO ToastMatcher just loops when there's no Toast on screen
    fun checkToastNotDisplayedWithMessage(@StringRes stringId: Int): ViewInteraction {
        return onView(withText(stringId))
                .inRoot(ToastMatcher())
                .check(doesNotExist())
    }
    //endregion

    //region RadioButton
    protected fun checkRadioBtnIsChecked(@IdRes id: Int): ViewInteraction {
        return onView(withId(id)).perform(nestedScrollTo()).check(matches(isChecked()))
    }
    //endregion

    //region Spinner
    protected fun <T : Activity> clickSpinnerItem(activityRule: ActivityTestRule<T>
                                                  , @IdRes spinnerId: Int
                                                  , @StringRes stringId: Int): ViewInteraction {
        return clickSpinnerItem(spinnerId, TestUtil.getString(activityRule, stringId))
    }

    private fun clickSpinnerItem(@IdRes spinnerId: Int
                                   , selection: String): ViewInteraction {
        // click conversion spinner
        clickViewId(spinnerId)
        // find target item and click it
        return onData(allOf(`is`(instanceOf(String::class.java)), `is`(selection))).perform(click())
    }

    protected fun checkSpinnerSelection(@IdRes spinnerId: Int
                                        , @StringRes stringId: Int): ViewInteraction {
        return onView(withId(spinnerId))
                .perform(nestedScrollTo())
                .check(matches(withSpinnerText(stringId)))
    }
    //endregion

    //region RecyclerView
    @Suppress("SameParameterValue")
    protected fun <T : Activity> clickRecyclerViewItem(activityRule: ActivityTestRule<T>
                                                                 , @IdRes recyclerViewId: Int
                                                                 , @StringRes stringId: Int): ViewInteraction {
        // click on the ViewHolder that has the specified text (Activity name)
        return onView(withId(recyclerViewId))
                .perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                        hasDescendant(withText(TestUtil.getString(activityRule, stringId)))
                        , click()))
    }
    //endregion

    //region NumberPicker
    @Suppress("SameParameterValue")
    protected fun setNumberPickerValue(@IdRes numberPickerId: Int, num: Int): ViewInteraction {
        return onView(withId(numberPickerId)).perform(object : ViewAction {
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
    }
    //endregion

    //region Menu
    fun openDisclaimer() {
        // open overflow menu
        openActionBarOverflowOrOptionsMenu(
                InstrumentationRegistry.getInstrumentation().targetContext)
        // open disclaimer screen
        onView(withText(R.string.menu_disclaimer)).perform(click())
    }

    fun openSettings() {
        // open overflow menu
        openActionBarOverflowOrOptionsMenu(
                InstrumentationRegistry.getInstrumentation().targetContext)
        // open settings screen
        onView(withText(R.string.menu_settings)).perform(click())
    }
    //endregion

    //region Orientation
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
        // waitForIdleSync() doesn't get the job done; still getting errors trying to use it.
        // Need to use sleep as well or else we'll still get errors. Ex: PredictiveEquationsTest,
        // rotating the screen twice with waitForIdleSync() and then trying to click on the equation
        // Spinner leads to an error without using sleep.
        instrumentation.waitForIdleSync()
        SystemClock.sleep(500)
    }
    //endregion

    //region Activity
    protected fun checkActivityIsDisplayed(className: String) {
        intended(hasComponent(className))
    }
    //endregion
}