package com.github.versus;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.actionWithAssertions;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.assertion.ViewAssertions.matches;


import static org.hamcrest.Matchers.anything;

import androidx.core.view.GravityCompat;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;



import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;






@RunWith(AndroidJUnit4.class)
public class GameFragmentTest {
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
    public void testScheduleButtons2(){
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());

        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        onView(withId(R.id.nav_schedule)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(0).perform(click());
        ViewInteraction imageView = onView(ViewMatchers.withId(R.id.frame_2));

// Scroll to the view
        imageView.perform(ViewActions.scrollTo());
        ViewInteraction rectangleView = onView(withId(R.id.rectangle1));
        rectangleView.perform(ViewActions.scrollTo());
        onData(anything())
                .inAdapterView(withId(R.id.list_players_view))
                .atPosition(0)
                .onChildView(withId(R.id.rate_button))
                .perform(click());
// Scroll to the view

    }
    }