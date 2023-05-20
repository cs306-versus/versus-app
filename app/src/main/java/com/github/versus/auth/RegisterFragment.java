package com.github.versus.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.versus.MainActivity;
import com.github.versus.R;
import com.github.versus.databinding.AuthFragmentRegisterBinding;
import com.github.versus.user.VersusUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * ???
 */
public final class RegisterFragment extends Fragment {

    private AuthFragmentRegisterBinding binding;
    /** Instance of the authenticator we use */
    private Authenticator auth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.auth = VersusAuthenticator.getInstance(FirebaseAuth.getInstance());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = AuthFragmentRegisterBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.registerBtn.setOnClickListener(this::createAccount);
    }

    private void createAccount(View view) {
        // HR : fetch the mail
        String mail = binding.mail.getText().toString();
        // HR : Fetch the pwd
        String pwd = binding.pwd.getText().toString();
        String pwd_confirmation = binding.confirmPwd.getText().toString();
        if (!pwd.equals(pwd_confirmation)) {
            binding.pwd.getBackground().setState(new int[]{R.attr.pwd_state});
        }
        VersusUser.VersusBuilder builder = new VersusUser.VersusBuilder(null);
        // TODO HR : Link this when the UI is ready (see issue #58 in versus-app)
        builder.setFirstName(binding.firstName.getText().toString())
                .setLastName(binding.lastName.getText().toString())
                .setUserName("johndoe")
                .setPhone(binding.phone.getText().toString())
                .setMail(mail)
                .setRating(3)
                .setZipCode(0)
                .setCity("Lausanne")
                .setPreferredSports(List.of());

        // Request from firebase
        Task<AuthResult> task = auth.createAccountWithMail(mail, pwd, builder);
        Log.d("TAG", "account creation started");
        task.addOnSuccessListener(res -> {
            startActivity(new Intent(getContext(), MainActivity.class));
            getActivity().finish();
            Log.d("TAG", "account creation successful");
        });
        // HR : if the connection failed
        task.addOnFailureListener(ex -> {
            Log.d("TAG", "account creation failed");
        });
        // HR : if the connection was cancelled
        task.addOnCanceledListener(() -> {
            Log.d("TAG", "account creation cancelled");
        });
    }

}