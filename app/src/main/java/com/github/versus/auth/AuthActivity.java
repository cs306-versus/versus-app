package com.github.versus.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.github.versus.R;

import java.util.Optional;

/**
 * Main Activity for the authentication process
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 */
public final class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        Optional.ofNullable(getSupportActionBar()).ifPresent(ActionBar::hide);
        setContentView(R.layout.activity_auth);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}