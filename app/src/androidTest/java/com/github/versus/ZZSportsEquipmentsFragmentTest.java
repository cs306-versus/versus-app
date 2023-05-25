package com.github.versus;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.core.view.GravityCompat;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.utils.FirebaseEmulator;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
//Class to test the functinalities of the Sport Equipments Fragment
public final class ZZSportsEquipmentsFragmentTest {

    static {
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
    }

    //Setting the rule for the ActivityScenario
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Switch the view to the Schedule
     */

    /**
     * Opening the drawer and selecting the nav_sport_equipments option in the menu
     */
    @Before
    public void before_routine() {
        //Open the drawer
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        //Select the nav_sport_equipment option in menu
        onView(withId(R.id.nav_sport_equipments)).perform(click());
    }

    /**
     * This method tests the functionalities of the fragment including the contact button and the filtering of the posts.
     */
    @Test
    public void testing() {
        //Scrolling down until the right arrow is visible
        ViewInteraction rectangleView1 = onView(withId(R.id.the_real_right_arrow));
        rectangleView1.perform(ViewActions.scrollTo());
        //Performing multiple clicks on the left and right arrow to test all the branches for high test coverage
        onView(withId(R.id.the_real_right_arrow)).perform(click());
        onView(withId(R.id.the_real_right_arrow)).perform(click());
        onView(withId(R.id.the_real_right_arrow)).perform(click());
        onView(withId(R.id.the_real_left_arrow)).perform(click());
        onView(withId(R.id.the_real_left_arrow)).perform(click());
        onView(withId(R.id.the_real_left_arrow)).perform(click());
        //Scrolling down until the contact_person button is visible
        ViewInteraction rectangleView2 = onView(withId(R.id.contact_person));
        rectangleView2.perform(ViewActions.scrollTo());

        try {
            Thread.sleep(2000); // wait for 10 seconds
        } catch (InterruptedException e) {
            // handle the exception
        }
        //Clicking on the contact person button
        onView(withId(R.id.contact_person)).perform(click());
        //Clicking on the back to posts button, before that we need to scroll down until it is visible
        ViewInteraction rectangleView3 = onView(withId(R.id.back_to_posts_container));

        rectangleView3.perform(ViewActions.scrollTo());
        try {
            Thread.sleep(2000); // wait for 10 seconds
        } catch (InterruptedException e) {
            // handle the exception
        }
        onView(withId(R.id.back_to_posts_container)).perform(click());
        //Filtering the posts with the keyword Boxing
        onView(withId(R.id.search_for_post)).perform(typeText("Boxing"), closeSoftKeyboard());


        try {
            Thread.sleep(2000); // wait for 10 seconds
        } catch (InterruptedException e) {
            // handle the exception
        }


    }


}

