package com.github.versus;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.core.view.GravityCompat;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test class for the OptionChooseLocation feature.
 * Tests the behavior of clicking on the map to choose a location.
 */
//@RunWith(AndroidJUnit4.class)
public class OptionChooseLocationTest {

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
     * Test for simulating a click on the map.
     * Ensures that clicking on the map results in the expected behavior.
     *
     * @throws InterruptedException if the test is interrupted
     */


    //@Test
    public void testClick() throws InterruptedException {
        String placeName = "GooglePlex Football";
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        // Open the Choose location option
        onView(withText("Choose location")).perform(click());
        
        // Get screen dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) ApplicationProvider.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);

        // Calculate the center coordinates
        int centerX = displayMetrics.widthPixels / 2;
        int centerY = displayMetrics.heightPixels / 2;

        // Simulate a map click event at the center of the screen
        simulateMapClick(centerX, centerY);
        Thread.sleep(2000);
        onView(withText(placeName)).perform(click());
    }

    //@Test
    public void testDrawingPathWithPlaceSelected() throws InterruptedException {
        String placeName = "GooglePlex Football";
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        
        // Open the Choose location option
        onView(withText("Choose location")).perform(click());
        
        // Get screen dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) ApplicationProvider.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        
        // Calculate the center coordinates
        int centerX = displayMetrics.widthPixels / 2;
        int centerY = displayMetrics.heightPixels / 2;
        
        // Simulate a map click event at the center of the screen
        simulateMapClick(centerX, centerY);
        Thread.sleep(2000);
        onView(withText(placeName)).perform(click());
        onView(withText("Draw Path")).perform(click());
    }

    //@Test
    public void testDrawingPathWithoutPlaceSelected() throws InterruptedException {
        String placeName = "Bassenges Football";
        onView(withText("Draw Path")).perform(click());
    }


    /**
     * Simulates a map click at the given screen coordinates.
     *
     * @param x int representing the x-coordinate of the screen location to click
     * @param y int representing the y-coordinate of the screen location to click
     */
    private void simulateMapClick(int x, int y) {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.click(x, y);
    }
    
}
