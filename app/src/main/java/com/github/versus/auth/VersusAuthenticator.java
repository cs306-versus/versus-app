package com.github.versus.auth;

import static java.util.Objects.isNull;

import com.github.versus.db.FsScheduleManager;
import com.github.versus.db.FsUserManager;
import com.github.versus.schedule.Schedule;
import com.github.versus.user.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ???
 */
public final class VersusAuthenticator implements Authenticator {

    private static final AtomicReference<User> currentUser = new AtomicReference<>(null);
    private final FsUserManager user_manager;

    private final FsScheduleManager schedule_manager;

    /**
     *
     */
    private final FirebaseAuth auth;

    private VersusAuthenticator(FirebaseAuth auth) {
        this.auth = auth;
        this.user_manager = new FsUserManager(FirebaseFirestore.getInstance());
        this.schedule_manager = new FsScheduleManager(FirebaseFirestore.getInstance());
        if(auth.getCurrentUser() != null){
            ((CompletableFuture<User>)user_manager.fetch(auth.getUid())).thenAccept(currentUser::set);
        }
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
            String uid = result.getUser().getUid();
            // HR : Add user information to the database
            User user = builder.setUID(uid).build();
            user_manager.insert(user);
            currentUser.set(user);

            // HR : Add schedule document for the user
            schedule_manager.insert(new Schedule(uid));
        });
        return task;
    }

    @Override
    public Task<AuthResult> signInWithMail(String mail, String password) {
        // Request authentication from Firebase
        Task<AuthResult> task = auth.signInWithEmailAndPassword(mail, password);
        // Fill in the current user
        task.addOnSuccessListener(result -> {
            CompletableFuture<User> user = (CompletableFuture<User>) user_manager.fetch(result.getUser().getUid());
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

    @Override
    public boolean hasValidMail() {
        return currentUser() != null && auth.getCurrentUser().isEmailVerified();
    }

    @Override
    public void reload() {
        if(auth.getCurrentUser() != null)
            auth.getCurrentUser().reload();
    }
}
