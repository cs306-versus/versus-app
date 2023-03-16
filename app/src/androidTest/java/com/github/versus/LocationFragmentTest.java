package com.github.versus;



import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
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
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;


import android.graphics.Color;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;


import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;



import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LocationFragmentTest {

    @Rule
    public ActivityScenarioRule<EntryActivity> activityRule = new ActivityScenarioRule<>(EntryActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testUiElements() throws InterruptedException {

        onView(withId(R.id.mail_login)).perform(clearText()).perform(typeText("haha@gmail.com")).check(matches(withText("haha@gmail.com"))).perform(closeSoftKeyboard());
        onView(withId(R.id.pwd_login)).perform(clearText()).perform(typeText("123456789")).check(matches(withText("123456789"))).perform(closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        ;
        // Open the drawer by clicking the hamburger button
        //Espresso.onView(withContentDescription("Open navigation drawer")).perform(click());

        // Click on the "Location" item inside the drawer
        //Espresso.onView(withId(R.id.nav_location)).perform(click());
        Thread.sleep(2000);


        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());

        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        onView(withId(R.id.nav_location)).perform(click());
        //.check(matches(isDisplayed())); This doesnt work TODO


        // Find the overflow menu button and find the "Choose a radius" menu item and perform a type text action

        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());


        // Find the menu item by its ID and perform a click
        onView(withText("Choose a radius")).perform(click());
        onView(withId(R.id.edit_text_radius)).perform(typeText("1000"));

        Espresso.onView(withText("OK")).perform(click());

        // Wait for the map to load and show the circle
     /*HasCircle c=   new HasCircle(LocationFragment.getLocalPos());
        Espresso.onView(withId(R.id.map)).perform(c);

        // Check the properties of the circle
        CircleOptions circleOptions = LocationFragment.circleOptions;

        assertThat(circleOptions.getCenter(),is(equalTo(c.center)));
        assertThat(circleOptions.getStrokeWidth(),is(equalTo(c.circle.getStrokeWidth())));
        assertThat(circleOptions.getStrokeColor(),is(equalTo(c.circle.getStrokeColor())));
        assertThat(circleOptions.getFillColor(),is(equalTo(Color.parseColor("#500084d3"))));*/

    }


    private static class HasCircle implements ViewAction {
        private final LatLng center;
        CircleOptions circle ;
        public HasCircle(LatLng center) {
            this.center = center;
        }

        @Override
        public Matcher<View> getConstraints() {
            return allOf(withId(R.id.map), isDisplayed());
        }

        @Override
        public String getDescription() {
            return "waiting for circle to be shown on map";
        }

        @Override
        public void perform(UiController uiController, View view) {
            //GoogleMap map = ((MapFragment) activityTestRule.getActivity().getFragmentManager().findFragmentById(R.id.map)).getMap();
            //GoogleMap map = ((SupportMapFragment) activityRule.getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            circle = new CircleOptions().center(LocationFragment.getLocalPos()).strokeWidth(2).strokeColor(Color.BLUE).fillColor(Color.parseColor("#500084d3"));
            if (circle == null || !circle.getCenter().equals(center)) {
                uiController.loopMainThreadForAtLeast(50);
            }
        }
    }
}


        // Start the screen of your activity.
        /*Espresso.onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.your_navigation_menu_item));

        // Check that you Activity was opened.
        String expectedNoStatisticsText = InstrumentationRegistry.getTargetContext()
                .getString(R.string.no_item_available);
        onView(withId(R.id.no_statistics)).check(matches(withText(expectedNoStatisticsText)));

        // Check that a new activity is launched
        //intended(hasComponent(MainActivity.class.getName()))
        /*Espresso.onView(withId(R.id.nav_location))
                .check(matches(isDisplayed()));*/
        //Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
       /* Espresso.onView(withId(R.id.nav_location))
                .perform(click())
                .check(matches(isDisplayed()));*/


        /*LocationFragment fragment = new LocationFragment();
        FragmentScenario<LocationFragment> scenario = FragmentScenario.launchInContainer(LocationFragment.class);
        // Access views inside the fragment
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());

        // Click on the "Get Place" menu item
        Espresso.onView(withId(R.id.option_radius))
                .perform(click());
*/
        // Check that the intent has the correct extras and data
       // intended(allOf(hasExtra("", "adam"), toPackage("com.github.adam.bootcamp")));
