package com.github.versus;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.github.versus.auth.AuthActivity;
import com.github.versus.utils.FirebaseEmulator;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static com.github.versus.utils.auth.EmulatorUserProvider.validMail;
import static com.github.versus.utils.auth.EmulatorUserProvider.validPassword;
import static org.junit.Assert.assertTrue;

import android.content.Intent;

@RunWith(AndroidJUnit4.class)
public class EntryActivityTest {

    public ActivityTestRule<EntryActivity> rule;

    private FirebaseAuth auth;

    @Before public void setUpEmulator() {
        Intents.init();
        rule = new ActivityTestRule<>(EntryActivity.class);
        auth = FirebaseEmulator.FIREBASE_AUTH;
        // Sign out
        auth.signOut();
        rule.launchActivity(new Intent());
    }

    @After public void tearDown(){
        Intents.release();
    }

    @Test
    public void switchToMainActivity(){
        // Sign out
        auth.signOut();
        // Login to a test account
        Task<AuthResult> task = auth.signInWithEmailAndPassword(validMail(), validPassword());
        while(!(task.isComplete() || task.isCanceled())); // Spin and wait for login
        assertTrue(task.isSuccessful()); // Confirm login
        // Verify Intent
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void switchToAuthActivity(){
        // Sign out
        auth.signOut();
        intended(hasComponent(AuthActivity.class.getName()));
    }

}
