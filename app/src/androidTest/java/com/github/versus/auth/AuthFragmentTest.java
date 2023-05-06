package com.github.versus.auth;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.MainActivity;
import com.github.versus.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.github.versus.utils.auth.EmulatorUserProvider.*;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class AuthFragmentTest {

    @Rule
    public final ActivityScenarioRule<AuthActivity> scenario =
            new ActivityScenarioRule<>(AuthActivity.class);

    //@Test
    //public void testSignInRequest() {
    //    onView(withId(R.id.auth_signin)).perform(click());
    //    // Check if the SignIn layout is displayed
    //    onView(withId(R.id.frag_signin_layout)).check(matches(isDisplayed()));
    //}

    @Test
    public void testGoogleLoginRequest() {
        onView(withId(R.id.auth_login_google)).perform(click());
        onView(withId(R.id.frag_login_google_layout))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        // TODO HR : Still need to check if the activity changed
    }

    //@Test
    //public void testOnSuccessLogin() {
    //    try {
    //        Intents.init();
    //        onView(withId(R.id.auth_login_mail_mail)).perform(replaceText(validMail()));
    //        onView(withId(R.id.auth_login_mail_pwd)).perform(replaceText(validPassword()));
    //        onView(withId(R.id.auth_login_mail)).perform(click());
    //        // Intents.intended(allOf(hasComponent(MainActivity.class.getName())));
    //    } finally {
    //        Intents.release();
    //    }
    //}

    @Test
    public void testOnFailLogin() {
        onView(withId(R.id.auth_login_mail_mail)).perform(replaceText(nonValidMail()));
        onView(withId(R.id.auth_login_mail_pwd)).perform(replaceText(nonValidPassword()));
        onView(withId(R.id.auth_login_mail)).perform(click());
        // TODO HR : Still need to check for error message
    }


}
