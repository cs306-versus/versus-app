package com.github.versus;

import static java.util.Objects.isNull;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.versus.auth.AuthActivity;
import com.github.versus.auth.Authenticator;
import com.github.versus.auth.VersusAuthenticator;
import com.github.versus.user.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.CompletableFuture;


/**
 * Entry point to the Application.
 * <p>
 * This Activity will check if a user is logged in. If it's the case,
 * it will yield for the {@link MainActivity}. Otherwise, it will ask
 * the user to log in by starting the {@link AuthActivity}.
 */
public class EntryActivity extends AppCompatActivity {

    /**
     * Key to add an authenticator server to an Intent
     */
    public static final String AUTH_INTENT = "auth-server";

    private Authenticator auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAuthentication();
        //setContentView(R.layout.activity_entry);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.signOut();
        //Log.println(Log.ERROR, "", "asdfghjkl");
        // TODO HR : This makes the app crash
        //((CompletableFuture<User>)auth.currentUser()).thenAccept(this::yieldActivity);
        yieldActivity(null);
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
        // HR : For testing purposes, we check if the intent contains an authenticator
        Intent intent = getIntent();
        auth = intent.hasExtra(AUTH_INTENT)
                ? (Authenticator) intent.getSerializableExtra(AUTH_INTENT)
                : VersusAuthenticator.getInstance(FirebaseAuth.getInstance());
    }

}