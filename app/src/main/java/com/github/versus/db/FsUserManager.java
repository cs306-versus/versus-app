package com.github.versus.db;

import com.github.versus.user.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.Future;

/**
 * DataBase Manager to handle {@link User} information.
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @since SPRINT 2
 */
public class FsUserManager implements DataBaseManager<User> {

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
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    @Override
    public Future<Boolean> delete(String id) {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

}
