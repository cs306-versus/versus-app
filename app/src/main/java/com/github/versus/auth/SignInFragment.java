package com.github.versus.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.versus.R;
import com.github.versus.databinding.FragmentSignInBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * ???
 */
public class SignInFragment extends BaseAuthFragment {

    private FragmentSignInBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerLoginButton(binding.authSigninButton);
    }

    @Override
    protected Task<AuthResult> requestAuthentication() {
        String mailText = binding.authSigninMail.getText().toString();
        String pwdText = binding.authSigninPwd.getText().toString();
        return auth.createAccountWithMail(mailText, pwdText);
    }

    @Override
    protected void handleSuccessfulConnection(AuthResult result) {
        // TODO : Implement the successful result here
        Toast.makeText(getActivity(), "handleSuccessfulConnection",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void handleFailedConnection(Exception exception) {
        // TODO : Implement the successful result here
        Toast.makeText(getActivity(), R.string.failure,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void handleCancelledConnection() {
        // TODO : Implement the successful result here
        Toast.makeText(getActivity(), "handleCancelledConnection",
                Toast.LENGTH_SHORT).show();
    }
}