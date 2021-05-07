package com.octrobi.rdpocketpal.matcher

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`

object TextInputLayoutMatchers {
    fun withSuffix(text: String): Matcher<View> {
        return withSuffix(`is`(text))
    }

    private fun withSuffix(stringMatcher: Matcher<String>): Matcher<View> =
            WithSuffixMatcher(stringMatcher)

    fun withError(text: String?): Matcher<View> {
        return withError(`is`(text))
    }

    private fun withError(stringMatcher: Matcher<String?>): Matcher<View> =
        WithErrorMatcher(stringMatcher)

    class WithSuffixMatcher(private val stringMatcher: Matcher<String>)
        : BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {

        override fun describeTo(description: Description?) {
            description?.appendText("with suffix text $description")
        }

        override fun matchesSafely(item: TextInputLayout?): Boolean {
            // toString() because text derived from Strings with HTML
            // tags, etc. will return as a SpannedString and crash
            return stringMatcher.matches(item?.suffixText.toString())
        }
    }

    class WithErrorMatcher(private val stringMatcher: Matcher<String?>)
        : BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {

        override fun describeTo(description: Description?) {
            description?.appendText("with error text $description")
        }

        override fun matchesSafely(item: TextInputLayout?): Boolean {
            // toString() because text derived from Strings with HTML
            // tags, etc. will return as a SpannedString and crash
            return stringMatcher.matches(item?.error?.toString())
        }
    }
}