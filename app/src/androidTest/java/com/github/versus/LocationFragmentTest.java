package com.github.versus;



import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.endsWith;


import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.Root;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

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
    public void testLocationElements() throws InterruptedException {

      /* Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
       // Find the menu item by its ID and perform a click
       onView(withText("Get Place")).perform(click());
       onView(withId(R.id.edit_text_radius2)).perform(typeText("1500"));
       onView(withText("OK")).perform(click());
       /*String placeName = "Bassenges Football";
       // Wait for the ListView to be displayed
       onView(withText(placeName)).perform(click());*/
        String placeName = "Bassenges Football";
        clickOnLocation(placeName,"1500");


    }

    @Test
    public void testIfNoLocationWithinRadius() throws InterruptedException {
        // Find the overflow menu button and find the "Choose a radius" menu item and perform a type text action
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Get Place")).perform(click());

        onView(withId(R.id.edit_text_radius2)).perform(typeText("10"));




    }
    @Test
    public void test3s() throws InterruptedException {
        // Find the overflow menu button and find the "Choose a radius" menu item and perform a type text action
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Get Place")).perform(click());
        onView(withId(R.id.edit_text_radius2)).perform(typeText("10"));
        //onView(withText("Show Places")).perform(click());
    }





    @Test
    public void testNoRadiusInput() throws InterruptedException {
        // Find the overflow menu button and find the "Choose a radius" menu item and perform a type text action
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Get Place")).perform(click());
        Thread.sleep(2000);
        onView(withText(("Show Places"))).perform(click());
    }

    @Test
  public void testUiElements() throws InterruptedException {
      // Find the overflow menu button and find the "Choose a radius" menu item and perform a type text action
      Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
      // Find the menu item by its ID and perform a click
      onView(withText("Choose a radius")).perform(click());
      onView(withId(R.id.edit_text_radius2)).perform(typeText("1000"));
      onView(withText("OK")).perform(click());
      //Testing circle properties


  }





    private void clickOnLocation(String  location,String radius) throws InterruptedException {
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Get Place")).perform(click());
        onView(withId(R.id.edit_text_radius2)).perform(typeText(radius),closeSoftKeyboard());
        onView(withText("Show Places")).perform(click());
        String placeName = location;
        // Wait for the ListView to be displayed
        onView(withText(placeName)).perform(click());

    }


}






