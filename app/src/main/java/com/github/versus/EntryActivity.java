package com.github.versus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.github.versus.auth.AuthActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static java.util.Objects.*;


/**
 * Entry point to the Application.
 *
 * This Activity will check if a user is logged in. If it's the case,
 * it will yield for the {@link MainActivity}. Otherwise, it will ask
 * the user to log in by starting the {@link AuthActivity}.
 */
public class EntryActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAuthentication();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.signOut();
        FirebaseUser user = user();
        yieldActivity(user);
    }

    /**
     * ???
     * @return
     */
    private @Nullable FirebaseUser user(){
        return mAuth.getCurrentUser();
    }

    /**
     * ???
     * @param user
     */
    private void yieldActivity(@Nullable FirebaseUser user){
        Class<?> activity = isNull(user)
                ? AuthActivity.class
                : MainActivity.class;
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    /**
     * Initialize the authentication
     */
    private void initAuthentication(){
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(this::handleStateChange);
        mAuth.addIdTokenListener(this::handleIdTokenChanged);
    }

    /**
     * ???
     * @param auth
     */
    private void handleStateChange(FirebaseAuth auth){
        // TODO HR : To protect the App from LogIn issues, add listeners
        //  so we can handle such errors
    }

    /**
     * ???
     * @param auth
     */
    private void handleIdTokenChanged(FirebaseAuth auth){
        // TODO HR : To protect the App from LogIn issues, add listeners
        //  so we can handle such errors
    }

}