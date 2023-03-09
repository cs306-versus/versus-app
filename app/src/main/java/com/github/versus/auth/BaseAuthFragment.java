package com.github.versus.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    protected FirebaseAuth auth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.auth = FirebaseAuth.getInstance();
    }

    /**
     * ???
     * @param view
     */
    protected final void tryToLogin(View view){
        Task<AuthResult> task = requestAuthentication();
        task.addOnSuccessListener(this::handleSuccessfulConnection);
        task.addOnFailureListener(this::handleFailedConnection);
        task.addOnCanceledListener(this::handleCancelledConnection);

    }

    /**
     * ???
     * @return
     */
    protected abstract Task<AuthResult> requestAuthentication();

    /**
     * ???
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
