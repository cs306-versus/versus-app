package com.github.versus.auth;

import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.widget.Button;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.MainActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BaseAuthFragmentTest {

    BaseAuthFragment frag;
    Context ctx;
    Button button;

    @Before
    public void setUp(){
        ctx = ApplicationProvider.getApplicationContext();
        frag = mock(BaseAuthFragment.class);
        button = new Button(ctx);
        frag.registerLoginButton(button);
    }

    @Test
    public void testOnSuccessLogin(){
        Task<AuthResult> result = mock(Task.class);
        Exception e = new RuntimeException();
        when(result.isComplete()).thenReturn(true);
        when(result.isSuccessful()).thenReturn(true);
        when(frag.requestAuthentication()).thenReturn(result);
        doThrow(e).when(frag).handleSuccessfulConnection(any());
        Intents.init();
        try {
            button.performClick();
            // TODO HR : Add Intent check here later
        } catch (Exception ex){
            if(e != ex) fail(ex.getMessage());
        }
        finally {
            Intents.release();
        }
    }

    // TODO HR : Add tests here

}
