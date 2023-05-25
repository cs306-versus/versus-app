package com.github.versus.user;

import static java.util.Objects.isNull;

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
import com.github.versus.db.FsUserManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.CompletableFuture;

/**
 * ???
 */
public class UserInfoFragment extends Fragment {

    private FragmentUserInfoBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FsUserManager db = new FsUserManager(FirebaseFirestore.getInstance());
        ((CompletableFuture<User>)db.fetch(FirebaseAuth.getInstance().getUid()))
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
    public void updateUI(User user){

        if(isNull(user))
            return;
        binding.infoUid.setText(user.getUID());
        binding.infoFirstName.setText(user.getFirstName());
        binding.infoLastName.setText(user.getLastName());
        binding.infoUsername.setText(user.getUserName());
        binding.infoMail.setText(user.getMail());
        binding.infoPhone.setText(user.getPhone());
        binding.infoRating.setText(Integer.toString(user.getRating()));
        binding.infoCity.setText(user.getCity());
    }

}