package com.github.versus.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.versus.R;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }

    /**
     * ???
     * @param view
     */
    public void loginWithGoogleRequest(View view) {
        // TODO : this method should be defined in the AuthFragment class
        Toast.makeText(this, "loginWithGoogleRequest",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * ???
     * @param view
     */
    public void loginWithMailRequest(View view) {
        // TODO : this method should be defined in the AuthFragment class
        Toast.makeText(this, "loginWithGoogleRequest",
                Toast.LENGTH_SHORT).show();
    }
}