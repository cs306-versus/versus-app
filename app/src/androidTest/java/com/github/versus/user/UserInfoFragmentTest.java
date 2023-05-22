package com.github.versus.user;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.github.versus.MainActivity;
import com.github.versus.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

//@RunWith(AndroidJUnit4.class)
public class UserInfoFragmentTest {

    //@Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);
    UserInfoFragment frag;

    //@Before
    public void setUpFragment() {
        Task<AuthResult> task = FirebaseAuth.getInstance().signInWithEmailAndPassword("test@versus.ch", "123456789");
        while (!task.isComplete() && !task.isCanceled()) ;
        frag = setUp();
        // Wait for async
        for (int i = 0; i < 40_000_000; i++) ;
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());
        onView(withId(R.id.main_activity_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        onView(withId(R.id.nav_user_profil)).perform(click());
    }

    //@Test
    public void checkWithNull() {
        //frag.updateUI(null);
        //onView(withId(R.id.info_uid)).check(matches(withText(containsString("EPPOpGluoEQe6OsYZJ96mvZ1Ytu2"))));
    }

    //@Test
    public void checkWithUser() {
        VersusUser.VersusBuilder builder = new VersusUser.VersusBuilder("aywxdctfvgb");
        builder.setFirstName("John")
                .setLastName("Doe")
                .setUserName("johndoe")
                .setPhone("+41782345678")
                .setMail("john.doe@versus.ch")
                .setRating(3)
                .setZipCode(0)
                .setCity("Lausanne")
                .setPreferredSports(List.of());
        frag.updateUI(builder.build());
    }

    private UserInfoFragment setUp() {
        UserInfoFragment fragment = new UserInfoFragment();
        scenario.getScenario().onActivity(activity -> {
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.add(fragment, "fragment");
            transaction.commitNow();
        });
        return fragment;
    }

}