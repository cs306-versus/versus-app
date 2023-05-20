package com.github.versus.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.versus.posts.Post;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    // ===================================== USERS FIELDS =========================================
    // ============================================================================================

    private final FirebaseFirestore db;

    /**
     * ???
     * @param db
     */
    public FsUserManager(FirebaseFirestore db){
        this.db = db;
    }

    @Override
    public Future<Boolean> insert(User user) {
        CollectionReference collection = db.collection(USERS_COLLECTION_ID);
        DocumentReference doc = collection.document(user.getUID());
        Map<String, Object> fields = new HashMap<>();


        // Update actual DB
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Task<Void> task = doc.set(fields);
        task.addOnSuccessListener(result -> future.complete(true))
                .addOnFailureListener(future::completeExceptionally)
                .addOnCanceledListener(() -> future.cancel(true));
        return future;
    }


    public Future<List<User>> fetchAll(String collectionName){
        //accessing the collection
        CollectionReference postsRef = db.collection(collectionName);
        //task that gets all documents
        Task<QuerySnapshot> task = postsRef.get();

        // Wrap the Task in a CompletableFuture that returns the posts
        CompletableFuture<List<User>> future = new CompletableFuture<>();

        // Add a listener to the Task to handle the result
        task.addOnSuccessListener(res -> {
            //we get the query result
            List<DocumentSnapshot> docs = res.getDocuments();
            //transforming the query result into a list odf posts
            List<User> users = new ArrayList<>();
            for (DocumentSnapshot doc: docs
            ) {
                //converting the data we get into an actual post object
                VersusUser.Builder builder = build(doc);
                //.setZipCode(content.get(ZIP_CODE_FIELD, int.class))
                //.setPreferredSports(new ArrayList<>());
                users.add(builder.build());
            }
            future.complete(users);

        }).addOnFailureListener(res ->{
            future.complete(null);
        });
        return future;
    }

    private VersusUser.Builder build(DocumentSnapshot doc){
        VersusUser.Builder builder = new VersusUser.Builder(doc.getId());
        return builder.setFirstName(doc.get(FIRST_NAME_FIELD, String.class))
                .setLastName(doc.get(LAST_NAME_FIELD, String.class))
                .setUserName(doc.get(USERNAME_FIELD, String.class))
                .setMail(doc.get(MAIL_FIELD, String.class))
                .setPhone(doc.get(PHONE_FIELD, String.class))
                // TODO HR : Fix the issue here,
                //  cannot deserialize field as was done before
                //.setRating(content.get(RATING_FIELD, int.class))
                .setCity(doc.get(CITY_FIELD, String.class));
    }

    @Override
    public Future<User> fetch(String uid) {
        CollectionReference collection = db.collection(USERS_COLLECTION_ID);
        CompletableFuture<User> future = new CompletableFuture<>();
        Task<DocumentSnapshot> doc = collection.document(uid).get();
        doc.addOnSuccessListener(content -> {
            VersusUser.Builder builder = build(content);
            future.complete(builder.build());
        })
        .addOnFailureListener(failure -> {
            future.cancel(true);
        })
        .addOnCanceledListener(() -> {
            future.cancel(true);
        });
        return future;
    }

    @Override
    public Future<Boolean> delete(String id) {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

}
