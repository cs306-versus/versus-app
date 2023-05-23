package com.github.versus.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.versus.R;
import com.github.versus.databinding.AuthFragmentMailVerificationBinding;

public class MailVerificationFragment extends Fragment {

    private AuthFragmentMailVerificationBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = AuthFragmentMailVerificationBinding.inflate(inflater);
        return binding.getRoot();
    }

}