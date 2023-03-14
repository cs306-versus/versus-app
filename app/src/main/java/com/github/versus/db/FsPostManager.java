package com.github.versus.db;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.versus.posts.Post;
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

        // Wrap the Task in a CompletableFuture that returns the status of the insertion
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
        Task<QuerySnapshot> task = query.get();

        // Wrap the Task in a CompletableFuture that returns the post
        CompletableFuture<Post> future = new CompletableFuture<>();

        // Add a listener to the Task to handle the result
        task.addOnSuccessListener(res -> {
                //we get the query result
                List<DocumentSnapshot> docs = res.getDocuments();
                if(docs.isEmpty()){
                    //in case the query result is empty complete the future with null
                    future.complete(null);
                }else{
                    DocumentSnapshot doc = docs.get(0);
                    //converting the data we get into an actual post object
                    Post post = (new ObjectMapper()).convertValue(doc.getData(), Post.class);
                    future.complete(post);
                }
             }).addOnFailureListener(res ->{
                future.complete(null);
        });
        
        return future;
    }

    /**
     *
     * @return
     */
    public Future<List<Post>> fetchAll() {
        //TODO : may have to change the value of the id into a combination of the title and
        // announcer or a hash of the post

        //accessing the collection
        CollectionReference postsRef = db.collection("posts");
        //task that gets all documents
        Task<QuerySnapshot> task = postsRef.get();

        // Wrap the Task in a CompletableFuture that returns the posts
        CompletableFuture<List<Post>> future = new CompletableFuture<>();

        // Add a listener to the Task to handle the result
        task.addOnSuccessListener(res -> {
                //we get the query result
                List<DocumentSnapshot> docs = res.getDocuments();
                if(docs.isEmpty()){
                    //in case the query result is empty complete the future with null
                    future.complete(new ArrayList<>());
                }else{
                    List<Post> posts = new ArrayList<>();
                    for (DocumentSnapshot doc: res
                         ) {
                        //converting the data we get into an actual post object
                        Post post = (new ObjectMapper()).convertValue(doc.getData(), Post.class);
                        posts.add(post);
                    }

                    future.complete(posts);
                }
             }).addOnFailureListener(res ->{
                future.complete(null);
        });
        return future;
    }

    @Override
    public Future<Boolean> delete(String id) {
        //TODO: not needed for this sprint, implement in the next one
        return null;
    }

    public Future<Boolean> joinPost(String postId, User user){
        //accessing the collection
        CollectionReference postsRef = db.collection("posts");
        //finding the announcement with the right id
        Query query = postsRef.whereEqualTo("title", postId);
        Task<QuerySnapshot> task = query.get();

        // Wrap the Task in a CompletableFuture that returns the status of the post join
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
