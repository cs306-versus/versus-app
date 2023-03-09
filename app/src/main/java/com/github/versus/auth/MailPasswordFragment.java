package com.github.versus.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.versus.databinding.FragmentMailPasswordBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class MailPasswordFragment extends BaseAuthFragment {

    private FragmentMailPasswordBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMailPasswordBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerLoginButton(binding.authLoginMailButton);
    }

    @Override
    protected Task<AuthResult> requestAuthentication() {
        String mailText = binding.authLoginMailMail.getText().toString();
        String pwdText = binding.authLoginMailPassword.getText().toString();
        return auth.signInWithMail(mailText, pwdText);
    }

    /**
     * ???
     *
     * @param result
     */
    protected void handleSuccessfulConnection(AuthResult result) {
        // TODO : Implement the successful result here
        Toast.makeText(getContext(), "handleSuccessfulConnection",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * ???
     *
     * @param exception
     */
    protected void handleFailedConnection(Exception exception) {
        // TODO : Implement failed connection here
        Toast.makeText(getContext(), "handleFailedConnection",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * ???
     */
    protected void handleCancelledConnection() {
        // TODO : Implement cancelled connection here
        Toast.makeText(getContext(), "handleCancelledConnection",
                Toast.LENGTH_SHORT).show();
    }
}