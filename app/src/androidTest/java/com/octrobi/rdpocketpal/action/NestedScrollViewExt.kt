package com.octrobi.rdpocketpal.action

import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ListView
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anyOf

// Using delegation instead of a custom ViewAction because ScrollToAction is final and
// getConstraints() is the only method that needs to be overridden. Using private constructor with
// secondary delegator constructor because the passing of other ViewActions besides ScrollToAction
// is not wanted.
class NestedScrollViewExt private constructor(scrollToAction: ViewAction)
    : ViewAction by scrollToAction {

    constructor() : this(ViewActions.scrollTo())

    override fun getConstraints(): Matcher<View> {
        return allOf(
                withEffectiveVisibility(Visibility.VISIBLE),
                isDescendantOfA(
                        anyOf(
                                isAssignableFrom(NestedScrollView::class.java),
                                isAssignableFrom(ScrollView::class.java),
                                isAssignableFrom(HorizontalScrollView::class.java),
                                isAssignableFrom(ListView::class.java))))
    }
}