package com.github.versus.auth;


import com.github.versus.user.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.io.Serializable;

/**
 * ???
 */
public interface Authenticator extends Serializable {

    /**
     * ???
     *
     * @param mail
     * @param password
     * @return
     */
    Task<AuthResult> createAccountWithMail(String mail, String password);

    /**
     * ???
     *
     * @param mail
     * @param password
     * @return
     */
    Task<AuthResult> signInWithMail(String mail, String password);

    /**
     * ???
     *
     * @return
     */
    User currentUser();

    /**
     * ???
     */
    void signOut();


}
