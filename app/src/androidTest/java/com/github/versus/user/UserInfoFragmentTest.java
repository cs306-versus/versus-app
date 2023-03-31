package com.github.versus.user;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.core.StringContains.containsString;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.MainActivity;
import com.github.versus.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UserInfoFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUpFragment(){
        Task<?> task = FirebaseAuth.getInstance().signInWithEmailAndPassword("test@versus.ch", "123456789");
        while(!task.isComplete() && !task.isCanceled());
        setUp();
        //onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed(GravityCompat.START))).perform(DrawerActions.open());
        //onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isOpen(GravityCompat.START)));
        //onView(withId(R.id.nav_user_profil)).perform(click());
    }

    @Test
    public void checkUID(){
        onView(withId(R.id.info_uid)).check(matches(withText(containsString("EPPOpGluoEQe6OsYZJ96mvZ1Ytu2"))));
    }

    private void setUp() {
        scenario.getScenario().onActivity(activity -> {
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            UserInfoFragment voiceFragment = new UserInfoFragment();
            transaction.add(voiceFragment, "voiceFragment");
            transaction.commit();
        });
    }

}