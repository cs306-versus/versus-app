package com.github.versus.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.versus.MainActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 *
 */
public abstract class BaseAuthFragment extends Fragment {

    /**
     * Instance of the authenticator we use
     */
    protected Authenticator auth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.auth = VersusAuthenticator.getInstance();
    }

    /**
     * ???
     * @param button
     */
    protected final void registerLoginButton(Button button){
        button.setOnClickListener(this::tryToLogin);
    }

    /**
     * ???
     * @param view
     */
    private void tryToLogin(View view){
        Task<AuthResult> task = requestAuthentication();
        task.addOnSuccessListener(this::onSuccessfulLogin);
        task.addOnFailureListener(this::handleFailedConnection);
        task.addOnCanceledListener(this::handleCancelledConnection);

    }

    private void onSuccessfulLogin(AuthResult result){
        handleSuccessfulConnection(result);
        // Switch to the MainActivity
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    /**
     * ???
     * @return
     */
    protected abstract Task<AuthResult> requestAuthentication();

    /**
     * ???
     * NO NEED TO SWITCH TO THE MAIN ACTIVITY
     * @param result
     */
    protected abstract void handleSuccessfulConnection(AuthResult result);

    /**
     * ???
     * @param exception
     */
    protected abstract void handleFailedConnection(Exception exception);

    /**
     * ???
     */
    protected abstract void handleCancelledConnection();

}
