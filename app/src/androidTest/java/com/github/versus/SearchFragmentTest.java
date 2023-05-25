package com.github.versus;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.core.view.GravityCompat;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.github.versus.utils.FirebaseEmulator;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public final class SearchFragmentTest {

    static {
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
    }

    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void navigateToFrag() {
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        onView(withId(R.id.nav_search)).perform(click());
    }

    //@Test
    public void testScrollRecyclerView() {
        onView(withId(R.id.recyclerView)).perform(click());
    }

    //@Test
    public void testMakePost() {
        onView(withId(R.id.add_posts)).perform(click());
        onView(withId(R.id.editPostTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.editPostTitle)).perform(typeText("TEST POST"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("Archery")).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.editMaxPlayers)).perform(typeText("4"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
    }


    //@Test
    public void testCancelPost() {
        onView(withId(R.id.add_posts)).perform(click());
        onView(withId(R.id.editPostTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.editPostTitle)).perform(typeText("TEST POST"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("Archery")).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.editMaxPlayers)).perform(typeText("4"), closeSoftKeyboard());
        onView(withId(android.R.id.button2)).perform(click());
    }


    //    @Test
//    public void testCreatePostWithLocation() throws InterruptedException {
//        onView(withId(R.id.add_posts)).perform(click());
//        onView(withId(R.id.editPostTitle)).check(matches(isDisplayed()));
//        onView(withId(R.id.editPostTitle)).perform(typeText("TEST POST"), closeSoftKeyboard());
//        onView(withId(android.R.id.button1)).perform(click());
//        onView(withText("Archery")).perform(click());
//        onView(withId(android.R.id.button1)).perform(click());
//        onView(withId(R.id.editMaxPlayers)).perform(typeText("4"), closeSoftKeyboard());
//        onView(withId(android.R.id.button1)).perform(click());
//        onView(withId(android.R.id.button1)).perform(click());
//        onView(withText("Choose")).perform(click());
//        UiDevice device = UiDevice.getInstance(getInstrumentation());
//        UiObject searchBox = device.findObject(new UiSelector().text("Search"));
//
//        try {
//            // Type the text and press enter
//            searchBox.setText("unilego.");
//            Thread.sleep(2000);
//            device.pressEnter();
//            device.pressEnter();
//        } catch (UiObjectNotFoundException e) {
//            fail("Could not find the Autocomplete widget");
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        Thread.sleep(2000);
//        onView(withId(R.id.add_posts)).perform(click());
//
//    }
    //@Test
    public void testCreatePostWithLocationCanceled() {
        onView(withId(R.id.add_posts)).perform(click());
        onView(withId(R.id.editPostTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.editPostTitle)).perform(typeText("TEST POST"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("Archery")).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.editMaxPlayers)).perform(typeText("4"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("Cancel")).perform(click());

    }

    //@Test
    public void findEditSearch() {
        boolean success = false;
        for (int i = 0; i < 50; i++) {
            onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition(i));
            try {
                onView(withText("Edit Post")).perform(click());
                success = true;
                break;
            } catch (Exception e) {

            }
        }
        if (success) {
            onView(withId(R.id.game_players)).perform(click());
            onView(withId(R.id.game_players)).perform(click());
            onView(withId(R.id.edit_sport)).perform(click());
        }
    }

}