package com.github.versus.auth;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.junit.Assert.fail;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.R;
import com.github.versus.utils.FirebaseEmulator;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public final class AuthActivityTest {

    static {
        // HR : Make sure the emulator is launched
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
    }
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

}
