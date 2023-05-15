package com.github.versus;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.fail;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.core.view.GravityCompat;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



@RunWith(AndroidJUnit4.class)
public class SearchBarTest {


    // Declare activity rule and permission rule
        @Rule
        public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

        @Rule
        public GrantPermissionRule grantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

        /**
         * Sets up the testing environment before each test.
         * Initializes the Intents framework and opens the drawer layout.
         */
        @Before
        public void setUp() {
            // Open the drawer_layout
            onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());
            onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
            onView(withId(R.id.nav_location)).perform(click());
        }


    /**
     * This test is to validate the functionality of the search bar in the application.
     *
     * @throws InterruptedException if the thread sleep is interrupted
     */
    @Test
    public void testSearchBar() throws InterruptedException {
        // Open the options menu in the action bar
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        // Find the menu item "Search" by its text and perform a click. This should open the search bar.
        onView(withText("Search")).perform(click());
/*
        // Get the instance of the device on which the test is running
        UiDevice device = UiDevice.getInstance(getInstrumentation());

        // Find the search bar (UiObject) by its hint text "Search"
        UiObject searchBox = device.findObject(new UiSelector().text("Search"));

        try {
            // Click on the search bar to focus it, then type "Unil sport" into the search bar
            searchBox.click();
            searchBox.setText("Unil sport");

            // Sleep for 2 seconds to give time for potential network operations (like autocomplete suggestions)
            Thread.sleep(2000);

            // Simulate pressing the enter key twice. This might be necessary if the first press only closes the keyboard.
            device.pressEnter();
            device.pressEnter();
        } catch (UiObjectNotFoundException e) {
            // If we couldn't find the search bar, fail the test
            fail("Could not find the Autocomplete widget");
        }
        // Sleep for another 2 seconds to give time for the search to complete
        Thread.sleep(2000);

        // Find the "Draw Path" button by its text and perform a click. This should start the path drawing operation.
        onView(withText("Draw Path")).perform(click());*/
    }



}
