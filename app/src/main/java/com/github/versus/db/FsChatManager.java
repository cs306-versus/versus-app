package com.github.versus.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.versus.posts.Post;
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

import chats.Chat;
import chats.Message;

public class FsChatManager implements DataBaseManager<Chat>{
    private final FirebaseFirestore db;
    public static FsCollections CHATCOLLECTION = FsCollections.CHATS ;

    public FsChatManager(FirebaseFirestore db){
        this.db = db;
    }


    @Override
    public Future<Boolean> insert(Chat data) {
        //inserting the Chat in the Chat database
        DocumentReference docRef = db.collection(CHATCOLLECTION.toString()).document();
        Task<Void> task = docRef.set(data.getAllAttributes());

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
    //TODO: for now the id of the chat will just be a concatenation of the uids of the users, should be changed later to a better hash function
    public Future<Chat> fetch(String chatId) {
        //accessing the User Chat collection
        CollectionReference postsRef = db.collection(CHATCOLLECTION.toString());

        //finding the user with the right id
        Query query = postsRef.whereEqualTo("chatId", chatId);
        Task<QuerySnapshot> task = query.get();

        //Creating the CompletableFuture wrap that returns the Chat
        CompletableFuture<Chat> future = new CompletableFuture<>();

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

                Chat chat = (new ObjectMapper()).convertValue(doc.getData(), Chat.class);
                future.complete(chat);
            }
        }).addOnFailureListener(res ->{
            future.complete(null);
        });

        return future;
    }

    @Override
    public Future<Boolean> delete(String id) {

        //accessing the collection
        CollectionReference postsRef = db.collection(CHATCOLLECTION.toString());
        //finding the chat with the right id
        Query query = postsRef.whereEqualTo("chatId", id);
        Task<QuerySnapshot> task = query.get();

        // Wrap the Task in a CompletableFuture that returns status of deletion
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        // Add a listener to the Task to handle the result
        task.addOnSuccessListener(res -> {
            //we get the query result
            List<DocumentSnapshot> docs = res.getDocuments();
            if(docs.isEmpty()){
                //in case the query result is empty complete the future with true
                //because there was nothing to delete
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

    public Future<Boolean> addMessageToChat(String chatId, Message message) {
        //accessing the chat collection
        CollectionReference scheduleRef = db.collection(CHATCOLLECTION.toString());

        //finding the schedule with the right chatId
        Query query = scheduleRef.whereEqualTo("chatId", chatId);
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
                List<Message> messages = (List<Message>)doc.get("messages");

                //creating a new list corresponding to the old one + the new post
                List<Message> newMessages = new ArrayList<>(messages);
                newMessages.add(message);

                //updating the field value
                //if the update task is a success we complete the future with true
                //otherwise we complete the future with false
                doc.getReference().update("messages", newMessages).addOnSuccessListener(aVoid ->{
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
