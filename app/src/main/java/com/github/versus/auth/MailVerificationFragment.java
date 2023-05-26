package com.github.versus.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.versus.MainActivity;
import com.github.versus.R;
import com.github.versus.databinding.AuthFragmentMailVerificationBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.atomic.AtomicBoolean;

public class MailVerificationFragment extends Fragment {

    private AuthFragmentMailVerificationBinding binding;
    private final Handler handler = new Handler();

    private final VersusAuthenticator auth = VersusAuthenticator.getInstance(FirebaseAuth.getInstance());

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