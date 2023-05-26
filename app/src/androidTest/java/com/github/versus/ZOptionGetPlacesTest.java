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
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import com.github.versus.utils.FirebaseEmulator;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test class for the OptionGetPlaces feature.
 * Tests the behavior of the Get Places operation, including edge cases like no radius input or no places within the specified radius.
 */
@RunWith(AndroidJUnit4.class)
public final class ZOptionGetPlacesTest {

    static {
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
    }

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
     * Test for canceling the get places operation.
     * Ensures that canceling the operation results in the expected behavior.
     *
     * @throws InterruptedException if the test is interrupted
     */
    @Test
    public void testCancel() {
        // Find the menu item by its ID and perform a click
        onView(withId(R.id.near_me_button)).perform(click());
        onView(withText("Cancel")).perform(click());
    }
    /**
     * Test for clicking on a specific location.
     * Ensures that clicking on a location results in the expected behavior.
     *
     * @throws InterruptedException if the test is interrupted
     */
    @Test
    public void testClickOnLocation() {
        // Find the menu item by its ID and perform a click
        onView(withId(R.id.near_me_button)).perform(click());
        onView(withText("GooglePlex Football")).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.near_me_button)).perform(click());
        onView(withText("GooglePlex Football")).perform(click());
        onView(withText("OK")).perform(click());



    }

    /**
     * Test for clicking on a specific location.
     * Ensures that clicking on a location results in the expected behavior.
     *
     * @throws InterruptedException if the test is interrupted
     */
    @Test
    public void selectClickOnLocationThenByClick() {
        // Find the menu item by its ID and perform a click
        onView(withId(R.id.near_me_button)).perform(click());
        onView(withText("GooglePlex Football")).perform(click());
        onView(withText("OK")).perform(click());
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        onView(withText("By Click")).perform(click());
    }
    @Test
    public void selectClickOnLocationThenBySearch() {
        // Find the menu item by its ID and perform a click
        onView(withId(R.id.near_me_button)).perform(click());
        onView(withText("GooglePlex Football")).perform(click());
        onView(withText("OK")).perform(click());
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        onView(withText("By Search")).perform(click());
    }

    @Test
    public void selectClickOnLocationThenByChangeDistance() {
        // Find the menu item by its ID and perform a click
        onView(withId(R.id.near_me_button)).perform(click());
        onView(withText("GooglePlex Football")).perform(click());
        onView(withText("OK")).perform(click());
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        onView(withText("Change distance")).perform(click());
    }





    /**
     * Test for the case when there are no places within the specified radius.
     * Ensures that the application handles the situation when no places are found within the specified radius.
     *
     * @throws InterruptedException if the test is interrupted
     */
    @Test
    public void testIfNoPlacesWithinRadius() {
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Change distance")).perform(click());
        onView(withId(R.id.edit_text_radius3)).perform(typeText("100"), closeSoftKeyboard());
        onView(withText("ENTER")).perform(click());
        onView(withId(R.id.near_me_button)).perform(click());
    }

}