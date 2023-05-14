package com.github.versus;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.containsString;

import androidx.core.view.GravityCompat;
import androidx.core.widget.TextViewCompat;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.regex.Matcher;

public class SeachFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void navigateToFrag(){
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        onView(withId(R.id.nav_search)).perform(click());
    }
    
    @Test
    public void testScrollRecyclerView(){
        onView(withId(R.id.recyclerView)).perform(click());
    }

    @Test
    public void testMakePost(){
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

    @Test
    public void testCancelPost(){
        /*
        onView(withId(R.id.add_posts)).perform(click());
        onView(withId(R.id.editPostTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.editPostTitle)).perform(typeText("TEST POST"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("Archery")).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.editMaxPlayers)).perform(typeText("4"), closeSoftKeyboard());
        
        onView(withId(android.R.id.button2)).perform(click());*/

    }


    //@Test
    //public void testSearchBar(){
    //    onView(withId(R.id.search_posts)).perform(typeText("Football"), closeSoftKeyboard());
    //    onView((withText("Football"))).check(matches(isDisplayed()));
    //}


}
