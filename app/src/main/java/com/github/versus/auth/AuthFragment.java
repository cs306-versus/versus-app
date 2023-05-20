package com.github.versus.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.versus.R;
import com.github.versus.databinding.AuthFragmentBinding;

/**
 * Entry point of the authentication process
 * It welcomes the user and request from it to either
 * login or register
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 */
public final class AuthFragment extends Fragment {

    private AuthFragmentBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AuthFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.registerBtn.setOnClickListener(v -> switchTo(RegisterFragment.class));
        binding.signinBtn.setOnClickListener(v -> switchTo(SignInFragment.class));
    }

    private void switchTo(Class<? extends Fragment> clz){
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView, clz, null);
        transaction.commit();
    }

}