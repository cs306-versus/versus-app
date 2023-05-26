package com.github.versus;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static java.util.Objects.requireNonNull;

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
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;


import androidx.core.view.GravityCompat;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;


import com.github.versus.utils.FirebaseEmulator;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public final class ScheduleTest {




    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Switch the view to the Schedule
     */
    @Before public void testScheduleButtons2(){
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        onView(withId(R.id.nav_schedule)).perform(click());
    }

    @Test
    public void testing(){
        onView(withId(R.id.Tuesday_button)).perform(click());
    }

    @Test
    public void testingNextWeeks1(){
        for(int i = 0; i < 60; i++)
            onView(withId(R.id.arrow_11)).perform(click());
    }

    @Test
    public void testingNextWeeks2(){
        for(int i = 0; i < 60; i++)
            onView(withId(R.id.arrow_image_2)).perform(click());
    }

    @Test
    public void testing1(){
        onView(withId(R.id.Monday_button)).perform(click());
    }

    @Test
    public void testing2(){
        onView(withId(R.id.Wednesday_button)).perform(click());
    }

    @Test
    public void testing3(){
         onView(withId(R.id.Thursday_button)).perform(click());
    }

    @Test
    public void testing6(){
        onView(withId(R.id.Sunday_button)).perform(click());
    }

}
