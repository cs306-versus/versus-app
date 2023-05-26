package com.github.versus;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static java.util.Objects.requireNonNull;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;


import androidx.core.view.GravityCompat;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class TopGamesTest {

    //General rule that sets up the Activity Scenario Rule
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);

    //Initiating the intents
    @Before
    public void setUp() {
        Intents.init();
    }

    //Releasing the intents
    @After
    public void tearDown() {
        Intents.release();
    }


    /**
     * This method tests the first position of the trending sports by clicking on the appropriate picture and
     * showing the filtered posts of the sport
     */
    @Test
    public void testOnFirstPosition() {
        //Opening the drawer menu
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        //Performing the click on the trending sports button
        onView(withId(R.id.nav_trending_sports)).perform(click());
        //Performing multiple clicks  on the left and right arrow in order to cover all the branches  in the code
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
        //Clicking on the image in order to show the filtered posts of the trending sport.

        try {
            Thread.sleep(10000); // wait for 10 seconds
        } catch (InterruptedException e) {
            // handle the exception
        }


    }

    /**
     * This method tests the second position of the trending sports by clicking on the appropriate picture and
     * showing the filtered posts of the sport
     */



}
