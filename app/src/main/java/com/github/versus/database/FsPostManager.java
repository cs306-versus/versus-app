package com.github.versus.database;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.versus.user.User;
import com.google.android.gms.tasks.*;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.*;
import java.util.concurrent.CompletableFuture;
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
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        task.addOnSuccessListener(res -> {
            completableFuture.complete(true);
        }).addOnFailureListener(e -> {
            completableFuture.complete(false);
        });

        return completableFuture;
    }



    @Override
    public Future<Post> fetch(String id) {
        //TODO : fetch implem is buggy, not required for this sprint, leave it for the next one
        // may have to change the value of the id into a combination of the title and
        // announcer or a hash of the post

        //accessing the collection
        CollectionReference postsRef = db.collection("posts");
        //finding the announcement with the right id
        Query query = postsRef.whereEqualTo("title", id);
        Task<QuerySnapshot> querySnapshotFuture = query.get();

        // Wrap the Task in a CompletableFuture that returns the post
        CompletableFuture<Post> future = new CompletableFuture<>();

        // Add a listener to the Task to handle the result
        querySnapshotFuture.addOnCompleteListener((res) -> {
                try {
                    QuerySnapshot querySnapshot = querySnapshotFuture.getResult();

                    // handle case where query result is empty
                    if (querySnapshot.isEmpty()) {
                        future.complete(null);
                        return;
                    }

                    // Create a Post instance from the document snapshot
                    DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                    Post post = (new ObjectMapper()).convertValue(documentSnapshot, Post.class) ;

                    // Complete the future with the MyObject instance
                    future.complete(post);

                } catch (Exception e) {
                    future.completeExceptionally(e);
                }
            });

        return future;
    }


    @Override
    public Future<Boolean> delete(String id) {
        return null;
    }

    public Future<Boolean> joinPost(String postId, User user){
        //accessing the collection
        CollectionReference postsRef = db.collection("posts");
        //finding the announcement with the right id
        Query query = postsRef.whereEqualTo("title", postId);
        Task<QuerySnapshot> task = query.get();

        // Wrap the Task in a CompletableFuture that returns the status of the insertion
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        //we complete the future with false if the query failed
        //otherwise we try to update the value of the players field
        task.addOnSuccessListener(res -> {
            //getting the document corresponding to the post
            DocumentSnapshot doc = res.getDocuments().get(0);
            List<User> players = (List<User>)doc.get("players");
            //creating a new list corresponding to the old one + the new user
            List<User> newPlayers = new ArrayList<>(players);
            newPlayers.add(user);
            //updating the field value
            //if the update task is a success we complete the future with true
            //otherwise we complete the future with false
            doc.getReference().update("players", newPlayers).addOnSuccessListener(aVoid ->{
                future.complete(true);
            }).addOnFailureListener(e ->{
                future.complete(false);
                    }
            );

        }).addOnFailureListener(e -> {
            future.complete(false);
        });


        return future;

    }
}
