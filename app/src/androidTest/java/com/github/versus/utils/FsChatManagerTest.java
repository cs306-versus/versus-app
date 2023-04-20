package com.github.versus.utils;

import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.db.FsChatManager;
import com.github.versus.db.FsPostManager;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.sports.Sport;
import com.github.versus.user.DummyUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Month;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import chats.Chat;

@RunWith(AndroidJUnit4.class)

public class FsChatManagerTest {

    @Test
    public void CorrectFsPostInsert_fetch() throws ExecutionException, InterruptedException, TimeoutException
    {
        // Creating FsPostm instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FsChatManager postm = new FsChatManager(db);

        // Creating a test post
        Chat chat = new Chat(new DummyUser("abdess"), new DummyUser("aymane"), "abdessaymane" );

        //inserting the post
        Future<Boolean> insertResult = postm.insert(chat);

        // Wait for the insert operation to complete
        boolean insertSuccess = insertResult.get();

        // Verify that the insert operation succeeded
        assertTrue(insertSuccess);

        // Verify that the chat was inserted into Firestore
        //by fetching it and then comparing
        Chat c = postm.fetch(chat.getChatId()).get();
        assertTrue(c.equals(chat));

        // Clean up the test data
        boolean deletionSuccess = postm.delete("_test_").get();
        assertTrue(deletionSuccess);
    }



}
