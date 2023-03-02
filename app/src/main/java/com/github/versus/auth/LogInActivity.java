package com.github.versus.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.versus.MainActivity;
import com.github.versus.R;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * ???
 */
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
                .addOnSuccessListener(this, this::handleSuccessLogIn)
                .addOnFailureListener(this, this::handleFailedLogIn)
                .addOnCanceledListener(this, this::handleCanceledLogIn);
    }

    /**
     * ???
     * @param result
     */
    private void handleSuccessLogIn(AuthResult result){
        Log.d(TAG, "signInWithEmailAndPassword:success");
        startActivity(new Intent(LogInActivity.this, MainActivity.class));
    }

    /**
     * ???
     * @param exception
     */
    private void handleFailedLogIn(Exception exception){
        // If sign in fails, display a message to the user.
        Log.w(TAG, "signInWithEmailAndPassword:failure", exception);
        Toast.makeText(LogInActivity.this, "Authentication failed",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * ???
     */
    private void handleCanceledLogIn(){
        throw new RuntimeException("Not Implemented");
    }

    /**
     * ???
     * @param view
     */
    public void redirectToSignIn(View view){
        startActivity(new Intent(LogInActivity.this, SignInActivity.class));
    }

}