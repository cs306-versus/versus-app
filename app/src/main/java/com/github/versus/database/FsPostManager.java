package com.github.versus.database;

import com.google.android.gms.tasks.*;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FsPostManager implements DataBaseManager<Post> {
    private final FirebaseFirestore db;
    /**
     * main constructor for Firestore Post Manager class
     *
     * @param db  : the firestore database
     */
    public FsPostManager(FirebaseFirestore db){
        this.db = db;
    }
    @Override
    public Future<Boolean> insert(Post post) {

        DocumentReference docRef = db.collection("posts").document();
        Task<Void> task = docRef.set(post.getAllAttributes());
        //TODO : change this future to something meaningfull once implem is sure to work
        FutureTask<Boolean> future = new FutureTask<>(() -> {
            return true;
         }
        );
        return future;

    }

    @Override
    public Future<Post> fetch(String id) {
        return null;
    }

    @Override
    public Future<Boolean> delete(String id) {
        return null;
    }
}
