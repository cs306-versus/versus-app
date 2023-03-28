package com.github.versus.db;

import com.github.versus.user.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * DataBase Manager to handle {@link User} information.
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @since SPRINT 2
 */
public class FsUserManager implements DataBaseManager<User> {

    private static final String USERS_COLLECTION_ID = "users";

    private final FirebaseFirestore db;

    /**
     * ???
     * @param db
     */
    public FsUserManager(FirebaseFirestore db){
        this.db = db;
    }

    @Override
    public Future<Boolean> insert(User data) {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    @Override
    public Future<User> fetch(String uid) {
        CollectionReference collection = db.collection(USERS_COLLECTION_ID);
        CompletableFuture<User> future = new CompletableFuture<>();
        Task<DocumentSnapshot> doc = collection.document(uid).get();
        doc.addOnSuccessListener(content -> {
            System.out.println(content.getData());
            // ???
        });
        doc.addOnFailureListener(failure -> {
            // ???
        });
        doc.addOnCanceledListener(() -> {
            // ???
        });
        return future;
    }

    @Override
    public Future<Boolean> delete(String id) {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

}
