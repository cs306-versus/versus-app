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

    private static final String test_account_mail = "demo@versus.ch";

    private static final String test_account_pwd = "123456789";

    @Test
    public void testScheduleButtons(){
        onView(withId(R.id.rectangle_18)).perform(click());
        onView(withId(R.id.rectangle_17)).perform(click());
        onView(withId(R.id.rectangle_3)).perform(click());
        onView(withId(R.id.rectangle_14)).perform(click());





        scenario.getScenario().onActivity(a -> requireNonNull(a.getPackageName()));
    }



}