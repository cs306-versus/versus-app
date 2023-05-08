package com.github.versus;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.auth.AuthActivity;
import com.github.versus.utils.FirebaseEmulator;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static com.github.versus.utils.auth.EmulatorUserProvider.validMail;
import static com.github.versus.utils.auth.EmulatorUserProvider.validPassword;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class EntryActivityTest {

    private FirebaseAuth auth;

    @Before public void setUpEmulator() {
        auth = FirebaseEmulator.FIREBASE_AUTH;
    }

    @Test
    public void switchToMainActivity(){
        // Sign out
        auth.signOut();
        // Login to a test account
        Task<AuthResult> task = auth.signInWithEmailAndPassword(validMail(), validPassword());
        while(!(task.isComplete() || task.isCanceled())); // Spin and wait for login
        assertTrue(task.isSuccessful()); // Confirm login
        try(ActivityScenario<EntryActivity> scenario = ActivityScenario.launch(EntryActivity.class)){
            Intents.init();
            intending(hasComponent(MainActivity.class.getName()));
        } finally {
            Intents.release();
        }
    }

    @Test
    public void switchToAuthActivity(){
        // Sign out
        auth.signOut();
        try(ActivityScenario<EntryActivity> scenario = ActivityScenario.launch(EntryActivity.class)){
            Intents.init();
            intending(hasComponent(AuthActivity.class.getName()));
        } finally {
            Intents.release();
        }
    }

}
