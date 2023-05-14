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
        public void testSearchBar() {

            Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
            // Find the menu item by its ID and perform a click
            onView(withText("Search")).perform(click());

        }

    }
