package com.github.versus;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.github.versus.auth.AuthActivity;
import com.github.versus.auth.Authenticator;
import com.github.versus.auth.VersusAuthenticator;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;
import com.github.versus.utils.FirebaseEmulator;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static com.github.versus.utils.auth.EmulatorUserProvider.validMail;
import static com.github.versus.utils.auth.EmulatorUserProvider.validPassword;
import static org.junit.Assert.fail;

import android.content.Intent;

import java.io.Serializable;

@RunWith(AndroidJUnit4.class)
public class EntryActivityTest {

    static {
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
    }

    public ActivityTestRule<EntryActivity> rule;

    @Before public void setUpEmulator() {
        Intents.init();
        rule = new ActivityTestRule<>(EntryActivity.class);
    }

    @After public void tearDown(){
        Intents.release();
    }

    @Test
    public void switchToMainActivity(){
        Intent intent = new Intent();
        intent.putExtra(EntryActivity.AUTH_INTENT, new UserAuthenticator());
        // Launch the activity
        rule.launchActivity(intent);
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void switchToAuthActivity(){
        Intent intent = new Intent();
        intent.putExtra(EntryActivity.AUTH_INTENT, new NoUserAuthenticator());
        // Launch the activity
        rule.launchActivity(intent);
        intended(hasComponent(AuthActivity.class.getName()));
    }

    private static class NoUserAuthenticator implements Authenticator, Serializable {

        @Override
        public Task<AuthResult> createAccountWithMail(String mail, String password, User.Builder user) {
            return null;
        }

        @Override
        public Task<AuthResult> signInWithMail(String mail, String password) {
            return null;
        }

        @Override
        public User currentUser() {
            return null;
        }

        @Override
        public void signOut() {

        }
    }

    private static class UserAuthenticator implements Authenticator, Serializable {

        @Override
        public Task<AuthResult> createAccountWithMail(String mail, String password, User.Builder user) {
            return null;
        }

        @Override
        public Task<AuthResult> signInWithMail(String mail, String password) {
            return null;
        }

        @Override
        public User currentUser() {
            return new VersusUser();
        }

        @Override
        public void signOut() {

        }
    }

    /*
    //@Test
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

    //@Test
    public void withCredentialUncached(){
        // HR : Check that the auth_fragment is visible
        onView(withId(R.id.auth_fragment)).check(matches(isDisplayed()));
    }
    */

}
