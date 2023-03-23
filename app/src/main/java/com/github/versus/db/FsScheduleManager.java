package com.github.versus.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.user.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class FsScheduleManager implements ScheduleManager {
    private final FirebaseFirestore db;
    public FsScheduleManager(FirebaseFirestore db){
        this.db = db;
    }

    @Override
    public Future<List<Post>> getSchedule(String UID){

        //accessing the User Schedule collection
        CollectionReference postsRef = db.collection("User");

        //finding the user with the right id
        Query query = postsRef.whereEqualTo("UID", UID);
        Task<QuerySnapshot> task = query.get();

        //Creating the CompletableFuture wrap that returns the schedule
        CompletableFuture<List<Post>> future = new CompletableFuture<>();

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

                //TODO : this part should be completed ince we decide whether we store the schedule
                // directly in the user collection or in another collection
                User user = (new ObjectMapper()).convertValue(doc.getData(), User.class);
                //future.complete(user.getSchedule());
                future.complete(null);
            }
        }).addOnFailureListener(res ->{
            future.complete(null);
        });

        return future;
    }

    @Override
    public Future<List<Post>> getScheduleStartingFromDate(User user, Timestamp startingDate) {
        return null;
    }


}
