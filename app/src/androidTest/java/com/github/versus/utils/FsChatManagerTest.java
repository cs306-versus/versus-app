package com.github.versus.utils;

import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.db.FsChatManager;
import com.github.versus.db.FsPostManager;
import com.github.versus.db.FsScheduleManager;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.schedule.Schedule;
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
import chats.Message;

@RunWith(AndroidJUnit4.class)

public class FsChatManagerTest {

    @Test
    public void CorrectFsChatInsert_fetch() throws ExecutionException, InterruptedException, TimeoutException
    {
        // Creating FsPostm instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FsChatManager chat_m = new FsChatManager(db);

        // Creating a test post
        Chat chat = new Chat(new DummyUser("abdess"), new DummyUser("aymane"), "abdessaymane" );

        //inserting the post
        Future<Boolean> insertResult = chat_m.insert(chat);

        // Wait for the insert operation to complete
        boolean insertSuccess = insertResult.get();

        // Verify that the insert operation succeeded
        assertTrue(insertSuccess);

        // Verify that the chat was inserted into Firestore
        //by fetching it and then comparing
        Chat c = chat_m.fetch(chat.getChatId()).get();
        assertTrue(c.equals(chat));

        // Clean up the test data
        boolean deletionSuccess = chat_m.delete(c.getChatId()).get();
        assertTrue(deletionSuccess);
    }

    @Test
    public void messageAdditionTest() throws ExecutionException, InterruptedException, TimeoutException {
        String testChatId = "p1p2";
        // Creating FsScheduleManager instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FsChatManager chatm = new FsChatManager(db);

        // Creating a test chat
        DummyUser u1 = new DummyUser("p1");
        DummyUser u2 = new DummyUser("p2");
        String testChatName = "p1p2";
        Chat chat = new Chat(u1, u2, testChatName );

        // Wait for the insert operation to complete
        Message testMessage = new Message(u1, u2, "heyy",  new Timestamp(2023, Month.AUGUST, 18, 11, 15, Timestamp.Meridiem.AM));
        boolean insertSuccess = chatm.addMessageToChat(testChatId, testMessage).get();

        // Verify that the insert operation succeeded
        assertTrue(insertSuccess);

        //adding the post to the schedule


        Future<Boolean> messageAdditionResult = chatm.addMessageToChat(testChatName, testMessage);
        boolean joinSuccess = messageAdditionResult.get();
        assertTrue(joinSuccess);

        //getting the modified schedule from the db
        Chat fetchedChathat = chatm.fetch(testChatName).get();
        assertTrue(fetchedChathat.getMessages().get(0).equals(testMessage));

        //cleaning up
        boolean deletionSuccess = chatm.delete(testChatId).get();
        assertTrue(deletionSuccess);

    }





}
