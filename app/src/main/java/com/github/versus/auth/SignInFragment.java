package com.github.versus.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.versus.MainActivity;
import com.github.versus.R;
import com.github.versus.databinding.AuthFragmentSigninBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * ???
 */
public final class SignInFragment extends Fragment {

    private AuthFragmentSigninBinding binding;

    /** Instance of the authenticator we use */
    private Authenticator auth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.auth = VersusAuthenticator.getInstance(FirebaseAuth.getInstance());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = AuthFragmentSigninBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.signin.setOnClickListener(this::loginWithPWD);
        binding.googleSignin.setOnClickListener(this::loginWithGoogleRequest);
    }

    /**
     * ???
     *
     * @param view
     */
    private void loginWithGoogleRequest(View view) {
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.auth_fragment_container, GoogleAuthFragment.class, null);
        transaction.commit();
    }

    private void loginWithPWD(View view){
        String mail = binding.mail.getText().toString();
        if(mail.isEmpty()){
            binding.emailOrPasswordWrong.setVisibility(View.VISIBLE);
            return;
        }
        String pwd = binding.pwd.getText().toString();
        if(pwd.isEmpty()){
            binding.emailOrPasswordWrong.setVisibility(View.VISIBLE);
            return;
        }
        Task<AuthResult> task = auth.signInWithMail(mail, pwd);
        // HR : if the connection was successful, move to the MainActivity
        task.addOnSuccessListener(res -> {
            if(res.getUser().isEmailVerified()){
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            } else {
                FragmentManager manager = getParentFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.auth_fragment_container, MailVerificationFragment.class, null);
                transaction.commit();
            }
        });
        // HR : if the connection failed
        task.addOnFailureListener(ex -> {
            binding.emailOrPasswordWrong.setVisibility(View.VISIBLE);
        });
        // HR : if the connection was cancelled
        task.addOnCanceledListener(() -> {
            //Toast.makeText(getActivity(),
            //        "Connection was cancelled for some reason",
            //        Toast.LENGTH_SHORT).show();
        });
    }

}