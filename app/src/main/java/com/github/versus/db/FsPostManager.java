package com.github.versus.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.versus.posts.Post;
import com.github.versus.user.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


public class FsPostManager implements DataBaseManager<Post> {

    //Collection representative Constants
    public static FsCollections POSTCOLLECTION = FsCollections.POSTS ;

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
        //inserting the post in the posts database
        DocumentReference docRef = db.collection(POSTCOLLECTION.toString()).document();
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
        //TODO :may have to change the value of the id into a combination of the title and
        // announcer or a hash of the post

        //accessing the collection
        CollectionReference postsRef = db.collection(POSTCOLLECTION.toString());
        //finding the post with the right id
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
                    post.setUid(doc.getId());
                    future.complete(post);
                }
             }).addOnFailureListener(res ->{
                future.complete(null);
        });
        
        return future;
    }


    public void update(String id, Post edits){
        db.collection("posts").document(id).update("title", edits.getTitle());
        db.collection("posts").document(id).update("date", edits.getDate());
        db.collection("posts").document(id).update("location", edits.getLocation());
        db.collection("posts").document(id).update("playerLimit", edits.getPlayerLimit());
        db.collection("posts").document(id).update("sport", edits.getSport());
    }

    /**
     *
     * @param collectionName : name of the collection from which we want to fetch elements
     * @return a future containing all Posts from teh given collection

     */
    public Future<List<Post>> fetchAll(String collectionName) {
        //TODO : may have to change the value of the id into a combination of the title and
        // announcer or a hash of the post

        //accessing the collection
        CollectionReference postsRef = db.collection(collectionName);
        //task that gets all documents
        Task<QuerySnapshot> task = postsRef.orderBy("date", Query.Direction.DESCENDING).get();

        // Wrap the Task in a CompletableFuture that returns the posts
        CompletableFuture<List<Post>> future = new CompletableFuture<>();

        // Add a listener to the Task to handle the result
        task.addOnSuccessListener(res -> {
            //we get the query result
            List<DocumentSnapshot> docs = res.getDocuments();
            //transforming the query result into a list odf posts
            List<Post> posts = new ArrayList<>();
            for (DocumentSnapshot doc: docs
            ) {
                //converting the data we get into an actual post object
                Post post = (new ObjectMapper()).convertValue(doc.getData(), Post.class);
                post.setUid(doc.getId());
                System.out.println(doc.get("players"));
                posts.add(post);
            }
            future.complete(posts);

        }).addOnFailureListener(res ->{
            future.complete(null);
        });
        return future;
    }

    @Override
    public Future<Boolean> delete(String id) {

        //accessing the collection
        CollectionReference postsRef = db.collection(POSTCOLLECTION.toString());
        //finding the post with the right id
        Query query = postsRef.whereEqualTo("title", id);
        Task<QuerySnapshot> task = query.get();

        // Wrap the Task in a CompletableFuture that returns status of deletion
        CompletableFuture<Boolean> compFuture = new CompletableFuture<>();

        task.addOnSuccessListener(res -> {
            List<DocumentSnapshot> schedules = res.getDocuments();
            if(schedules.isEmpty()){
                compFuture.complete(true);
            }else{
                //getting all the matching posts reference
                for (DocumentSnapshot doc: schedules
                     ) {
                    DocumentReference docRef = doc.getReference();
                    docRef.delete().addOnFailureListener(av ->{
                        compFuture.complete(false);
                    });
                }
                compFuture.complete(true);
            }
        }).addOnFailureListener(res ->{
            compFuture.complete(false);
        });

        return compFuture;
    }

    public Future<Boolean> joinPost(User user, String uid){
        //accessing the collection
        CollectionReference postsRef = db.collection(POSTCOLLECTION.toString());
        //finding the announcement with the right id
        Task<DocumentSnapshot> task = postsRef.document(uid).get();

        // Wrap the Task in a CompletableFuture that returns the status of the post join
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        //we complete the future with false if the query failed
        //otherwise we try to update the value of the players field
        task.addOnSuccessListener(res-> {

            //getting the documents corresponding to the post


            List<User> players = (List<User>)res.get("players");

            //getting the player limit
            long playerLimit = (long)res.get("playerLimit");

            //check that the limit isn't reached yet
            if(players.size() >= playerLimit){
                future.complete(false);
            }else{
                //creating a new list corresponding to the old one + the new user
                List<User> newPlayers = new ArrayList<>(players);

                newPlayers.add(user);

                //updating the field value
                //if the update task is a success we complete the future with true
                //otherwise we complete the future with false
                res.getReference().update("players", newPlayers).addOnSuccessListener(aVoid ->{

                    future.complete(true);
                }).addOnFailureListener(e ->{
                    future.complete(false);
                }
                );
            }

        }).addOnFailureListener(e -> {
            future.complete(false);
        });

        return future;
    }


    public Future<Boolean> leavePost(User user, String uid){
        //accessing the collection
        CollectionReference postsRef = db.collection(POSTCOLLECTION.toString());
        //finding the announcement with the right id
        Task<DocumentSnapshot> task = postsRef.document(uid).get();

        // Wrap the Task in a CompletableFuture that returns the status of the post join
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        //we complete the future with false if the query failed
        //otherwise we try to update the value of the players field
        task.addOnSuccessListener(res-> {

            //getting the documents corresponding to the post


            List<HashMap> players = (List<HashMap>) res.get("players");

            //getting the player limit

                //creating a new list corresponding to the old one - the user
            List<HashMap> newPlayers = new ArrayList<>();
            for(HashMap player : players){
                if(!player.get("uid").equals(user.getUID())){
                    newPlayers.add(player);
                }
            }

                System.out.println("REMOVING PLAYERS");

                //updating the field value
                //if the update task is a success we complete the future with true
                //otherwise we complete the future with false
                res.getReference().update("players", newPlayers).addOnSuccessListener(aVoid ->{
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

    /**
     *method used to filter posts based on the value of a certain attribute
     *
     * @param fieldName name of the field we want to base our query on
     * @param fieldValue value of the field we want to abse our query on
     * @return a future containing a list of posts that have the field name-value correspondence
     */
    public Future<List<Post>> fetchSpecific(String fieldName, Object fieldValue) {


        CollectionReference postsRef = db.collection(POSTCOLLECTION.toString());

        //finding the post with the right field name-value correspondence
        Query query = postsRef.whereEqualTo(fieldName, fieldValue);
        Task<QuerySnapshot> task = query.get();

        // Wrap the Task in a CompletableFuture that returns the posts having the correct
        // field-value match
        CompletableFuture<List<Post>> future = new CompletableFuture<>();

        // Add a listener to the Task link it to the completable future
        task.addOnSuccessListener(res -> {
            //we get the query result
            List<DocumentSnapshot> docs = res.getDocuments();
            //transforming the query result into a list of posts
            List<Post> filteredPosts = new ArrayList<>();
            for (DocumentSnapshot doc: docs
            ) {
                //converting the data we get into an actual Post object
                Post post = (new ObjectMapper()).convertValue(doc.getData(), Post.class);
                filteredPosts.add(post);
            }
            future.complete(filteredPosts);

        }).addOnFailureListener(res ->{
            future.complete(null);
        });

        return future;
    }
    public Future<Boolean> joinPost(String postId, User user){
        //accessing the collection
        CollectionReference postsRef = db.collection(POSTCOLLECTION.toString());
        //finding the announcement with the right id
        Query query = postsRef.whereEqualTo("title", postId);
        Task<QuerySnapshot> task = query.get();

        // Wrap the Task in a CompletableFuture that returns the status of the post join
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        //we complete the future with false if the query failed
        //otherwise we try to update the value of the players field
        task.addOnSuccessListener(doc -> {

            //getting the documents corresponding to the post
            List<DocumentSnapshot> docs = doc.getDocuments();
            if(docs.isEmpty()){
                future.complete(false);
            }else {
                List<User> newPlayers = new ArrayList<>();
                newPlayers.add(user);

                docs.get(0).getReference().update("players", newPlayers).addOnSuccessListener(aVoid ->{
                    future.complete(true);
                }).addOnFailureListener(e ->{
                            future.complete(false);
                        }
                );
            }


        }).addOnFailureListener(e -> {
            future.complete(false);
        });

        return future;
    }






}
