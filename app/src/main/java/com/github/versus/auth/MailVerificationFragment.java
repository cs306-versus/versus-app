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
        // HR : Periodically check if the user has verified its mail
        // HR : this is a kick start
        while(!handler.post(new CheckMail()));
        return binding.getRoot();
    }

    private final class CheckMail implements Runnable {

        @Override
        public void run() {
            auth.reload();
            if(auth.hasValidMail()){
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
            else
                while(!handler.postDelayed(this, 1000));
        }
    }

}