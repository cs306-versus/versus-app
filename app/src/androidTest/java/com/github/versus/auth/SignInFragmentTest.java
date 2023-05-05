package com.github.versus.auth;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
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

@RunWith(AndroidJUnit4.class)
public class SignInFragmentTest {

    @Rule
    public ActivityScenarioRule<AuthActivity> scenario = new ActivityScenarioRule<>(AuthActivity.class);

    @Before
    public void setUp(){
        onView(withId(R.id.auth_signin)).perform(click());
    }

    @Test
    public void testOnSuccessSignIn(){
        onView(withId(R.id.auth_signin_mail)).perform(replaceText(freeMail()));
        onView(withId(R.id.auth_signin_pwd)).perform(replaceText(validPassword()));
        onView(withId(R.id.auth_signin_button)).perform(click());
        // Spin & Check if the currentUser has changed
        while(isNull(FirebaseEmulator.FIREBASE_AUTH.getCurrentUser()));
        // TODO HR : This is not the best check, we should check the screen or catch the intent
    }

    @Test
    public void testOnFailSignIn() {
        onView(withId(R.id.auth_signin_mail)).perform(replaceText(nonValidMail()));
        onView(withId(R.id.auth_signin_pwd)).perform(replaceText(nonValidPassword()));
        onView(withId(R.id.auth_signin_button)).perform(click());
        // TODO HR : Still need to check here for error
    }

    // TODO HR : Add tests here

}
