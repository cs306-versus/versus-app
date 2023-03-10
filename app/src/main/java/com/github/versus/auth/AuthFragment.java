package com.github.versus.auth;

import android.content.Intent;
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

/**
 * ???
 */
public class AuthFragment extends Fragment {

    private FragmentAuthBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.authSignin.setOnClickListener(this::signInRequest);
        binding.authLoginMail.setOnClickListener(this::loginWithMailRequest);
        binding.authLoginGoogle.setOnClickListener(this::loginWithGoogleRequest);
    }

    /**
     * ???
     *
     * @param view
     */
    private void loginWithGoogleRequest(View view) {
        Toast.makeText(getContext(), "loginWithGoogleRequest",
                Toast.LENGTH_SHORT).show();
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView, GoogleAuthFragment.class, null);
        transaction.commit();
    }

    /**
     * ???
     *
     * @param view
     */
    private void loginWithMailRequest(View view) {
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView, MailPasswordFragment.class, null);
        transaction.commit();
    }

    /**
     * ???
     *
     * @param view
     */
    private void signInRequest(View view) {
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView, SignInFragment.class, null);
        transaction.commit();
    }

}