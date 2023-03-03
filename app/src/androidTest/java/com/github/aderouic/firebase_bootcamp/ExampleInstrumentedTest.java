package com.github.aderouic.firebase_bootcamp;

import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;

import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.MatcherAssert.assertThat;


import android.view.View;
import android.widget.EditText;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> testRule = new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void getSetTest(){


        androidx.test.espresso.ViewInteraction phone = androidx.test.espresso.Espresso.onView(ViewMatchers.withId(R.id.editTextPhone));
        androidx.test.espresso.ViewInteraction email = androidx.test.espresso.Espresso.onView(ViewMatchers.withId(R.id.editTextTextEmailAddress));
        androidx.test.espresso.ViewInteraction getButton = androidx.test.espresso.Espresso.onView(ViewMatchers.withId(R.id.GET));
        androidx.test.espresso.ViewInteraction setButton = androidx.test.espresso.Espresso.onView(ViewMatchers.withId(R.id.SET));

        phone.perform(typeText("0000"));
        phone.perform(closeSoftKeyboard());
        email.perform(typeText("124@epfl.ch"));
        phone.perform(closeSoftKeyboard());

        setButton.perform(click());

        //clearing the email field
        email.perform(clearText());

        //clicking the get Button
        getButton.perform(click());
        Matcher<View> e = withText("124@epfl.ch");
        email.check(matches(e));

    }
}

