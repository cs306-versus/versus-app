package com.github.versus.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.versus.R;
import com.github.versus.databinding.FragmentAuthBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * ???
 */
public class SignInFragment extends BaseAuthFragment {

    private FragmentAuthBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.authSignin.setOnClickListener(this::signInRequest);
        registerLoginButton(binding.authLoginMail);
        binding.authLoginGoogle.setOnClickListener(this::loginWithGoogleRequest);
    }

    /**
     * ???
     *
     * @param view
     */
    private void loginWithGoogleRequest(View view) {
        switchTo(GoogleAuthFragment.class);
    }

    /**
     * ???
     *
     * @param view
     */
    private void signInRequest(View view) {
        switchTo(RegisterFragment.class);
    }

    private void switchTo(Class<? extends Fragment> clz){
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView, clz, null);
        transaction.commit();
    }

    @Override
    protected Task<AuthResult> requestAuthentication() {
        String mailText = binding.authLoginMailMail.getText().toString();
        String pwdText = binding.authLoginMailPwd.getText().toString();
        return auth.signInWithMail(mailText, pwdText);
    }

    @Override
    protected void handleSuccessfulConnection(AuthResult result) {

    }

    @Override
    protected void handleFailedConnection(Exception exception) {
        Toast.makeText(getContext(), "handleFailedConnection",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void handleCancelledConnection() {
        Toast.makeText(getContext(), "handleCancelledConnection",
                Toast.LENGTH_SHORT).show();
    }
}