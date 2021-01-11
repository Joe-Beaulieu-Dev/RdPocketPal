package com.octrobi.rdpocketpal.matcher

import android.text.SpannableString
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matcher

fun clickClickableSpan(@StringRes textToClickId: Int): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = isAssignableFrom(TextView::class.java)

        override fun getDescription(): String = "click on ClickableSpan"

        override fun perform(uiController: UiController?, view: View?) {
            val textView = view as TextView
            val spannableString: SpannableString = textView.text as SpannableString

            if (spannableString.isEmpty()) {
                throw IllegalStateException("SpannableString is empty")
            }

            val textToClick = InstrumentationRegistry
                    .getInstrumentation().targetContext.resources.getString(textToClickId)
            val spans = spannableString
                    .getSpans(0, spannableString.length, ClickableSpan::class.java)
            // search for target span and click it
            if (spans.isNotEmpty()) {
                spans.forEach {
                    val start = spannableString.getSpanStart(it)
                    val end = spannableString.getSpanEnd(it)
                    val sequence = spannableString.subSequence(start, end)
                    if (textToClick == sequence.toString()) {
                        it.onClick(textView)
                        return
                    }
                }
                throw IllegalStateException("No matching ClickableSpans in SpannableString")
            }
            throw IllegalStateException("SpannableString has no spans")
        }
    }
}