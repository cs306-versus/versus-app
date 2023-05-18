package com.github.versus.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.versus.R;
import com.github.versus.databinding.FragmentAuthBinding;
import com.github.versus.databinding.FragmentSignInBinding;

public class AuthFragment extends Fragment {

    private FragmentAuthBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(inflater);
        return binding.getRoot();
    }
}