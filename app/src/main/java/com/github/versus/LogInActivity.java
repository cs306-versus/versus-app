package com.github.versus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public final class LogInActivity extends AppCompatActivity {

    private static final String TAG = "SignIn";

    private FirebaseAuth mAuth;

    private EditText mail;
    private EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        init();
    }

    /**
     * Initialize UI Component references
     */
    private void init(){
        mAuth = FirebaseAuth.getInstance();
        mail = findViewById(R.id.mail_login);
        pwd = findViewById(R.id.pwd_login);
    }

    /**
     * ???
     * @param view
     */
    public void attemptToLogIn(View view){
        String m = mail.getText().toString();
        String p = pwd.getText().toString();
        mAuth.signInWithEmailAndPassword(m, p)
                .addOnCompleteListener(this, this::handleLogInResult);
    }

    /**
     * ???
     * @param task
     */
    private void handleLogInResult(@NonNull Task<AuthResult> task){
        if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            Log.d(TAG, "signInWithEmailAndPassword:success");
            startActivity(new Intent(LogInActivity.this, MainActivity.class));
        } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG, "signInWithEmailAndPassword:failure", task.getException());
            Toast.makeText(LogInActivity.this, "Authentication failed",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * ???
     * @param view
     */
    public void redirectToSignIn(View view){
        startActivity(new Intent(LogInActivity.this, SignInActivity.class));
    }

}