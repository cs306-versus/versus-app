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
import android.widget.Button;
import android.widget.Toast;

import com.github.versus.R;

/**
 * ???
 */
public class AuthFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button signin = view.findViewById(R.id.auth_signin);
        Button mail = view.findViewById(R.id.auth_login_mail);
        Button google = view.findViewById(R.id.auth_login_google);

        signin.setOnClickListener(this::signInRequest);
        mail.setOnClickListener(this::loginWithMailRequest);
        google.setOnClickListener(this::loginWithGoogleRequest);

    }

    /**
     * ???
     * @param view
     */
    private void loginWithGoogleRequest(View view) {
        Toast.makeText(getContext(), "loginWithGoogleRequest",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * ???
     * @param view
     */
    private void loginWithMailRequest(View view) {
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView, MailPasswordFragment.class, null);
        transaction.commit();
    }

    /**
     * ???
     * @param view
     */
    private void signInRequest(View view){
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView, SignInFragment.class, null);
        transaction.commit();
    }
}