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

        @Test
        public void testSearchBar() throws InterruptedException {

            Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
            // Find the menu item by its ID and perform a click
            onView(withText("Search")).perform(click());
           /* DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) ApplicationProvider.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);

            // Calculate the center coordinates
            int centerX = displayMetrics.widthPixels / 2;
            int centerY = 300;
            //System.out.println(displayMetrics.widthPixels+"  HEHEHEHEH  " +displayMetrics.heightPixels);

            // Simulate a map click event at the center of the screen
            Thread.sleep(2000);
            simulateMapClick(centerX, centerY);
            perform(typeText("1500"), closeSoftKeyboard());*/

            UiDevice device = UiDevice.getInstance(getInstrumentation());
            UiObject searchBox = device.findObject(new UiSelector().text("Search"));

            try {
                // Type the text and press enter
                searchBox.click();
                searchBox.setText("Unil sport");
                Thread.sleep(2000);
                device.pressEnter();
                device.pressEnter();
            } catch (UiObjectNotFoundException e) {
                fail("Could not find the Autocomplete widget");
            }
            Thread.sleep(2000);
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
