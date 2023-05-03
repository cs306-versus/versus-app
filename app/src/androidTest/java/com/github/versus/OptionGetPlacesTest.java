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
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test class for the OptionGetPlaces feature.
 * Tests the behavior of the Get Places operation, including edge cases like no radius input or no places within the specified radius.
 */
@RunWith(AndroidJUnit4.class)
public class OptionGetPlacesTest {
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
        Intents.init();
        // Open the drawer_layout
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        onView(withId(R.id.nav_location)).perform(click());
    }

    /**
     * Cleans up the testing environment after each test.
     * Releases the Intents framework.
     */
    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testDrawingPathWithPlaceSelected() throws InterruptedException {
        long waitingTime = 10000;
        ElapsedTimeIdlingResource idlingResourceFirst = new ElapsedTimeIdlingResource(waitingTime);
        IdlingRegistry.getInstance().register(idlingResourceFirst);
        String placeName = "GooglePlex Football";
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Get Place")).perform(click());

        onView(withId(R.id.edit_text_radius2)).perform(typeText("1500"));
        closeSoftKeyboard();
        onView(withText("Show Places")).inRoot(isDialog()).perform(click());
        long waitingTime2 = 10000;
        ElapsedTimeIdlingResource idlingResource2 = new ElapsedTimeIdlingResource(waitingTime2);
        IdlingRegistry.getInstance().register(idlingResource2);
        onView(withText(placeName)).perform(click());
        IdlingRegistry.getInstance().unregister(idlingResource2);

        ElapsedTimeIdlingResource idlingResource3 = new ElapsedTimeIdlingResource(waitingTime2);
        IdlingRegistry.getInstance().register(idlingResource3);
        onView(withText("Draw Path")).perform(click());
        IdlingRegistry.getInstance().unregister(idlingResource3);


        IdlingRegistry.getInstance().unregister(idlingResourceFirst);
    }


    /**
     * Test for canceling the get places operation.
     * Ensures that canceling the operation results in the expected behavior.
     *
     * @throws InterruptedException if the test is interrupted
     */
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
        onView(withText("Show Places")).inRoot(isDialog()).perform(click());

        long waitingTime2 = 10000;
        ElapsedTimeIdlingResource idlingResource2 = new ElapsedTimeIdlingResource(waitingTime2);
        IdlingRegistry.getInstance().register(idlingResource2);
        onView(withText("Cancel")).perform(click());
        IdlingRegistry.getInstance().unregister(idlingResource2);
        IdlingRegistry.getInstance().unregister(idlingResourceFirst);
    }

    /**
     * Test for clicking on a specific location.
     * Ensures that clicking on a location results in the expected behavior.
     *
     * @throws InterruptedException if the test is interrupted
     */
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
        onView(withText("Show Places")).inRoot(isDialog()).perform(click());
        long waitingTime2 = 10000;
        ElapsedTimeIdlingResource idlingResource2 = new ElapsedTimeIdlingResource(waitingTime2);
        IdlingRegistry.getInstance().register(idlingResource2);
        onView(withText(placeName)).perform(click());
        IdlingRegistry.getInstance().unregister(idlingResource2);
        IdlingRegistry.getInstance().unregister(idlingResourceFirst);
    }


    /**
     * Test for the case when no radius is input.
     * Ensures that the application handles the situation when no radius is provided.
     *
     * @throws InterruptedException if the test is interrupted
     */


    /**
     * Test for the case when there are no places within the specified radius.
     * Ensures that the application handles the situation when no places are found within the specified radius.
     *
     * @throws InterruptedException if the test is interrupted
     */
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
        onView(withText("Show Places")).inRoot(isDialog()).perform(click());
        long waitingTime2 = 10000;
        ElapsedTimeIdlingResource idlingResource2 = new ElapsedTimeIdlingResource(waitingTime2);
        IdlingRegistry.getInstance().register(idlingResource2);

        IdlingRegistry.getInstance().unregister(idlingResource2);
        IdlingRegistry.getInstance().unregister(idlingResourceFirst);
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
            return ElapsedTimeIdlingResource.class.getName() + ":" + waitingTime;
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


