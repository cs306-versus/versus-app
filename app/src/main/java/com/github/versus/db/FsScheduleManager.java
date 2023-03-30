package com.github.versus.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.schedule.Schedule;
import com.github.versus.user.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class FsScheduleManager implements ScheduleManager {
    private final FirebaseFirestore db;
    public static FsCollections SCHEDULECOLLECTION = FsCollections.SCHEDULES ;

    public FsScheduleManager(FirebaseFirestore db){
        this.db = db;
    }

    @Override
    public CompletableFuture<Schedule> getSchedule(String UID){

        //accessing the User Schedule collection
        CollectionReference postsRef = db.collection(SCHEDULECOLLECTION.toString());

        //finding the user with the right id
        Query query = postsRef.whereEqualTo("UID", UID);
        Task<QuerySnapshot> task = query.get();

        //Creating the CompletableFuture wrap that returns the schedule
        CompletableFuture<Schedule> future = new CompletableFuture<>();

        // Add a listener to the Task to handle the result
        task.addOnSuccessListener(res -> {
            //we get the query result
            List<DocumentSnapshot> docs = res.getDocuments();
            if(docs.isEmpty()){
                //in case the query result is empty complete the future with null
                future.complete(null);
            }else{
                //if the query is not empty we assume that the first document has the correct user
                DocumentSnapshot doc = docs.get(0);
                //converting the data we get into an actual post object

                Schedule schedule = (new ObjectMapper()).convertValue(doc.getData(), Schedule.class);
                future.complete(schedule);
            }
        }).addOnFailureListener(res ->{
            future.complete(null);
        });

        return future;
    }

    @Override
    public CompletableFuture<Schedule> getScheduleStartingFromDate(String UID, Timestamp startingDate) {
        return getSchedule(UID).thenApply(s -> s == null ? null :  s.startingFromDate(startingDate));
    }


    public CompletableFuture<Schedule> getScheduleOnDate(String UID, Timestamp startingDate) {
        return getSchedule(UID).thenApply(s -> s == null ? null :  s.onDate(startingDate));
    }

    @Override
    public Future<Boolean> addScheduleToDatabase(String UID){
        //Creating the empty schedule
        Schedule emptySchedule = new Schedule(UID);
        //inserting the schedule in the Schedule database
        DocumentReference docRef = db.collection(SCHEDULECOLLECTION.toString()).document();
        Task<Void> task = docRef.set(emptySchedule.getAllAttributes());

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
    public Future<Boolean> addPostToSchedule(String UID, Post post) {
        //accessing the schedule collection
        CollectionReference postsRef = db.collection(SCHEDULECOLLECTION.toString());

        //finding the schedule with the right UID
        Query query = postsRef.whereEqualTo("UID", UID);
        Task<QuerySnapshot> task = query.get();

        // Wrap the Task in a CompletableFuture that returns the status of the schedule update
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        //we complete the future with false if the query failed
        //otherwise we try to update the value of the scheduled posts field
        task.addOnSuccessListener(res -> {

            //getting the documents corresponding to the post
            List<DocumentSnapshot> docs = res.getDocuments();
            if(docs.isEmpty()){
                future.complete(false);
            }else{
                DocumentSnapshot doc = docs.get(0);
                List<Post> scheduledPosts = (List<Post>)doc.get("posts");

                //creating a new list corresponding to the old one + the new post
                List<Post> newScheduledPosts = new ArrayList<>(scheduledPosts);
                newScheduledPosts.add(post);

                //updating the field value
                //if the update task is a success we complete the future with true
                //otherwise we complete the future with false
                doc.getReference().update("posts", newScheduledPosts).addOnSuccessListener(aVoid ->{
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
