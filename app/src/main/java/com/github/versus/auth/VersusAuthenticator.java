package com.github.versus.auth;

import static java.util.Objects.isNull;

import com.github.versus.db.FsUserManager;
import com.github.versus.user.User;
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
    public Future<User> currentUser() {
        FirebaseUser firebase_user = auth.getCurrentUser();
        if (isNull(firebase_user))
            return null;
        FsUserManager db = new FsUserManager(FirebaseFirestore.getInstance());
        return db.fetch(firebase_user.getUid());
    }

    @Override
    public void signOut() {
        auth.signOut();
    }
}
