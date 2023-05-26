package com.github.versus.auth;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.versus.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleAuthFragment extends Fragment {

    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;

    private FirebaseAuth mAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        oneTapClient = Identity.getSignInClient(getContext());
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();

        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener((OnSuccessListener<BeginSignInResult>) result -> {
                    try {
                        startIntentSenderForResult(result.getPendingIntent().getIntentSender(), 10, null, 0, 0, 0, null);
                    } catch (IntentSender.SendIntentException e) {
                        Log.e("TAG", "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                    }
                })
                .addOnFailureListener(e -> {
                        // No saved credentials found. Launch the One Tap sign-up flow, or
                        // do nothing and continue presenting the signed-out UI.
                        Log.d("TAG", e.getLocalizedMessage());
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            SignInCredential googleCredential = oneTapClient.getSignInCredentialFromIntent(data);
            String idToken = googleCredential.getGoogleIdToken();
            if (idToken !=  null) {
                // Got an ID token from Google. Use it to authenticate
                // with Firebase.
                AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
                mAuth.signInWithCredential(firebaseCredential)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Log.d("gf", user.toString());
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "signInWithCredential:failure", task.getException());
                            }
                        });

            }
        } catch (ApiException e) {
            // ...
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.auth_fragment_google, container, false);
    }
}