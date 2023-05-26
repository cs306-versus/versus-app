package com.github.versus;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.github.versus.utils.EmulatorUserProvider.validMail;
import static com.github.versus.utils.EmulatorUserProvider.validPassword;

import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.github.versus.auth.VersusAuthenticator;
import com.github.versus.db.FsUserManager;
import com.github.versus.utils.FirebaseEmulator;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public final class SearchFriendFragmentTest {

    static {
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
    }

    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);
    
    @Before
    public void navigateToFrag(){
        // Sign in the user
        VersusAuthenticator auth = VersusAuthenticator.getInstance(FirebaseAuth.getInstance());
        Task<AuthResult> task = auth.signInWithMail(validMail(), validPassword());
        spinAndWait(task);

        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        onView(withId(R.id.friend_search)).perform(click());

    }
    @Test
    public void testSearchBar(){
        onView(withId(R.id.search_users)).perform(click());
        onView(withId(R.id.search_users)).perform(replaceText("Ab"));

    }
    @Test
    public void testFriending(){
        FsUserManager fum = new FsUserManager(FirebaseFirestore.getInstance());
        fum.unfriendAll(FirebaseAuth.getInstance().getUid()).continueWith(
                res -> {
                     onView(withId(R.id.friend_added_true)).perform(click());
                     return true;

                }

        );


    }


    private Task<AuthResult> spinAndWait(Task<AuthResult> task){
        // spin and wait for the task to complete
        while (!(task.isComplete() || task.isCanceled())) ;
        return task;
    }

}
