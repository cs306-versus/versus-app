package com.github.versus.auth;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.not;

import static java.util.Objects.requireNonNull;

import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.Root;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.viewpager.widget.ViewPager;

import com.github.versus.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

@RunWith(AndroidJUnit4.class)
public class SignInFragmentTest {

    @Rule
    public ActivityTestRule<AuthActivity> scenario = new ActivityTestRule<>(AuthActivity.class);

    private View decorView;

    private static final String test_account_mail = "demo-test@versus.ch";

    private static final String test_account_pwd = "123456789";

    @Before
    public void setUp(){
        decorView = scenario.getActivity().getWindow().getDecorView();
        Task<?> task = FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(test_account_mail, test_account_pwd)
                .addOnSuccessListener(res -> requireNonNull(res.getUser()).delete());
        while(!task.isComplete()); // Wait for user to be deleted
    }

    @Test
    public void testOnSuccessSignIn(){
        onView(withId(R.id.auth_signin)).perform(click());
        onView(withId(R.id.auth_signin_mail)).perform(replaceText(test_account_mail));
        onView(withId(R.id.auth_signin_pwd)).perform(replaceText(test_account_pwd));
        onView(withId(R.id.auth_signin_button)).perform(click());
        while(FirebaseAuth.getInstance().getCurrentUser() == null); // Wait for login
        Task<?> task = FirebaseAuth.getInstance().getCurrentUser().delete();
        while(!task.isComplete()); // Wait for task to complete
    }

    @Test
    public void testOnFailSignIn() {
        //onView(withId(R.id.auth_signin)).perform(click());
        //Task<?> task = FirebaseAuth.getInstance().createUserWithEmailAndPassword(test_account_mail, test_account_pwd);
        //while(!task.isComplete()); // Wait for task to complete
        //onView(withId(R.id.auth_signin_mail)).perform(replaceText(test_account_mail));
        //onView(withId(R.id.auth_signin_pwd)).perform(replaceText(test_account_pwd));
        //onView(withId(R.id.auth_signin_button)).perform(click());
        //task = FirebaseAuth.getInstance().signInWithEmailAndPassword(test_account_mail, test_account_pwd);
        //while(!task.isComplete()); // Wait for task to complete
        //task = FirebaseAuth.getInstance().getCurrentUser().delete();
        //while(!task.isComplete()); // Wait for task to complete
    }

    private static Matcher<Root> isToast() {
        return new TypeSafeMatcher<Root>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("is toast");
            }

            @Override
            public boolean matchesSafely(Root root) {
                int type = root.getWindowLayoutParams().get().type;
                if (type == WindowManager.LayoutParams.TYPE_TOAST) {
                    IBinder windowToken = root.getDecorView().getWindowToken();
                    IBinder appToken = root.getDecorView().getApplicationWindowToken();
                    return windowToken == appToken;
                }
                return false;
            }
        };
    }

    // TODO HR : Add tests here

}
