package com.github.versus.utils;

import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.db.FsPostManager;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.sports.Sport;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Month;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

@RunWith(AndroidJUnit4.class)

public class FsChatManagerTest {

    @Test
    public void CorrectFsPostInsert_fetch() throws ExecutionException, InterruptedException, TimeoutException
    {
        // Creating FsPostm instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FsPostManager postm = new FsPostManager(db);

        // Creating a test post
        Post post = new Post( "_test_", new Timestamp(2023, Month.AUGUST, 18, 11, 15, Timestamp.Meridiem.AM) ,
                new Location("tirane", 0, 0), new ArrayList<>(), 15, Sport.SOCCER, new ArrayList<>());

        //inserting the post
        Future<Boolean> insertResult = postm.insert(post);

        // Wait for the insert operation to complete
        boolean insertSuccess = insertResult.get();

        // Verify that the insert operation succeeded
        assertTrue(insertSuccess);

        // Verify that the post was inserted into Firestore
        //by fetching it and then comparing
        Post p = postm.fetch("_test_").get();
        assertTrue(post.equals(p));

        // Clean up the test data
        boolean deletionSuccess = postm.delete("_test_").get();
        assertTrue(deletionSuccess);
    }



}
