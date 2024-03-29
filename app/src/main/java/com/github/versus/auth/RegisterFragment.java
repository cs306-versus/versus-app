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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.versus.MainActivity;
import com.github.versus.R;
import com.github.versus.databinding.AuthFragmentRegisterBinding;
import com.github.versus.db.FsPostManager;
import com.github.versus.db.FsScheduleManager;
import com.github.versus.db.FsUserManager;
import com.github.versus.rating.Rating;
import com.github.versus.schedule.Schedule;
import com.github.versus.user.VersusUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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
        String mail = binding.mail.getText().toString(); // HR : fetch the mail
        String pwd = binding.pwd.getText().toString(); // HR : Fetch the pwd
        String pwd_confirmation = binding.confirmPwd.getText().toString();
        String phone = binding.phone.getText().toString();
        String firstName = binding.firstName.getText().toString();
        String lastName = binding.lastName.getText().toString();
        if (!pwd.equals(pwd_confirmation)) {
            binding.passwordsAreNotSimilar.setVisibility(View.VISIBLE);
            return;
        }
        VersusUser.VersusBuilder builder = new VersusUser.VersusBuilder(FirebaseAuth.getInstance().getUid());
        // TODO HR : Link this when the UI is ready (see issue #58 in versus-app)
        ( (VersusUser.VersusBuilder)(builder.setFirstName(firstName)
                .setLastName(lastName)
                .setUserName(String.format("%s-%s", firstName, lastName).toLowerCase())
                .setPhone(phone)
                .setMail(mail)
                .setRating(Rating.DEFAULT_ELO)
                .setZipCode(0) // TODO HR : This is still hardcoded
                .setCity("Lausanne") // TODO HR : This is still hardcoded
                .setPreferredSports(List.of())))
                .setFriends(List.of());


        // Request from firebase
        Task<AuthResult> task = auth.createAccountWithMail(mail, pwd, builder);
        Log.d("TAG", "account creation started");
        task.addOnSuccessListener(res -> {
            // HR : Send mail
            res.getUser().sendEmailVerification();
            FragmentManager manager = getParentFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.auth_fragment_container, MailVerificationFragment.class, null);
            transaction.commit();
            Log.d(this.getClass().getName(), "account creation successful but mail not validated");
        });
        // HR : if the connection failed
        task.addOnFailureListener(ex -> {
            binding.mailAlreadyUsed.setVisibility(View.VISIBLE);
            Log.d("TAG", ex.toString());
        });
        // HR : if the connection was cancelled
        task.addOnCanceledListener(() -> {
            Log.d("TAG", "account creation cancelled");
        });
    }

}