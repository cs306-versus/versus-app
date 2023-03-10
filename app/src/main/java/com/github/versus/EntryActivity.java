package com.github.versus;

import static java.util.Objects.isNull;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.versus.auth.AuthActivity;
import com.github.versus.auth.Authenticator;
import com.github.versus.auth.VersusAuthenticator;
import com.github.versus.user.User;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Entry point to the Application.
 * <p>
 * This Activity will check if a user is logged in. If it's the case,
 * it will yield for the {@link MainActivity}. Otherwise, it will ask
 * the user to log in by starting the {@link AuthActivity}.
 */
public class EntryActivity extends AppCompatActivity {

    private Authenticator auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAuthentication();
    }

    @Override
    protected void onStart() {
        super.onStart();
        User user = auth.currentUser();
        yieldActivity(user);
    }

    /**
     * ???
     *
     * @param user
     */
    private void yieldActivity(@Nullable User user) {
        Class<?> activity = isNull(user)
                ? AuthActivity.class
                : MainActivity.class;
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    /**
     * Initialize the authentication
     */
    private void initAuthentication() {
        auth = VersusAuthenticator.getInstance();
        auth.signOut();
        //mAuth.addAuthStateListener(this::handleStateChange);
        //mAuth.addIdTokenListener(this::handleIdTokenChanged);
    }

    /**
     * ???
     *
     * @param auth
     */
    private void handleStateChange(FirebaseAuth auth) {
        // TODO HR : To protect the App from LogIn issues, add listeners
        //  so we can handle such errors
    }

    /**
     * ???
     *
     * @param auth
     */
    private void handleIdTokenChanged(FirebaseAuth auth) {
        // TODO HR : To protect the App from LogIn issues, add listeners
        //  so we can handle such errors
    }

}