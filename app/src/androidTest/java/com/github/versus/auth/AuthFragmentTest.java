package com.github.versus.auth;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.R;
import com.github.versus.utils.FirebaseEmulator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AuthFragmentTest {

    @Rule
    public ActivityScenarioRule<AuthActivity> scenario = new ActivityScenarioRule<>(AuthActivity.class);

    @Before
    public void setUp() {
        // HR : Make sure that the user is signed out
        FirebaseEmulator.FIREBASE_AUTH.signOut();
    }

    @Test
    public void testVisibility(){
        onView(withId(R.id.auth_frag)).check(matches(isDisplayed()));
    }

    @Test
    public void testRegister(){
        onView(withId(R.id.register_btn)).perform(click());
        onView(withId(R.id.register_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void testSignIn(){
        onView(withId(R.id.signin_btn)).perform(click());
        onView(withId(R.id.frag_auth_layout)).check(matches(isDisplayed()));
    }

}
