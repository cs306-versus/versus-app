package com.github.versus;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.assertion.ViewAssertions.matches;


import static org.hamcrest.Matchers.anything;

import androidx.core.view.GravityCompat;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;


import com.github.versus.auth.AuthActivity;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.UiController;

import android.view.View;
import android.widget.Button;

@RunWith(AndroidJUnit4.class)
public class TestSearch {
    //General rule that sets up the Activity Scenario Rule
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);

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
            Thread.sleep(5000); // wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Opening the drawer
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        //Clicking on the  nav_schedule button
        onView(withId(R.id.nav_search)).perform(click());
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom(View.class);
                    }

                    @Override
                    public String getDescription() {
                        return "Click on button within RecyclerView item";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        // Get the button from the item's layout
                        View button = view.findViewById(R.id.joinhold1);
                        if (button != null) {
                            // Perform a click on the button
                            button.performClick();
                            try {
                                Thread.sleep(2000); // wait for 1 second
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            button.performClick();

                        }
                    }
                }));
        try {
            Thread.sleep(2000); // wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        //Scrolling down so that  the first item of the list of games is visible
      //  RecyclerView rectangleView1 = onView(withId(R.id.recyclerView));
        try {
            Thread.sleep(2000); // wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(2000); // wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();


        }
        //Performing the click on the first item of the games in the schedule
       // onData(anything()).inAdapterView(withId(R.id.recyclerView)).atPosition(0).perform(click());
        try {
            Thread.sleep(2000); // wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}