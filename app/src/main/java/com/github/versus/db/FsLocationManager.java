package com.github.versus.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.versus.posts.Location;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class FsLocationManager implements DataBaseManager<Location>{

    private final FirebaseFirestore db;

    public FsLocationManager(FirebaseFirestore db){
        this.db = db;
    }

    public static FsCollections LOCATIONCOLLECTION = FsCollections.LOCATIONS ;

    @Override
    public Future<Boolean> insert(Location location) {
        //inserting the location in the Location database
        DocumentReference docRef = db.collection(LOCATIONCOLLECTION.toString()).document();
        Task<Void> task = docRef.set(location.getAllAttributes());

        // Wrap the Task in a CompletableFuture that returns the status of the insertion of the location
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();

        task.addOnSuccessListener(res -> {
            completableFuture.complete(true);
        }).addOnFailureListener(e -> {
            completableFuture.complete(false);
        });

        return completableFuture;
    }

    @Override
    public Future<Location> fetch(String id) {

        //accessing the user location
        CollectionReference postsRef = db.collection(LOCATIONCOLLECTION.toString());

        //finding the location with the right id
        Query query = postsRef.whereEqualTo("name", id);
        Task<QuerySnapshot> task = query.get();

        //Creating the CompletableFuture wrap that returns the location
        CompletableFuture<Location> future = new CompletableFuture<>();

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

                Location location = (new ObjectMapper()).convertValue(doc.getData(), Location.class);
                future.complete(location);
            }
        }).addOnFailureListener(res ->{
            future.complete(null);
        });

        return future;
    }

    @Override
    public Future<Boolean> delete(String id) {
        //accessing the location collection
        CollectionReference postsRef = db.collection(LOCATIONCOLLECTION.toString());
        //finding the location with the right id
        Query query = postsRef.whereEqualTo("name", id);
        Task<QuerySnapshot> task = query.get();

        // Wrap the Task in a CompletableFuture that returns status of deletion
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        // Add a listener to the Task to handle the result
        task.addOnSuccessListener(res -> {
            //we get the query result
            List<DocumentSnapshot> docs = res.getDocuments();
            if(docs.isEmpty()){
                //in case the query result is empty complete the future with true
                //because no deletion was needed delete
                future.complete(true);
            }else{
                //getting all the matching posts reference
                for (DocumentSnapshot doc: docs
                ) {
                    DocumentReference docRef = doc.getReference();
                    //deleting the document
                    docRef.delete().addOnFailureListener(av ->{
                        future.complete(false);
                    });
                }
                future.complete(true);
            }
        }).addOnFailureListener(res ->{
            future.complete(false);
        });

        return future;
    }
}
