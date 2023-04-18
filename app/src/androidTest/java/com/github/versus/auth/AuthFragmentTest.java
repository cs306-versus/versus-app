package com.github.versus.auth;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AuthFragmentTest {

    @Rule
    public ActivityScenarioRule<AuthActivity> scenario = new ActivityScenarioRule<>(AuthActivity.class);

    @Test
    public void testSignInRequest(){
        onView(withId(R.id.auth_signin)).perform(click());
        onView(withId(R.id.frag_signin_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void testMailLoginRequest(){
        onView(withId(R.id.auth_login_mail)).perform(click());
        onView(withId(R.id.auth_login_mail_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void testGoogleLoginRequest(){
        onView(withId(R.id.auth_login_google)).perform(click());
        onView(withId(R.id.frag_login_google_layout)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

}