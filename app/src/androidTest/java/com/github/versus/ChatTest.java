

package com.github.versus;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static com.github.versus.utils.EmulatorUserProvider.validMail;
import static com.github.versus.utils.EmulatorUserProvider.validPassword;

import androidx.core.view.GravityCompat;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.auth.VersusAuthenticator;
import com.github.versus.db.FsUserManager;
import com.github.versus.utils.FirebaseEmulator;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;


@RunWith(AndroidJUnit4.class)
public final class ChatTest {


    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() throws ExecutionException, InterruptedException {
        // Sign in the user
        VersusAuthenticator auth = VersusAuthenticator.getInstance(FirebaseAuth.getInstance());
        Task<AuthResult> task = auth.signInWithMail(validMail(), validPassword());
        spinAndWait(task);
        FsUserManager fsm = new FsUserManager(FirebaseFirestore.getInstance());
        fsm.addFriend(FirebaseAuth.getInstance().getUid(), "0ZnBDoch6feHhmNxlLjPNSne0HL2").get();


        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        //Performing the click on the trending sports button
        onView(withId(R.id.nav_chats)).perform(click());

    }
    @Test
    public void testChatActivity() throws ExecutionException, InterruptedException {
        onView(withId(R.id.usersRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.inputMessage)).perform(click());
        onView(withId(R.id.inputMessage)).perform(replaceText("haha"));
        onView(withId(R.id.imageSend)).perform(click());
        FsUserManager fsm = new FsUserManager(FirebaseFirestore.getInstance());

        fsm.unfriendAll(FirebaseAuth.getInstance().getUid());

    }


    private Task<AuthResult> spinAndWait(Task<AuthResult> task){
        // spin and wait for the task to complete
        while (!(task.isComplete() || task.isCanceled())) ;
        return task;
    }

}