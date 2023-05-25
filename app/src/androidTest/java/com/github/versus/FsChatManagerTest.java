package com.github.versus;

import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.chats.Chat;
import com.github.versus.chats.Message;
import com.github.versus.db.FsChatManager;
import com.github.versus.db.FsPostManager;
import com.github.versus.db.FsScheduleManager;
import com.github.versus.db.FsUserManager;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.schedule.Schedule;
import com.github.versus.sports.Sport;
import com.github.versus.user.DummyUser;
import com.github.versus.user.VersusUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;


@RunWith(AndroidJUnit4.class)

public class FsChatManagerTest {

    @Test
    public void CorrectFsChatInsert_fetch() throws ExecutionException, InterruptedException, TimeoutException
    {
        // Creating FsPostm instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FsChatManager chatm = new FsChatManager(db);

        // Creating a test post
        Chat chat = new Chat("abdess", "aymane", "hehe" );

        //inserting the chat
        Future<Boolean> insertResult = chatm.insert(chat);

        // Wait for the insert operation to complete
        boolean insertSuccess = insertResult.get();

        // Verify that the insert operation succeeded
        assertTrue(insertSuccess);

        // Verify that the chat was inserted into Firestore
        //by fetching it and then comparing
        Chat c = chatm.fetch(chat.getChatId()).get();
        assertTrue(c.equals(chat));

        // Clean up the test data
        boolean deletionSuccess = chatm.delete(c.getChatId()).get();
        assertTrue(deletionSuccess);
    }

    @Test
    public void messageAdditionTest() throws ExecutionException, InterruptedException, TimeoutException {
        String testChatId = "p1p2";
        // Creating FsScheduleManager instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FsChatManager chatm = new FsChatManager(db);

        // Creating a test chat

        Chat chat = new Chat("p1", "p2", testChatId );

        //inserting the chat
        Future<Boolean> insertResult = chatm.insert(chat);

        // Wait for the insert operation to complete
        boolean insertSuccess = insertResult.get();
        assertTrue(insertSuccess);

        // creating a new message and adding it

        Message testMessage = new Message("p1", "p2", "heyy",  new Timestamp(2023, Month.AUGUST, 18, 11, 15, Timestamp.Meridiem.AM));
        boolean additionSuccess = chatm.addMessageToChat(testChatId, testMessage).get();

        // Verify that the addition operation succeeded
        assertTrue(additionSuccess);

        //adding the post to the schedule


        Future<Boolean> messageAdditionResult = chatm.addMessageToChat(testChatId, testMessage);
        assertTrue(messageAdditionResult.get());

        //getting the modified schedule from the db
        Chat fetchedChathat = chatm.fetch(testChatId).get();
        assertTrue(fetchedChathat.getMessages().get(0).equals(testMessage));

        //cleaning up
        boolean deletionSuccess = chatm.delete(testChatId).get();
        assertTrue(deletionSuccess);

    }









}