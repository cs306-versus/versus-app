package com.github.versus.user;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.github.versus.utils.EmulatorUserProvider.validMail;
import static com.github.versus.utils.EmulatorUserProvider.validPassword;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.MainActivity;
import com.github.versus.R;
import com.github.versus.auth.VersusAuthenticator;
import com.github.versus.utils.FirebaseEmulator;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class UserInfoFragmentTest {

    static {
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
    }

    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUpFragment() {
        Task<AuthResult> task = VersusAuthenticator.getInstance(FirebaseEmulator.FIREBASE_AUTH)
                .signInWithMail(validMail(), validPassword());
        spinAndWait(task);
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        onView(withId(R.id.nav_user_profil)).perform(click());
    }

    @Test
    public void checkWithUser() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.info_first_name)).check(matches(isDisplayed()));
        onView(withId(R.id.info_last_name)).check(matches(isDisplayed()));
        onView(withId(R.id.info_username));

    }

    private Task<AuthResult> spinAndWait(Task<AuthResult> task){
        // spin and wait for the task to complete
        while (!(task.isComplete() || task.isCanceled())) ;
        return task;
    }

}