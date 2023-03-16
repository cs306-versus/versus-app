package com.github.versus;



import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;


import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LocationFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);


    // Verify that the activity properly restored the location and camera position from the instance state


    @Before
    public void setUp() {
        Intents.init();
        //Open the drawer_layout
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        onView(withId(R.id.nav_location)).perform(click());

    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testUiElements() throws InterruptedException {
        // Find the overflow menu button and find the "Choose a radius" menu item and perform a type text action
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Choose a radius")).perform(click());
        onView(withId(R.id.edit_text_radius)).perform(typeText("1000"));
        onView(withText("OK")).perform(click());
        //Testing circle properties


    }
}






