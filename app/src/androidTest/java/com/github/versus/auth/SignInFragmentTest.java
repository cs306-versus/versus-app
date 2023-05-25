package com.github.versus.auth;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static java.util.Objects.isNull;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.versus.utils.*;

import static com.github.versus.utils.auth.EmulatorUserProvider.*;
import static org.hamcrest.Matchers.not;

//@RunWith(AndroidJUnit4.class)
public class SignInFragmentTest {

    private final Authenticator authenticator = VersusAuthenticator.getInstance(FirebaseEmulator.FIREBASE_AUTH);

    //@Rule
    public ActivityScenarioRule<AuthActivity> scenario = new ActivityScenarioRule<>(AuthActivity.class);

    //@Before
    public void setUp() {
        authenticator.signOut();
        onView(withId(R.id.signin_btn)).perform(click());
    }

    //@Test
    public void testVisibility(){
        onView(withId(R.id.auth_fragment_signin)).check(matches(isDisplayed()));
    }

    //@Test
    public void testOnSuccessSignIn() throws InterruptedException {
        onView(withId(R.id.mail)).perform(replaceText(validMail()));
        onView(withId(R.id.pwd)).perform(replaceText(validPassword()));
        onView(withId(R.id.signin)).perform(click());
        Thread.sleep(2000); // Wait for 2 secs
        onView(withId(R.id.main_activity_layout)).check(matches(isDisplayed()));
    }

    //@Test
    public void testOnFailSignIn() {
        onView(withId(R.id.mail)).perform(replaceText(validMail()));
        onView(withId(R.id.pwd)).perform(replaceText(validPassword()));
        onView(withId(R.id.signin)).perform(click());
        //onView(withId(R.id.auth_fragment_signin)).check(matches(isDisplayed()));
    }

}
