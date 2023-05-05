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

import java.util.Objects;
import java.util.Optional;

@RunWith(AndroidJUnit4.class)
public class VersusAuthenticatorTest {

    VersusAuthenticator auth;
    FirebaseAuth firebase;

    private final String MAIL_USER_IN_DB = "user.test+1@versus.ch";

    private final String MAIL_USER_NOT_IN_DB = "user.test+2@versus.ch";

    private final String PWD = "123456789";

    @Before
    public void setUp(){
        // Set up firebase auth to use the emulator
        firebase = FirebaseAuth.getInstance();
        firebase.useEmulator("10.0.2.2", 9099);
        // Instantiate the VersusAuthenticator to use the emulator
        auth = VersusAuthenticator.getInstance(firebase);
    }

    @Test
    public void createSuccessfulMailAccount(){
        // Request an account creation
        Task<AuthResult> task = auth.createAccountWithMail(MAIL_USER_NOT_IN_DB, PWD);

        // spin and wait for the task to complete
        while(!(task.isComplete() || task.isCanceled()));

        // Generate the error message & execute the test
        Optional.ofNullable(task.getException())
                .map(Throwable::getMessage)
                .or(() -> Optional.of(""))
                .ifPresent(msg -> assertTrue(msg, task.isSuccessful()));
    }

    @Test
    public void signInSuccessfulMailAccount(){
        // Request an account creation
        Task<AuthResult> task = auth.signInWithMail(MAIL_USER_IN_DB, PWD);

        // spin and wait for the task to complete
        while(!(task.isComplete() || task.isCanceled()));

        // Generate the error message & execute the test
        Optional.ofNullable(task.getException())
                .map(Throwable::getMessage)
                .or(() -> Optional.of(""))
                .ifPresent(msg -> assertTrue(msg, task.isSuccessful()));
    }

    @Test
    public void testSignOut(){
        auth.signOut();
        assertNull(auth.currentUser());
    }

}
