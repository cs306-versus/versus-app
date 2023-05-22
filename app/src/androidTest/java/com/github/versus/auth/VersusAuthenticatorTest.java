package com.github.versus.auth;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.utils.FirebaseEmulator;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

import static com.github.versus.utils.auth.EmulatorUserProvider.*;

//@RunWith(AndroidJUnit4.class)
public class VersusAuthenticatorTest {

    static {
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
    }

    private final VersusAuthenticator auth = VersusAuthenticator.getInstance(FirebaseEmulator.FIREBASE_AUTH);

    //@Test
    public void createSuccessfulMailAccount() {
        // Request an account creation
        Task<AuthResult> task = auth.createAccountWithMail(freeMail(), validPassword(), null);

        // spin and wait for the task to complete
        while (!(task.isComplete() || task.isCanceled())) ;

        // Generate the error message & execute the test
        Optional.ofNullable(task.getException())
                .map(Throwable::getMessage)
                .or(() -> Optional.of(""))
                .ifPresent(msg -> assertTrue(msg, task.isSuccessful()));
    }

    //@Test
    public void signInSuccessfulMailAccount() {
        // Request an account creation
        Task<AuthResult> task = auth.signInWithMail(validMail(), validPassword());

        // spin and wait for the task to complete
        while (!(task.isComplete() || task.isCanceled())) ;

        // Generate the error message & execute the test
        Optional.ofNullable(task.getException())
                .map(Throwable::getMessage)
                .or(() -> Optional.of(""))
                .ifPresent(msg -> assertTrue(msg, task.isSuccessful()));
    }

    //@Test
    public void testSignOut() {
        auth.signOut();
        assertNull(auth.currentUser());
    }

}
