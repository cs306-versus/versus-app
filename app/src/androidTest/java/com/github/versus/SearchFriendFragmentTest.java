package com.github.versus;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
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

public class SearchFriendFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);
    
    @Before
    public void navigateToFrag(){
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        onView(withId(R.id.friend_search)).perform(click());
    }
    
    @Test
    public void testScrollRecyclerView(){
        onView(withId(R.id.user_recyclerView)).perform(click());
    }

    @Test
    public void testPressProfile(){
        onView(withId(R.id.search_users)).perform(typeText("arnie"), closeSoftKeyboard());
        onView(withId(R.id.view_profile)).perform(click());
    }

    @Test
    public void testSearchBar(){
        onView(withId(R.id.search_users)).perform(typeText("John"), closeSoftKeyboard());
        onView((withText("John"))).check(matches(isDisplayed()));
    }


}
