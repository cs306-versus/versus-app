package com.github.versus.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.versus.R;
import com.github.versus.auth.VersusAuthenticator;
import com.github.versus.databinding.FragmentUserInfoBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.CompletableFuture;

/**
 * ???
 */
public class UserInfoFragment extends Fragment {

    private FragmentUserInfoBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((CompletableFuture<User>)VersusAuthenticator.getInstance(FirebaseAuth.getInstance()).currentUser())
                .thenAccept(this::updateUI);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserInfoBinding.inflate(inflater);
        return binding.getRoot();
    }

    /**
     * ???
     * @param user
     */
    private void updateUI(User user){
        Log.println(Log.ERROR, "", user.toString());
    }

}