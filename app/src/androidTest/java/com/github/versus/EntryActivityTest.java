package com.github.versus;

import static org.junit.Assert.assertThat;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.auth.AuthActivity;
import com.github.versus.auth.Authenticator;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static com.github.versus.utils.VersusComponentName.of;

import static org.mockito.Mockito.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RunWith(AndroidJUnit4.class)
public class EntryActivityTest {

    // HR : NOTE THAT WE CANNOT MOCK STATIC METHODS ON ANDROID
    // HR : AS A WORK AROUND, WE START THE ACTIVITY WITH AN INTENT

    private Authenticator auth;
    private Intent intent;

    @Before
    public void setup(){
        auth = mock(Authenticator.class, withSettings().serializable());
        intent = new Intent(getApplicationContext(), EntryActivity.class);
        intent.putExtra(EntryActivity.AUTH_INTENT, auth);
    }

    @Test
    public void authIfNoUserConnected(){
        when(auth.currentUser()).thenReturn(null);
        Intents.init();
        try(ActivityScenario<EntryActivity> scenario = ActivityScenario.launch(intent)){
            Intent s_intent = Intents.getIntents().get(1);
            Matcher<Intent> matcher = IntentMatchers.hasComponent(of(AuthActivity.class));
            assertThat(s_intent, matcher);
        } finally {
            Intents.release();
        }
    }

    @Test
    public void startAppIfUserConnected(){
        when(auth.currentUser()).thenReturn(mock(User.class, withSettings().serializable()));
        Intents.init();
        try(ActivityScenario<EntryActivity> scenario = ActivityScenario.launch(intent)){
            Intent s_intent = Intents.getIntents().get(1);
            Matcher<Intent> matcher = IntentMatchers.hasComponent(of(MainActivity.class));
            assertThat(s_intent, matcher);
        } finally {
            Intents.release();
        }
    }


}
