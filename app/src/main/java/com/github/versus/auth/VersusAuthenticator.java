package com.github.versus.auth;

import static java.util.Objects.isNull;

import com.github.versus.db.FsUserManager;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * ???
 */
public final class VersusAuthenticator implements Authenticator {

    /**
     *
     */
    private final FirebaseAuth auth;

    private VersusAuthenticator(FirebaseAuth auth) {
        this.auth = auth;
    }

    /**
     * ???
     *
     * @return
     */
    public static VersusAuthenticator getInstance(FirebaseAuth auth) {
        return new VersusAuthenticator(auth);
    }

    @Override
    public Task<AuthResult> createAccountWithMail(String mail, String password) {
        return auth.createUserWithEmailAndPassword(mail, password);
    }

    @Override
    public Task<AuthResult> signInWithMail(String mail, String password) {
        return auth.signInWithEmailAndPassword(mail, password);
    }

    @Override
    public User currentUser() {
        FirebaseUser firebase_user = auth.getCurrentUser();
        // TODO HR : Still need to build the correct user
        return isNull(firebase_user) ? null : new VersusUser();
    }

    @Override
    public void signOut() {
        auth.signOut();
    }
}
