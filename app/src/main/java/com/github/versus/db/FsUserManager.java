package com.github.versus.db;

import com.github.versus.sports.Sport;
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
    private static final String FIRST_NAME_FIELD  = "first-name";
    private static final String LAST_NAME_FIELD   = "last-name";
    private static final String USERNAME_FIELD    = "username";
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
        Map<String, Object> fields = new HashMap<>();

        // Add All fields
        fields.put(FIRST_NAME_FIELD, user.getFirstName());
        fields.put(LAST_NAME_FIELD, user.getLastName());
        fields.put(USERNAME_FIELD, user.getUserName());
        fields.put(MAIL_FIELD, user.getMail());
        fields.put(PHONE_FIELD, user.getPhone());
        fields.put(RATING_FIELD, user.getRating());
        fields.put(CITY_FIELD, user.getCity());
        fields.put(ZIP_CODE_FIELD, user.getZipCode());
        fields.put(PREF_SPORTS_FIELD, user.getPreferredSports());

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
        VersusUser.Builder builder = new VersusUser.VersusBuilder(doc.getId());
        return builder.setFirstName(doc.get(FIRST_NAME_FIELD, String.class))
                .setLastName(doc.get(LAST_NAME_FIELD, String.class))
                .setUserName(doc.get(USERNAME_FIELD, String.class))
                .setMail(doc.get(MAIL_FIELD, String.class))
                .setPhone(doc.get(PHONE_FIELD, String.class))
                .setRating(doc.get(RATING_FIELD) == null ?  2000 : Long.parseLong(doc.get(RATING_FIELD)))
                .setPreferredSports((List<Sport>)doc.get(PREF_SPORTS_FIELD))

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
