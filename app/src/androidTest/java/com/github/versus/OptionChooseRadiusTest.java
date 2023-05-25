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
public final class OptionChooseRadiusTest {

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

    @Test
    public void testChoosingRadius() {
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Choose radius")).perform(click());
        onView(withId(R.id.edit_text_radius3)).perform(typeText("1500"), closeSoftKeyboard());
        onView(withText("Enter")).inRoot(isDialog()).perform(click());
    }
    
    @Test
    public void testCancelingRadius() {
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Choose radius")).perform(click());
        onView(withId(R.id.edit_text_radius3)).perform(typeText("1500"), closeSoftKeyboard());
        onView(withText("Cancel")).inRoot(isDialog()).perform(click());
    }

}
