package com.github.versus;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.fail;


import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.core.view.GravityCompat;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.uiautomator.UiDevice;


import com.github.versus.MainActivity;
import com.github.versus.R;
import com.google.android.gms.maps.model.LatLng;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RunWith(AndroidJUnit4.class)
public class OptionChooseLocationTest {


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
    public void testClick() throws InterruptedException {
        long waitingTime = 10000;
        ElapsedTimeIdlingResource idlingResourceFirst = new ElapsedTimeIdlingResource(waitingTime);
        // IdlingRegistry.getInstance().register(idlingResourceFirst);
        String placeName = "GooglePlex Football";
        Espresso.openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());



        // Get screen dimensions




        onView(withText("Choose location")).perform(click());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) ApplicationProvider.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);

        // Calculate the center coordinates
        int centerX = displayMetrics.widthPixels / 2;
        int centerY = displayMetrics.heightPixels / 2;


        // Simulate a map click event at the center of the screen
        simulateMapClick(centerX, centerY);

        long waitingTime2 = 10000;
        ElapsedTimeIdlingResource idlingResource2 = new ElapsedTimeIdlingResource(waitingTime2);
        IdlingRegistry.getInstance().register(idlingResource2);
        onView(withText(placeName)).perform(click());
        IdlingRegistry.getInstance().unregister(idlingResource2);
        IdlingRegistry.getInstance().unregister(idlingResourceFirst);
    }
    private void simulateMapClick(int x, int y) {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.click(x, y);
    }




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
            return com.github.versus.OptionGetPlacesTest.ElapsedTimeIdlingResource.class.getName() + ":" + waitingTime;
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




