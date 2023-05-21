package com.github.versus.auth;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static com.github.versus.utils.auth.EmulatorUserProvider.validMail;
import static com.github.versus.utils.auth.EmulatorUserProvider.validPassword;

import static org.junit.Assert.fail;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.R;
import com.github.versus.utils.FirebaseEmulator;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AuthActivityTest {

    private final Authenticator authenticator = VersusAuthenticator.getInstance(FirebaseEmulator.FIREBASE_AUTH);

    @Rule
    public ActivityScenarioRule<AuthActivity> scenario = new ActivityScenarioRule<>(AuthActivity.class);

    @Before
    public void setUp() {
        // HR : Make sure that the user has signed out
        authenticator.signOut();
    }

    @Test
    public void checkVisibility(){
        onView(withId(R.id.auth_activity_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void withCredentialCached(){
        // HR : Make sure the credentials are cached
        Task<AuthResult> task = authenticator.signInWithMail(validMail(), validPassword());
        // HR : Spin and wait for completion
        while (!task.isComplete() && !task.isCanceled());
        // HR : Verification part
        if (task.isCanceled())
            fail("Sign in was cancelled");
        else if(!task.isSuccessful())
            fail("Sign in failed");
        else{
            // HR : reload the activity and check that the main_activity is visible
            scenario.getScenario().recreate();
            onView(withId(R.id.main_activity_layout)).check(matches(isDisplayed()));
        }

    }

    @Test
    public void withCredentialUncached(){
        // HR : Check that the auth_fragment is visible
        onView(withId(R.id.auth_fragment)).check(matches(isDisplayed()));
    }

}
