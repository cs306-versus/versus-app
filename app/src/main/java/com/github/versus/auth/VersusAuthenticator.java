package com.github.versus.auth;

import static java.util.Objects.isNull;

import com.github.versus.db.FsUserManager;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ???
 */
public final class VersusAuthenticator implements Authenticator {

    private final AtomicReference<User> currentUser = new AtomicReference<>(null);
    private final FsUserManager manager;

    /**
     *
     */
    private final FirebaseAuth auth;

    private VersusAuthenticator(FirebaseAuth auth) {
        this.auth = auth;
        this.manager = new FsUserManager(FirebaseFirestore.getInstance());
    }

    /**
     * ???
     *
     * @return
     */
    public static VersusAuthenticator getInstance(FirebaseAuth auth) {
        return new VersusAuthenticator(auth);
    }

    @Override
    public Task<AuthResult> createAccountWithMail(String mail, String password, User.Builder builder) {
        // Request authentication from Firebase
        Task<AuthResult> task = auth.createUserWithEmailAndPassword(mail, password);
        // Fill in the current user
        task.addOnSuccessListener(result -> {
            User user = builder.setUID(result.getUser().getUid()).build();
            manager.insert(user);
            currentUser.set(user);
        });
        return task;
    }

    @Override
    public Task<AuthResult> signInWithMail(String mail, String password) {
        // Request authentication from Firebase
        Task<AuthResult> task = auth.signInWithEmailAndPassword(mail, password);
        // Fill in the current user
        task.addOnSuccessListener(result -> {
            CompletableFuture<User> user = (CompletableFuture<User>) manager.fetch(result.getUser().getUid());
            user.thenAccept(currentUser::set);
        });
        return task;
    }

    @Override
    public User currentUser() {
        // Check firebase for cached user credentials
        FirebaseUser firebase_user = auth.getCurrentUser();
        // TODO HR : Still need to build the correct user
        return isNull(firebase_user) ? null : currentUser.get();
    }

    @Override
    public void signOut() {
        auth.signOut();
        currentUser.set(null);
    }
}
