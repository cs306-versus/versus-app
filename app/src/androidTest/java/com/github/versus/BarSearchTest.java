
package com.github.versus;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.fail;

import androidx.core.view.GravityCompat;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
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
public class BarSearchTest {

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
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        onView(withId(R.id.nav_location)).perform(click());
    }


    /**
     * This test is to validate the functionality of the search bar in the application.
     *
     * @throws InterruptedException if the thread sleep is interrupted
     */
   
    @Test
    public void testSearchBarByClicking() throws InterruptedException {
        // Open the options menu in the action bar
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        // Find the menu item "Search" by its text and perform a click. This should open the search bar.
        onView(withText("Search")).perform(click());

        long waitingTime = 6000;
        // Get the instance of the device on which the test is running
        ElapsedTimeIdlingResource idlingResourceFirst = new ElapsedTimeIdlingResource(waitingTime);

        onView(withId(R.id.autocomplete_location_search)).perform(click());


        IdlingRegistry.getInstance().register(idlingResourceFirst);

        IdlingRegistry.getInstance().unregister(idlingResourceFirst);

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


    /**
     * ElapsedTimeIdlingResource is an implementation of the IdlingResource interface.
     * It is used to wait for a specified amount of time before allowing the test to continue.
     * This can be useful when testing UI components that require a certain time to load or update.
     */
    public class ElapsedTimeIdlingResource implements IdlingResource {
        private final long startTime;
        private final long waitingTime;
        private ResourceCallback resourceCallback;

        /**
         * Constructs an ElapsedTimeIdlingResource with a specified waiting time.
         *
         * @param waitingTime The amount of time to wait, in milliseconds, before allowing the test to continue.
         */
        public ElapsedTimeIdlingResource(long waitingTime) {
            this.startTime = System.currentTimeMillis();
            this.waitingTime = waitingTime;
        }

        /**
         * Returns the name of the ElapsedTimeIdlingResource.
         *
         * @return A String containing the name of the ElapsedTimeIdlingResource.
         */
        @Override
        public String getName() {
            return OptionChooseLocationTest.ElapsedTimeIdlingResource.class.getName() + ":" + waitingTime;
        }

        /**
         * Checks whether the specified waiting time has elapsed.
         *
         * @return true if the waiting time has elapsed, false otherwise.
         */
        @Override
        public boolean isIdleNow() {
            long elapsed = System.currentTimeMillis() - startTime;
            boolean idle = (elapsed >= waitingTime);
            if (idle && resourceCallback != null) {
                resourceCallback.onTransitionToIdle();
            }
            return idle;
        }
        /**
         * Registers a ResourceCallback to be notified when the resource transitions to the idle state.
         *
         * @param resourceCallback The ResourceCallback to be registered.
         */
        @Override
        public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
            this.resourceCallback = resourceCallback;
        }
    }

}



