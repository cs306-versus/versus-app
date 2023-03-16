package com.github.versus;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static java.util.Objects.requireNonNull;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ScheduleTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);



    @Test
    public void testScheduleButtons1(){
        onView(withId(R.id.Monday_button)).perform(click());
        onView(withId(R.id.Monday_button)).perform(click());
        onView(withId(R.id.Tuesday_button)).perform(click());
        onView(withId(R.id.Tuesday_button)).perform(click());


        scenario.getScenario().onActivity(a -> requireNonNull(a.getPackageName()));
    }
    public void testScheduleButtons2(){

        onView(withId(R.id.Wednesday_button)).perform(click());
        onView(withId(R.id.Wednesday_button)).perform(click());
        onView(withId(R.id.Thursday_button)).perform(click());
        onView(withId(R.id.Thursday_button)).perform(click());








        scenario.getScenario().onActivity(a -> requireNonNull(a.getPackageName()));
    }
    public void testScheduleButtons3(){

        onView(withId(R.id.Friday_button)).perform(click());
        onView(withId(R.id.Friday_button)).perform(click());

        onView(withId(R.id.Saturday_button)).perform(click());
        onView(withId(R.id.Saturday_button)).perform(click());








        scenario.getScenario().onActivity(a -> requireNonNull(a.getPackageName()));
    }
    public void testScheduleButtons4(){

        onView(withId(R.id.Sunday_button)).perform(click());
        onView(withId(R.id.Sunday_button)).perform(click());










        scenario.getScenario().onActivity(a -> requireNonNull(a.getPackageName()));
    }



}