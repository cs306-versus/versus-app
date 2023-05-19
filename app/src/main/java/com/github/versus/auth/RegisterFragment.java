package com.github.versus.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.versus.databinding.FragmentRegisterBinding;
import com.github.versus.db.FsUserManager;
import com.github.versus.user.VersusUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

/**
 * ???
 */
public final class RegisterFragment extends BaseAuthFragment {

    private FragmentRegisterBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerLoginButton(binding.register);
    }

    @Override
    protected Task<AuthResult> requestAuthentication() {
        String mailText = binding.emailAddress.getText().toString();
        String pwdText = binding.password.getText().toString();
        Task<AuthResult> task = auth.createAccountWithMail(mailText, pwdText);
        task.addOnSuccessListener(result -> {
            String uid = result.getUser().getUid();
            VersusUser.Builder builder = new VersusUser.Builder(uid);
            // TODO HR : Link this when the UI is ready (see issue #58 in versus-app)
            builder.setFirstName("John")
                    .setLastName("Doe")
                    .setUserName("johndoe")
                    .setPhone("+41782345678")
                    .setMail("john.doe@versus.ch")
                    .setRating(3)
                    .setZipCode(0)
                    .setCity("Lausanne")
                    .setPreferredSports(List.of());
            new FsUserManager(FirebaseFirestore.getInstance()).insert(builder.build());
        });
        return task;
    }

    @Override
    protected void handleSuccessfulConnection(AuthResult result) {
        // TODO : Implement the successful result here
    }

    @Override
    protected void handleFailedConnection(Exception exception) {
        // TODO : Implement the successful result here
    }

    @Override
    protected void handleCancelledConnection() {
        // TODO : Implement the successful result here
    }
}