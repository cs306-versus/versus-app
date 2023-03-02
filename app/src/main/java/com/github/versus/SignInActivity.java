package com.github.versus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignIn";

    private FirebaseAuth mAuth;

    private Button signInButton;
    private EditText mail;
    private EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        // Init all UI Components
        init();
        registerClickListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void init(){
        mAuth = FirebaseAuth.getInstance();
        signInButton = findViewById(R.id.sign_in);
        mail = findViewById(R.id.mail);
        pwd = findViewById(R.id.pwd);
    }

    private void registerClickListener(){
        signInButton.setOnClickListener(view -> {
            String m = mail.getText().toString();
            String p = pwd.getText().toString();
            mAuth.createUserWithEmailAndPassword(m, p)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    });
        });
    }
}