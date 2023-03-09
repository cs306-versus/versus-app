package com.github.versus.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.versus.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MailPasswordFragment extends BaseAuthFragment {

    private EditText mail;
    private EditText pwd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mail_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mail = view.findViewById(R.id.auth_login_mail_mail);
        pwd = view.findViewById(R.id.auth_login_mail_password);
        registerLoginButton(view.findViewById(R.id.auth_login_mail_button));
    }

    @Override
    protected Task<AuthResult> requestAuthentication() {
        String mailText = mail.getText().toString();
        String pwdText = pwd.getText().toString();
        return auth.signInWithMail(mailText, pwdText);
    }

    /**
     * ???
     * @param result
     */
    protected void handleSuccessfulConnection(AuthResult result){
        // TODO : Implement the successful result here
        Toast.makeText(getContext(), "handleSuccessfulConnection",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * ???
     * @param exception
     */
    protected void handleFailedConnection(Exception exception){
        // TODO : Implement failed connection here
        Toast.makeText(getContext(), "handleFailedConnection",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * ???
     */
    protected void handleCancelledConnection(){
        // TODO : Implement cancelled connection here
        Toast.makeText(getContext(), "handleCancelledConnection",
                Toast.LENGTH_SHORT).show();
    }
}