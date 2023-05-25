package com.github.versus;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
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


import com.github.versus.auth.AuthActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class GameFragmentTest {
    //General rule that sets up the Activity Scenario Rule
    @Rule
    public ActivityScenarioRule<AuthActivity> scenario = new ActivityScenarioRule<>(AuthActivity.class);

    //Initiating the intents
    @Before
    public void setUp() {
        Intents.init();
    }

    // Releasing the intents after the test
    @After
    public void tearDown() {
        Intents.release();
    }

    /**
     * In this test , we are testing the game fragment, we first start by  opening the drawer menu ,
     * and going to the schedule fragment we then click on  one of the games to see the game fragment
     */
    @Test
    public void testGameFragment() {
        try {
            Thread.sleep(5000); // wait for 10 seconds
        } catch (InterruptedException e) {
            // handle the exception
        }
        onView(withId(R.id.signin_btn)).perform(click());
        try {
            Thread.sleep(5000); // wait for 10 seconds
        } catch (InterruptedException e) {
            // handle the exception
        }
      onView(ViewMatchers.withId(R.id.mail))
                .perform(typeText("john.doe@versus.ch"), closeSoftKeyboard());

        // Enter password
        onView(ViewMatchers.withId(R.id.pwd))
                .perform(typeText("123456789"), closeSoftKeyboard());
        try {
            Thread.sleep(5000); // wait for 10 seconds
        } catch (InterruptedException e) {
            // handle the exception
        }

        // Click the Sign In button
        onView(ViewMatchers.withId(R.id.signin))
                .perform(click());
        try {
            Thread.sleep(10000); // wait for 10 seconds
        } catch (InterruptedException e) {
            // handle the exception
        }
        onView(withId(R.id.arrow_11)).perform(click());
        //Opening the drawer
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        //Clicking on the  nav_schedule button
        onView(withId(R.id.nav_schedule)).perform(click());

        //Getting back two weeks before where there is a game in the database
        onView(withId(R.id.arrow_11)).perform(click());
        onView(withId(R.id.arrow_11)).perform(click());
        onView(withId(R.id.arrow_11)).perform(click());
        onView(withId(R.id.arrow_11)).perform(click());
        onView(withId(R.id.Tuesday_button)).perform(click());

        //Scrolling down so that  the first item of the list of games is visible
        ViewInteraction rectangleView1 = onView(withId(R.id.rectangle1));
        rectangleView1.perform(ViewActions.scrollTo());
        rectangleView1.perform(ViewActions.scrollTo());
        rectangleView1.perform(ViewActions.scrollTo());
        rectangleView1.perform(ViewActions.scrollTo());


        //Performing the click on the first item of the games in the schedule
        onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(0).perform(click());

    }
}