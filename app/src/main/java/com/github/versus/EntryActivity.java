package com.github.versus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.github.versus.auth.LogInActivity;
import com.google.firebase.auth.FirebaseAuth;

import static java.util.Objects.*;


public class EntryActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.signOut();
        Class<?> activity = isNull(mAuth.getCurrentUser()) ? LogInActivity.class : MainActivity.class;
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

}