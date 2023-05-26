package com.github.versus.db;

import com.github.versus.sports.Sport;
import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.versus.posts.Post;
import com.github.versus.rating.Rating;
import com.github.versus.sports.Sport;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private static final String FIRST_NAME_FIELD  = "first-name";
    private static final String LAST_NAME_FIELD   = "last-name";
    private static final String USERNAME_FIELD    = "username";

    private static final String FRIENDS_FIELD    = "friends";
    private static final String MAIL_FIELD        = "mail";
    private static final String PHONE_FIELD       = "phone";
    private static final String RATING_FIELD      = "rating";
    private static final String CITY_FIELD        = "city";
    private static final String ZIP_CODE_FIELD    = "zip";
    private static final String PREF_SPORTS_FIELD = "preferred-sports";
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
        Map<String, Object> fields = ((VersusUser)user).getAllAttributes();

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
        VersusUser.VersusBuilder builder = new VersusUser.VersusBuilder(doc.getId());
        return builder.setFriends((List<String>) doc.get("friends")).setFirstName(doc.get(FIRST_NAME_FIELD, String.class))
                .setLastName(doc.get(LAST_NAME_FIELD, String.class))
                .setUserName(doc.get(USERNAME_FIELD, String.class))
                .setMail(doc.get(MAIL_FIELD, String.class))
                .setPhone(doc.get(PHONE_FIELD, String.class))
                .setRating(Rating.DEFAULT_ELO )
                .setPreferredSports((List<Sport>)doc.get(PREF_SPORTS_FIELD))
                .setCity(doc.get(CITY_FIELD, String.class));

    }

    @Override
    public Future<User> fetch(String uid) {
        CollectionReference collection = db.collection(USERS_COLLECTION_ID);
        CompletableFuture<User> future = new CompletableFuture<>();
        Task<DocumentSnapshot> doc = collection.document(uid).get();
        doc.addOnSuccessListener(content -> {
            VersusUser.VersusBuilder builder = (VersusUser.VersusBuilder)build(content);
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

    public Future<Boolean> addFriend(String uid, String friendUID) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        CollectionReference collection = db.collection(USERS_COLLECTION_ID);
        DocumentReference doc = collection.document(uid);
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // Document exists, retrieve the old value of the players field
                    List<String> friends = (ArrayList<String>)(documentSnapshot.get("friends"));
                    friends.add(friendUID);
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("friends", friends);
                    doc.update(updates)
                            .addOnSuccessListener(aV -> future.complete(true) )
                            .addOnFailureListener(aV -> future.complete(false));

                } else {
                    future.complete(false);
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        future.complete(false);
                    }
                });
        return future;
    }

    public CompletableFuture<Boolean> createFriendship(String f1, String f2){
        CompletableFuture<Boolean> b1 = (CompletableFuture<Boolean>)addFriend(f1, f2);
        CompletableFuture<Boolean> b2= (CompletableFuture<Boolean>)addFriend(f2, f1);
        return b1.thenCombine(b2, (result1, result2) -> result1.booleanValue() && result2);

    }


    @Override
    public Future<Boolean> delete(String id) {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

}
