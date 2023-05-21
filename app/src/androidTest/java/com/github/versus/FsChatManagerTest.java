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


    public void createSomeUsers() throws ExecutionException, InterruptedException, TimeoutException
    {
        // Creating FsPostm instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        List<VersusUser> userList = new ArrayList<>();
        List<String> mails = new ArrayList<>();
        mails.add("jane.doe@versus.ch");
        mails.add("adam.mernissi@versus.ch");
        mails.add("aymane.lamyaghri@versus.ch");
        mails.add("mehdi.ziazi@versus.ch");
        mails.add("hamza.remmal@versus.ch");
        mails.add("alex.muller@versus.ch");
        List<String> firstNames = new ArrayList<>();
        firstNames.add("Jane");
        firstNames.add("Adam");
        firstNames.add("Aymane");
        firstNames.add("Mehdi");
        firstNames.add("Hamza");
        firstNames.add("Alex");

        List<String> lastNames = new ArrayList<>();
        lastNames.add("doe");
        lastNames.add("Adam");
        lastNames.add("Aymane");
        lastNames.add("Ziazi");
        lastNames.add("Remmal");
        lastNames.add("Muller");

        List<String> uids = new ArrayList<>();
        for (String mail: mails
        ) {
            String hash = VersusUser.computeUID(mail);
            uids.add(hash);
        }

        for (int i = 0; i < mails.size() ; i++) {
            int finalI = i;
            List<String> friends = uids.stream().filter(e -> e != uids.get(finalI)).collect(Collectors.toList());
            friends.forEach(e -> System.out.println(e));
            userList.add(
                    new VersusUser.Builder(uids.get(i)).setUserName(firstNames.get(i)+"_"+lastNames.get(i)).setFirstName(firstNames.get(i)).setLastName(lastNames.get(i)).setMail(mails.get(i))
                            .setPhone("+417864847892").setRating(2000).setCity("Lausanne").setPreferredSports(List.of(Sport.FOOTBALL)).setFriends(friends).build()
            );
        }

        FsUserManager userManager = new FsUserManager(db);
        for (VersusUser user: userList
        ) {
            assertTrue( userManager.insert(user).get());
        }

        FsChatManager chatManager = new FsChatManager(db);
        for (String friend: userList.get(0).getFriends()
        ) {
            List<Message> messageList = new ArrayList<>();
            // jane doe chats
            String u1 = uids.get(0);
            String u2 = friend;
            messageList.add(new Message(u1, u2, "Yo boii what's up", new Timestamp(2023, Month.MAY, 14, 7, 30,  Timestamp.Meridiem.AM)));
            messageList.add(new Message(u2, u1, "hey man",new Timestamp(2023, Month.MAY, 14, 7, 30,  Timestamp.Meridiem.AM)));
            messageList.add(new Message(u1, u2, "game tomorrow, you down ?", new Timestamp(2023, Month.MAY, 14, 7, 30,  Timestamp.Meridiem.AM)));
            messageList.add(new Message(u2, u1, "of couuuuurse man you know me ", new Timestamp(2023, Month.MAY, 14, 7, 30,  Timestamp.Meridiem.AM)));
            messageList.add(new Message(u1, u2, "All right see you at 9, usual place",new  Timestamp(2023, Month.MAY, 14, 7, 30,  Timestamp.Meridiem.AM)));
            messageList.add(new Message(u2, u1, "bet",new  Timestamp(2023, Month.MAY, 14, 7, 30,  Timestamp.Meridiem.AM)));

            Chat chat = new Chat(u1, u2, Chat.computeChatId(u1,u2) );
            for (Message m: messageList
            ) {
                chat.addMessage(m);
            }
            assertTrue(chatManager.insert(chat).get());
        }
    }






}