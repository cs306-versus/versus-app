package com.github.versus.auth;

import com.github.versus.user.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        // TODO : Convert a firebase user to a 'Versus' user
        return null;
    }

    @Override
    public void signOut() {
        auth.signOut();
    }
}