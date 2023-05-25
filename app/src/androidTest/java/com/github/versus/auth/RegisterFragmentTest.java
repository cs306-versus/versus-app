package com.github.versus.auth;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static com.github.versus.utils.EmulatorUserProvider.freeMail;
import static com.github.versus.utils.EmulatorUserProvider.validMail;
import static com.github.versus.utils.EmulatorUserProvider.validPassword;

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
public final class RegisterFragmentTest {


    static {
        // HR : Make sure the emulator is launched
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
    }

    private final Authenticator authenticator = VersusAuthenticator.getInstance(FirebaseEmulator.FIREBASE_AUTH);

    @Rule
    public ActivityScenarioRule<AuthActivity> scenario = new ActivityScenarioRule<>(AuthActivity.class);

    @Before
    public void setUp() {
        // HR : Make sure that the user is signed out
        authenticator.signOut();
        onView(withId(R.id.register_btn)).perform(click());
    }

    @Test
    public void testVisibility(){
        onView(withId(R.id.auth_fragment_register)).check(matches(isDisplayed()));
        
    }

    @Test
    public void successAccountCreation() throws InterruptedException {
        // HR : Fill out the form
        onView(withId(R.id.firstName)).perform(replaceText("John")); // HR : Set the first name
        onView(withId(R.id.lastName)).perform(replaceText("Doe")); // HR : Set the last name
        onView(withId(R.id.mail)).perform(replaceText(freeMail())); // HR : Set the mail
        onView(withId(R.id.phone)).perform(replaceText("0781234567")); // HR : Set the phone
        onView(withId(R.id.pwd)).perform(replaceText(validPassword())); // HR : Set the pwd
        onView(withId(R.id.confirm_pwd)).perform(replaceText(validPassword())); // HR : Set the pwd confirmation

        // HR : Request account creation
        onView(withId(R.id.register_btn)).perform(click());

        // HR : Check that the validation mail fragment is visible
        Thread.sleep(2000);
        onView(withId(R.id.auth_fragment_mail_validation)).check(matches(isDisplayed()));
    }

    @Test
    public void failedAccountCreation() throws InterruptedException {
        // HR : Fill out the form
        onView(withId(R.id.firstName)).perform(replaceText("John")); // HR : Set the first name
        onView(withId(R.id.lastName)).perform(replaceText("Doe")); // HR : Set the last name
        onView(withId(R.id.mail)).perform(replaceText(validMail())); // HR : Set the mail
        onView(withId(R.id.phone)).perform(replaceText("0781234567")); // HR : Set the phone
        onView(withId(R.id.pwd)).perform(replaceText(validPassword())); // HR : Set the pwd
        onView(withId(R.id.confirm_pwd)).perform(replaceText(validPassword())); // HR : Set the pwd confirmation

        // HR : Request account creation
        onView(withId(R.id.register_btn)).perform(click());

        // HR : Check that the register fragment is still visible
        Thread.sleep(2000);
        onView(withId(R.id.auth_fragment_register)).check(matches(isDisplayed()));
    }

}
