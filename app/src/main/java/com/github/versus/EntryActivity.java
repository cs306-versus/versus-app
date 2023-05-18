package com.github.versus;

import static java.util.Objects.isNull;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

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
public final class EntryActivity extends AppCompatActivity {

    /**
     * Key to add an authenticator server to an Intent
     */
    @VisibleForTesting
    public static final String AUTH_INTENT = "auth-server";

    private Authenticator auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // See edge-to-edge : https://developer.android.com/develop/ui/views/layout/edge-to-edge#java
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        initAuthentication();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Fetch the current user
        auth.signOut();
        User user = auth.currentUser();
        // Pick the target activity to start
        Class<?> activity = isNull(user)
                ? AuthActivity.class
                : MainActivity.class;
        // Prepare the Intent to start the activity
        Intent intent = new Intent(this, activity);
        // Launch th e targeted activity
        startActivity(intent);
        // Kill this activity as it's not needed
        finish();
    }

    /**
     * Initialize the authentication
     */
    private void initAuthentication() {
        Intent intent = getIntent();
        auth = intent.hasExtra(AUTH_INTENT)
                ? (Authenticator) intent.getSerializableExtra(AUTH_INTENT)
                : VersusAuthenticator.getInstance(FirebaseAuth.getInstance());
    }

}