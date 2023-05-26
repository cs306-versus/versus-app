package com.github.versus;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static com.github.versus.utils.EmulatorUserProvider.validMail;
import static com.github.versus.utils.EmulatorUserProvider.validPassword;
import static org.junit.Assert.fail;

import androidx.core.view.GravityCompat;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.github.versus.auth.VersusAuthenticator;
import com.github.versus.utils.FirebaseEmulator;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public final class SearchFragmentTest {

    static {
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
    }


    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void navigateToFrag() {
        VersusAuthenticator auth = VersusAuthenticator.getInstance(FirebaseAuth.getInstance());
        Task <AuthResult> authResultTask = auth.signInWithMail(validMail(),validPassword());
        spinAndWait(authResultTask);
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        onView(withId(R.id.nav_search)).perform(click());
    }

    @Test
    public void testScrollRecyclerView() {
        onView(withId(R.id.recyclerView)).perform(click());
    }

    @Test
    public void testMakePost() {
        onView(withId(R.id.add_posts)).perform(click());
        onView(withId(R.id.editPostTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.editPostTitle)).perform(typeText("TEST POST"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("Archery")).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.editMaxPlayers)).perform(typeText("4"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
    }


    @Test
    public void testCancelPost() {
        onView(withId(R.id.add_posts)).perform(click());
        onView(withId(R.id.editPostTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.editPostTitle)).perform(typeText("TEST POST"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("Archery")).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.editMaxPlayers)).perform(typeText("4"), closeSoftKeyboard());
        onView(withId(android.R.id.button2)).perform(click());
    }


    @Test
    public void testCreatePostWithLocation() throws InterruptedException {

        onView(withId(R.id.add_posts)).perform(click());
        onView(withId(R.id.editPostTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.editPostTitle)).perform(typeText("TEST POST"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("Archery")).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.editMaxPlayers)).perform(typeText("4"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("Choose")).perform(click());


    }
    @Test
    public void testCreatePostCancel() throws InterruptedException {

        onView(withId(R.id.add_posts)).perform(click());
        onView(withId(R.id.editPostTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.editPostTitle)).perform(typeText("TEST POST"), closeSoftKeyboard());

        onView(withText("CANCEL")).perform(click());



    }
    @Test
    public void testCreatePostAndCancel() throws InterruptedException {

        onView(withId(R.id.add_posts)).perform(click());
        onView(withId(R.id.editPostTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.editPostTitle)).perform(typeText("TEST POST"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("Archery")).perform(click());
        onView(withText("CANCEL")).perform(click());


    }
    @Test
    public void testCreatePostAndThenCancel() throws InterruptedException {

        onView(withId(R.id.add_posts)).perform(click());
        onView(withId(R.id.editPostTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.editPostTitle)).perform(typeText("TEST POST"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("Archery")).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.editMaxPlayers)).perform(typeText("4"), closeSoftKeyboard());

        onView(withText("CANCEL")).perform(click());


    }
    @Test
    public void testCreatePostWithLocationCanceled() {

        onView(withId(R.id.add_posts)).perform(click());
        onView(withId(R.id.editPostTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.editPostTitle)).perform(typeText("TEST POST"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("Archery")).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.editMaxPlayers)).perform(typeText("4"), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withText("Cancel")).perform(click());

    }

    @Test
    public void findEditSearch() {
        boolean success = false;
        for (int i = 0; i < 50; i++) {
            onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition(i));
            try {
                onView(withText("Edit Post")).perform(click());
                success = true;
                break;
            } catch (Exception e) {

            }
        }
        if (success) {
            onView(withId(R.id.game_players)).perform(click());
            onView(withId(R.id.game_players)).perform(click());
            onView(withId(R.id.edit_sport)).perform(click());
        }
    }
    private Task<AuthResult> spinAndWait(Task<AuthResult> task){
        // spin and wait for the task to complete
        while (!(task.isComplete() || task.isCanceled())) ;
        return task;
    }
    /**
     * ElapsedTimeIdlingResource is an implementation of the IdlingResource interface.
     * It is used to wait for a specified amount of time before allowing the test to continue.
     * This can be useful when testing UI components that require a certain time to load or update.
     */
    public class ElapsedTimeIdlingResource implements IdlingResource {

        private final long startTime;
        private final long waitingTime;
        private ResourceCallback resourceCallback;

        /**
         * Constructs an ElapsedTimeIdlingResource with a specified waiting time.
         *
         * @param waitingTime The amount of time to wait, in milliseconds, before allowing the test to continue.
         */
        public ElapsedTimeIdlingResource(long waitingTime) {
            this.startTime = System.currentTimeMillis();
            this.waitingTime = waitingTime;
        }

        /**
         * Returns the name of the ElapsedTimeIdlingResource.
         *
         * @return A String containing the name of the ElapsedTimeIdlingResource.
         */
        @Override
        public String getName() {
            return OptionChooseLocationTest.ElapsedTimeIdlingResource.class.getName() + ":" + waitingTime;
        }

        /**
         * Checks whether the specified waiting time has elapsed.
         *
         * @return true if the waiting time has elapsed, false otherwise.
         */
        @Override
        public boolean isIdleNow() {
            long elapsed = System.currentTimeMillis() - startTime;
            boolean idle = (elapsed >= waitingTime);
            if (idle && resourceCallback != null) {
                resourceCallback.onTransitionToIdle();
            }
            return idle;
        }
        /**
         * Registers a ResourceCallback to be notified when the resource transitions to the idle state.
         *
         * @param resourceCallback The ResourceCallback to be registered.
         */
        @Override
        public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
            this.resourceCallback = resourceCallback;
        }
    }

}