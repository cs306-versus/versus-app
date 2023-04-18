package com.github.versus.auth;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class VersusAuthenticatorTest {

    VersusAuthenticator auth;
    FirebaseAuth authSpy;
    Task<AuthResult> resultSuccess;
    Task<AuthResult> resultFailure;
    Task<AuthResult> resultCancelled;

    @Before
    public void setUp(){
        FirebaseApp.clearInstancesForTest();
        Context ctx = ApplicationProvider.getApplicationContext();
        FirebaseApp.initializeApp(ctx);
        authSpy = mock(FirebaseAuth.class);
        auth = VersusAuthenticator.getInstance(authSpy);
        resultSuccess = mock(Task.class);
        when(resultSuccess.isSuccessful()).thenReturn(true);
        resultFailure = mock(Task.class);
        when(resultFailure.isSuccessful()).thenReturn(false);
        resultCancelled = mock(Task.class);
        when(resultCancelled.isCanceled()).thenReturn(true);
    }

    @Test
    public void createSuccessfulMailAccount(){
        when(authSpy.createUserWithEmailAndPassword(anyString(), anyString()))
                .thenReturn(resultSuccess);
        assertTrue(auth.createAccountWithMail("", "").isSuccessful());
    }

    @Test
    public void signInSuccessfulMailAccount(){
        when(authSpy.signInWithEmailAndPassword(anyString(), anyString()))
                .thenReturn(resultSuccess);
        assertTrue(auth.signInWithMail("", "").isSuccessful());
    }

    @Test
    public void testSignOut(){
        auth.signOut();
        assertNull(auth.currentUser());
    }

}
