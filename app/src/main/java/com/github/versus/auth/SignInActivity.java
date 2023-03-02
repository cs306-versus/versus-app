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

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignIn";

    private FirebaseAuth mAuth;

    private EditText mail;
    private EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
    }

    /**
     * Initialize UI Component references
     */
    private void init(){
        mAuth = FirebaseAuth.getInstance();
        mail = findViewById(R.id.mail);
        pwd = findViewById(R.id.pwd);
    }

    /**
     * ???
     * @param view
     */
    public void attemptAccountCreation(View view){
        String m = mail.getText().toString();
        String p = pwd.getText().toString();
        mAuth.createUserWithEmailAndPassword(m, p)
                .addOnSuccessListener(this, this::handleSuccessCreation)
                .addOnFailureListener(this, this::handleFailedCreation)
                .addOnCanceledListener(this, this::handleCanceledCreation);
    }

    /**
     * ???
     * @param result
     */
    private void handleSuccessCreation(AuthResult result){
        // Sign in success, update UI with the signed-in user's information
        Log.d(TAG, "createUserWithEmail:success");
        startActivity(new Intent(this, MainActivity.class));
    }

    /**
     * ???
     * @param exception
     */
    private void handleFailedCreation(Exception exception){
        Log.w(TAG, "createUserWithEmail:failure", exception);
        Toast toast = Toast.makeText(
                SignInActivity.this,
                "Authentication failed",
                Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * ???
     */
    private void handleCanceledCreation(){
        throw new RuntimeException("Not Implemented");
    }

}