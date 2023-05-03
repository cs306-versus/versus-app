package com.github.versus;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static java.util.Objects.requireNonNull;

import android.os.SystemClock;

import androidx.fragment.app.FragmentTransaction;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;


import android.graphics.Color;
import android.view.View;

import androidx.core.view.GravityCompat;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;



import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



import com.github.versus.auth.AuthActivity;



@RunWith(AndroidJUnit4.class)
public class TopGamesTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);
    @Before
    public void setUp() {
        Intents.init();
    }
    @After
    public void tearDown() {
        Intents.release();
    }



    @Test
    public void test1(){
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());


        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        onView(withId(R.id.nav_trending_sports)).perform(click());

        onView(withId(R.id.arrow_right)).perform(click());
        onView(withId(R.id.arrow_right)).perform(click());

        onView(withId(R.id.arrow_right)).perform(click());
        onView(withId(R.id.arrow_right)).perform(click());
        onView(withId(R.id.arrow_right)).perform(click());
        onView(withId(R.id.arrow_left)).perform(click());
        onView(withId(R.id.arrow_left)).perform(click());
        onView(withId(R.id.arrow_left)).perform(click());
        onView(withId(R.id.arrow_left)).perform(click());
        onView(withId(R.id.arrow_left)).perform(click());

        try {
            Thread.sleep(10000); // wait for 10 seconds
        } catch (InterruptedException e) {
            // handle the exception
        }
        onView(withId(R.id.rectangle_22)).perform(ViewActions.scrollTo()).perform(click());

        try {
            Thread.sleep(10000); // wait for 10 seconds
        } catch (InterruptedException e) {
            // handle the exception
        }


    }



}
