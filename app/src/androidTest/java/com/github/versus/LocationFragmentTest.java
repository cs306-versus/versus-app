












package com.github.versus;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.fail;


import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.Root;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.idling.CountingIdlingResource;
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
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

@RunWith(AndroidJUnit4.class)
public class LocationFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);
    @Rule
    public GrantPermissionRule grantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

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
    public void testSuccess() throws InterruptedException {


       /* String placeName = "GooglePlex Football";
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Get Place")).perform(click());

        onView(withId(R.id.edit_text_radius2)).perform(typeText("800"));

        closeSoftKeyboard();
        onView(withText("Show Places")).perform(click());
*/
        String placeName = "GooglePlex Football";
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Get Place")).perform(click());

        onView(withId(R.id.edit_text_radius2)).perform(typeText("800"));

        closeSoftKeyboard();
        onView(withText("Show Places")).perform(click());

        //long waitingTime = 5000; // Wait for 5 seconds
        //ElapsedTimeIdlingResource idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        //IdlingRegistry.getInstance().register(idlingResource);

        onView(withText("Cancel")).perform(click());

        //IdlingRegistry.getInstance().unregister(idlingResource);

    }

}
   /* @Test
    public void testSuccess() throws InterruptedException {


        String placeName = "Bassenges Football";
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Get Place")).perform(click());

        onView(withId(R.id.edit_text_radius2)).perform(typeText("800"));

        closeSoftKeyboard();

        onView(withText("Show Places")).perform(click());


       // onView(withText("Cancel")).perform(click());

        //IdlingRegistry.getInstance().unregister(idlingResource);


    }
*/
   /* @Test
    public void testSuccess2() throws InterruptedException {

        //The emulator has its default location on GooglePlex
        String placeName = "GooglePlex Football";
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Get Place")).perform(click());

        onView(withId(R.id.edit_text_radius2)).perform(typeText("1500"));

        closeSoftKeyboard();

        onView(withText("Show Places")).perform(click());


        //onData(anything()).inAdapterView(withText(placeName)).atPosition(0).perform(click());
      //  long waitingTime = 5000; // Wait for 5 seconds
        //ElapsedTimeIdlingResource idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        //IdlingRegistry.getInstance().register(idlingResource);

        onView(withText("Select a place")).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.test_list_view2)).atPosition(0).perform(click());

       // IdlingRegistry.getInstance().unregister(idlingResource);
    }*/

   /* public class ElapsedTimeIdlingResource implements IdlingResource {
        private final long startTime;
        private final long waitingTime;
        private ResourceCallback resourceCallback;

        public ElapsedTimeIdlingResource(long waitingTime) {
            this.startTime = System.currentTimeMillis();
            this.waitingTime = waitingTime;
        }

        @Override
        public String getName() {
            return ElapsedTimeIdlingResource.class.getName() + ":" + waitingTime;
        }

        @Override
        public boolean isIdleNow() {
            long elapsed = System.currentTimeMillis() - startTime;
            boolean idle = (elapsed >= waitingTime);
            if (idle && resourceCallback != null) {
                resourceCallback.onTransitionToIdle();
            }
            return idle;
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
            this.resourceCallback = resourceCallback;
        }
    }
}


    //IdlingRegistry.getInstance().unregister(idlingResource);





    /* @Test
     public void testLocationElements() throws InterruptedException {
         String placeName = "Bassenges Football";
         Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
         // Find the menu item by its ID and perform a click
         onView(withText("Get Place")).perform(click());
         onView(withId(R.id.edit_text_radius2)).perform(typeText("800"));
         onView(withText("Show Places")).perform(click());
        // onView(withText("Cancel")).perform(click());
         long waitingTime = 7000; // Wait for 5 seconds
         ElapsedTimeIdlingResource idlingResource = new ElapsedTimeIdlingResource(waitingTime);
         IdlingRegistry.getInstance().register(idlingResource);
         onView(withText("Bassenges Football")).perform(click());
         IdlingRegistry.getInstance().unregister(idlingResource);
         //IdlingRegistry.getInstance().unregister(idlingResource);
     }*/


