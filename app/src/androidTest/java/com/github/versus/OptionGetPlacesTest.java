package com.github.versus;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import androidx.core.view.GravityCompat;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class OptionGetPlacesTest {
    // Declare activity rule and permission rule
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);
    @Rule
    public GrantPermissionRule grantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    // Set up the testing environment before each test
    @Before
    public void setUp() {
        Intents.init();
        //Open the drawer_layout
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        onView(withId(R.id.nav_location)).perform(click());

    }

    // Clean up the testing environment after each test
    @After
    public void tearDown() {

        Intents.release();

    }
    // Test for canceling the get places operation
    @Test
    public void testCancel() throws InterruptedException {
        long waitingTime = 10000;
        ElapsedTimeIdlingResource idlingResourceFirst = new ElapsedTimeIdlingResource(waitingTime);
        IdlingRegistry.getInstance().register(idlingResourceFirst);
        String placeName = "GooglePlex Football";
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Get Place")).perform(click());


        onView(withId(R.id.edit_text_radius2)).perform(typeText("1500"));
        closeSoftKeyboard();
        onView(withText("Show Places")).inRoot(isDialog())
                .perform(click());

        long waitingTime2 = 10000;
        ElapsedTimeIdlingResource idlingResource2 = new ElapsedTimeIdlingResource(waitingTime2);
        IdlingRegistry.getInstance().register(idlingResource2);
        onView(withText("Cancel")).perform(click());
        IdlingRegistry.getInstance().unregister(idlingResource2);
        IdlingRegistry.getInstance().unregister(idlingResourceFirst);
    }
    // Test for clicking on a specific location
    @Test
    public void testClickOnLocation() throws InterruptedException {
        long waitingTime = 10000;
        ElapsedTimeIdlingResource idlingResourceFirst = new ElapsedTimeIdlingResource(waitingTime);
        IdlingRegistry.getInstance().register(idlingResourceFirst);
        String placeName = "GooglePlex Football";
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Get Place")).perform(click());


        onView(withId(R.id.edit_text_radius2)).perform(typeText("1500"));
        closeSoftKeyboard();
        onView(withText("Show Places")).inRoot(isDialog())
                .perform(click());
        long waitingTime2 = 10000;
        ElapsedTimeIdlingResource idlingResource2 = new ElapsedTimeIdlingResource(waitingTime2);
        IdlingRegistry.getInstance().register(idlingResource2);
        onView(withText(placeName)).perform(click());
        IdlingRegistry.getInstance().unregister(idlingResource2);
        IdlingRegistry.getInstance().unregister(idlingResourceFirst);
    }
    // Test for the case when no radius is input
    @Test
    public void testIfNoRadiusInput() throws InterruptedException {
        long waitingTime = 10000;
        ElapsedTimeIdlingResource idlingResourceFirst = new ElapsedTimeIdlingResource(waitingTime);
        IdlingRegistry.getInstance().register(idlingResourceFirst);
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click

        onView(withText("Get Place")).perform(click());
        closeSoftKeyboard();
        onView(withText("Show Places")).inRoot(isDialog())
                .perform(click());

        long waitingTime2 = 10000;
        ElapsedTimeIdlingResource idlingResource2 = new ElapsedTimeIdlingResource(waitingTime2);
        IdlingRegistry.getInstance().register(idlingResource2);
        IdlingRegistry.getInstance().unregister(idlingResource2);
        IdlingRegistry.getInstance().unregister(idlingResourceFirst);
    }
    // Test for the case when there are no places within the specified radius
    @Test
    public void testIfNoPlacesWithinRadius() throws InterruptedException {
        long waitingTime = 10000;
        ElapsedTimeIdlingResource idlingResourceFirst = new ElapsedTimeIdlingResource(waitingTime);
        IdlingRegistry.getInstance().register(idlingResourceFirst);
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Get Place")).perform(click());


        onView(withId(R.id.edit_text_radius2)).perform(typeText("100"));
        closeSoftKeyboard();
        onView(withText("Show Places")).inRoot(isDialog())
                .perform(click());
        long waitingTime2 = 10000;
        ElapsedTimeIdlingResource idlingResource2 = new ElapsedTimeIdlingResource(waitingTime2);
        IdlingRegistry.getInstance().register(idlingResource2);

        IdlingRegistry.getInstance().unregister(idlingResource2);
        IdlingRegistry.getInstance().unregister(idlingResourceFirst);
    }
    // ElapsedTimeIdlingResource class for waiting a specified amount of time before continuing
    public class ElapsedTimeIdlingResource implements IdlingResource {
        private final long startTime;
        private final long waitingTime;
        private ResourceCallback resourceCallback;

        public ElapsedTimeIdlingResource(long waitingTime) {
            this.startTime = System.currentTimeMillis();
            this.waitingTime = waitingTime;
        }

        @Override
        public String getName() {
            return ElapsedTimeIdlingResource.class.getName() + ":" + waitingTime;
        }

        @Override
        public boolean isIdleNow() {
            long elapsed = System.currentTimeMillis() - startTime;
            boolean idle = (elapsed >= waitingTime);
            if (idle && resourceCallback != null) {
                resourceCallback.onTransitionToIdle();
            }
            return idle;
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
            this.resourceCallback = resourceCallback;
        }
    }
}



