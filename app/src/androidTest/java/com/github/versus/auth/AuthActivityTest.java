package com.github.versus.auth;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.mock;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AuthActivityTest {

    private Context ctx;

    @Rule
    public ActivityScenarioRule<AuthActivity> scenario = new ActivityScenarioRule<>(AuthActivity.class);

    @Before
    public void setUp(){
        ctx = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void checkVisibleAuthFragment(){
        onView(withId(R.id.frag_auth_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void checkOnActivityResult(){
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(0, null);
        Intents.init();
        intending(hasComponent(AuthActivity.class.getName())).respondWith(result);
        scenario.getScenario().onActivity(a -> a.startActivityForResult(new Intent(ctx,AuthActivity.class), 0));
        Intents.release();
    }


}
