package com.github.versus;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
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

public class SearchFragTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);
    @Before
    public void setUp() {
        Intents.init();
    }
    @After
    public void tearDown() {
        Intents.release();
    }
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
//        onView(withId(R.id.editPostTitle)).check(matches(withText(containsString("Title"))));
//        onView(withId(android.R.id.button1)).perform(click());
//        onView(withText(containsString("Choose a sport"))).check(matches(isDisplayed()));
//        onView(withText(containsString("Next"))).perform(click());
//        onView(withText(containsString("Cancel"))).perform(click());

    }
}
