package com.example.rdpocketpal2.predictiveequations;

import com.example.rdpocketpal2.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.rdpocketpal2.Util.getString;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class ConversionTest {

    @Rule
    public ActivityTestRule<PredictiveEquationsActivity> rule
            = new ActivityTestRule<>(PredictiveEquationsActivity.class);

    @Test
    public void appropriateStandardHintsTest() {
        // Set standard values
        onView((withText(R.string.text_standard))).perform(click());

        // Check for appropriate hints
        onView(withId(R.id.pe_weight_unit_label)).check(matches(withText(getString(rule, R.string.text_lb))));
        onView(withId(R.id.pe_height_unit_label)).check(matches(withText(getString(rule, R.string.text_in))));
    }

    @Test
    public void appropriateMetricHintsTest() {
        // Set metric values
        onView((withText(R.string.text_metric))).perform(click());

        // Check for appropriate hints
        onView(withId(R.id.pe_weight_unit_label)).check(matches(withText(getString(rule, R.string.text_kg))));
        onView(withId(R.id.pe_height_unit_label)).check(matches(withText(getString(rule, R.string.text_cm))));
    }

}