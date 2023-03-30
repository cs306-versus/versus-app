package com.github.versus;



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
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
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
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
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


        String placeName = "Bassenges Football";
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Get Place")).perform(click());
        //onView(withId(R.id.edit_text_radius2)).perform(typeText("900"));
       // onView(withText("Show Places")).perform(closeSoftKeyboard(),click());

       //onView(withText("Bassenges Football")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
       /* DataInteraction dataInteraction = onData(anything())
                .inAdapterView(allOf(withId(R.id.test_list_view), isDisplayed()))
                .atPosition(0);

        dataInteraction
                .inRoot(isDialog())
                .onChildView(isAssignableFrom(TextView.class))
                .check(matches(withText("Bassenges Football")))
                .perform(click());*/
        //onView(withText("Bassenges Football")).perform(click());
        /*onData(anything())
                .inAdapterView(allOf(withId(R.id.test_list_view), isDisplayed()))
                .atPosition(0)
                .inRoot(isAlertDialog()) // Use the custom Matcher to find the AlertDialog
                .check(matches(withText("Bassenges Football")))
                .perform(click());
*/
        /*onData(anything())
                .inAdapterView(allOf(withId(R.id.test_list_view2), isDisplayed()))
                .atPosition(0)
                .inRoot(isPlatformPopup()) // Use the isPlatformPopup() matcher to find the AlertDialog
                .check(matches(withText("Bassenges Football")))
                .perform(click());*/

       /* onData(anything())
                .inAdapterView(withId(R.id.test_list_view))
                .atPosition(0)
                .perform(ViewActions.scrollTo())
                .check(matches(withText("Bassenges Football")));*/


        // Wait for the AlertDialog to appear


        // onView(withText("Bassenges Football")).perform(click());


       // onView(withText("Select a place")).check(matches(isDisplayed()));


        // Wait for the ListView to be displayed
        // Implement your own IdlingResource or use a pre-built one from Espresso, such as CountingIdlingResource

        //ElapsedTimeIdlingResource idlingResource = new ElapsedTimeIdlingResource(5000);
       // IdlingRegistry.getInstance().register(idlingResource);
        onView(withText("Cancel")).perform(click());
        //onView(withId(R.id.test_list_view2)).perform(click());

        //IdlingRegistry.getInstance().unregister(idlingResource);





    }
   /* public static Matcher<Root> isAlertDialog() {
        return new TypeSafeMatcher<Root>() {
            @Override
            protected boolean matchesSafely(Root root) {
                int type = root.getWindowLayoutParams().get().type;
                return type == WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is alert dialog");
            }
        };
    }



   /* @Test
    public void testIfNoLocationWithinRadius() throws InterruptedException {
        // Find the overflow menu button and find the "Choose a radius" menu item and perform a type text action
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Get Place")).perform(click());

        onView(withId(R.id.edit_text_radius2)).perform(typeText("10"));




    }*/
    /*@Test
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
        onView(withText(("Show Places"))).perform(click());
    }

    @Test
    public void testNoCloseLocations() throws InterruptedException {
        // Find the overflow menu button and find the "Choose a radius" menu item and perform a type text action
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Get Place")).perform(click());

        onView(withId(R.id.edit_text_radius2)).perform(typeText("10"));
        onView(withText(("Show Places"))).perform(click());
        Thread.sleep(3000);
    }

   /* @Test
  public void testUiElements() throws InterruptedException {
      // Find the overflow menu button and find the "Choose a radius" menu item and perform a type text action
      Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
      // Find the menu item by its ID and perform a click
      onView(withText("Choose a radius")).perform(click());
      onView(withId(R.id.edit_text_radius2)).perform(typeText("1000"));
      onView(withText("Show Places")).perform(click());
      //Testing circle properties


  }*/



    public class ElapsedTimeIdlingResource implements IdlingResource {
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



    private void clickOnLocation(String  location,String radius) throws InterruptedException {
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        // Find the menu item by its ID and perform a click
        onView(withText("Get Place")).perform(click());
        onView(withId(R.id.edit_text_radius2)).perform(typeText(radius));
        onView(withText("Show Places")).perform(click());
        String placeName = location;
        // Wait for the ListView to be displayed
        onView(withText(placeName)).perform(click());


    }


}






