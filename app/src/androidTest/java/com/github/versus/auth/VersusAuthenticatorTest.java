package com.github.versus.auth;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.user.VersusUser;
import com.github.versus.utils.FirebaseEmulator;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

import static com.github.versus.utils.auth.EmulatorUserProvider.*;

@RunWith(AndroidJUnit4.class)
public class VersusAuthenticatorTest {

    static {
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
    }

    private final VersusAuthenticator auth = VersusAuthenticator.getInstance(FirebaseEmulator.FIREBASE_AUTH);

    private final VersusUser.VersusBuilder builder = new VersusUser.VersusBuilder(null)
            .setFirstName("John")
            .setLastName("Doe")
            .setUserName("john-doe")
            .setPhone("0781234567")
            .setCity("Lausanne")
            .setZipCode(1013)
            .setRating(0);

    @Before
    public void setUp(){
        auth.signOut();
    }

    // ============================================================================================
    // ================================= ACCOUNT CREATION TEST ====================================
    // ============================================================================================

    @Test
    public void successAccountCreation() {
        // Prepare the builder
        String mail = freeMail();
        builder.setMail(mail);
        // Request an account creation
        Task<AuthResult> task = auth.createAccountWithMail(mail, validPassword(), builder);
        // spin and wait for the task to complete
        while (!(task.isComplete() || task.isCanceled())) ;
        // Generate the error message & execute the test
        Optional.ofNullable(task.getException())
                .map(Throwable::getMessage)
                .or(() -> Optional.of(""))
                .ifPresent(msg -> assertTrue(msg, task.isSuccessful()));
    }

    @Test
    public void failedAccountCreation() {
        // Prepare the builder
        String mail = validMail(); // Use a mail address already registered
        builder.setMail(mail);
        // Request an account creation
        Task<AuthResult> task = auth.createAccountWithMail(mail, nonValidPassword(), builder);
        // spin and wait for the task to complete
        while (!(task.isComplete() || task.isCanceled())) ;
        // Generate the error message & execute the test
        Optional.ofNullable(task.getException())
                .map(Throwable::getMessage)
                .or(() -> Optional.of(""))
                .ifPresent(msg -> assertFalse(msg, task.isSuccessful()));
    }

    // ============================================================================================
    // ====================================== SIGN IN TESTS =======================================
    // ============================================================================================

    @Test
    public void successSignInWithMail() {
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

    @Test
    public void failedSignInWithMail() {
        // Request an account creation
        Task<AuthResult> task = auth.signInWithMail(nonValidMail(), nonValidPassword());

        // spin and wait for the task to complete
        while (!(task.isComplete() || task.isCanceled())) ;

        // Generate the error message & execute the test
        Optional.ofNullable(task.getException())
                .map(Throwable::getMessage)
                .or(() -> Optional.of(""))
                .ifPresent(msg -> assertTrue(msg, !task.isSuccessful()));
    }

    // ============================================================================================
    // ==================================== SIGN OUT TESTS ========================================
    // ============================================================================================

    @Test
    public void testSignOut() {
        auth.signOut();
        assertNull(auth.currentUser());
    }

}
