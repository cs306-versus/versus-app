package com.github.versus.auth;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static java.util.Objects.requireNonNull;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MailPasswordFragmentTest {
    @Rule
    public ActivityScenarioRule<AuthActivity> scenario = new ActivityScenarioRule<>(AuthActivity.class);

    private static final String test_account_mail = "demo@versus.ch";

    private static final String test_account_pwd = "123456789";

    @Test
    public void testOnSuccessLogin(){
        onView(withId(R.id.auth_login_mail)).perform(click());
        onView(withId(R.id.auth_login_mail_mail)).perform(replaceText(test_account_mail));
        onView(withId(R.id.auth_login_mail_password)).perform(replaceText(test_account_pwd));
        onView(withId(R.id.auth_login_mail_button)).perform(click());
        scenario.getScenario().onActivity(a -> requireNonNull(a.getPackageName()));
    }

    @Test
    public void testOnFailLogin(){
        onView(withId(R.id.auth_login_mail)).perform(click());
        onView(withId(R.id.auth_login_mail_mail)).perform(replaceText(test_account_mail));
        onView(withId(R.id.auth_login_mail_password)).perform(replaceText(test_account_pwd + "0"));
        onView(withId(R.id.auth_login_mail_button)).perform(click());
    }

    // TODO HR : Add tests here

}
