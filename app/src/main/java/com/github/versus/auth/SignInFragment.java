package com.github.versus.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.github.versus.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * ???
 */
public class SignInFragment extends BaseAuthFragment {

    private EditText name;
    private EditText mail;
    private EditText pwd;
    private EditText phone;
    private EditText zipCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.auth_signin_name);
        mail = view.findViewById(R.id.auth_signin_mail);
        pwd = view.findViewById(R.id.auth_signin_pwd);
        phone = view.findViewById(R.id.auth_signin_phone);
        zipCode = view.findViewById(R.id.auth_signin_zip_code);

        registerLoginButton(view.findViewById(R.id.auth_signin_button));
    }

    @Override
    protected Task<AuthResult> requestAuthentication() {
        String mailText = mail.getText().toString();
        String pwdText = pwd.getText().toString();
        return auth.createAccountWithMail(mailText, pwdText);
    }

    @Override
    protected void handleSuccessfulConnection(AuthResult result) {
        // TODO : Implement the successful result here
        Toast.makeText(getContext(), "handleSuccessfulConnection",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void handleFailedConnection(Exception exception) {
        // TODO : Implement the successful result here
        Toast.makeText(getContext(), "handleFailedConnection",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void handleCancelledConnection() {
        // TODO : Implement the successful result here
        Toast.makeText(getContext(), "handleCancelledConnection",
                Toast.LENGTH_SHORT).show();
    }
}