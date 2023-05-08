package com.github.versus;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.github.versus.db.FsPostManager;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.sports.Sport;
import com.github.versus.user.DummyUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class FsPostManagerTests {

    @Test
    public void CorrectFsPostInsert_fetch() throws ExecutionException, InterruptedException, TimeoutException {
        // Creating FsPostm instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FsPostManager postm = new FsPostManager(db);

        // Creating a test post
        Post post = new Post("_test_", new Timestamp(2023, Month.AUGUST, 18, 11, 15, Timestamp.Meridiem.AM),
                new Location("tirane", 0, 0), new ArrayList<>(), 15, Sport.SOCCER, new ArrayList<>(), "_test_");

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

    @Test
    public void NullGetResultOnAbsentPost() throws ExecutionException, InterruptedException, TimeoutException {
        // Creating FsPostm instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FsPostManager postm = new FsPostManager(db);

        // delete the test post if it's there
        boolean deletionSuccess = postm.delete("_test_").get();
        assertTrue(deletionSuccess);

        // Verify that the post was inserted into Firestore
        //by fetching it and then comparing
        Post p = postm.fetch("_test_").get();
        assertNull(p);
    }


    @Test
    public void CorrectFetchAllPostsFromTestCollection() throws ExecutionException, InterruptedException {
        // Creating FsPostm instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FsPostManager postm = new FsPostManager(db);
        List<Post> l = postm.fetchAll("test_posts").get();
        //creating test posts

        Post postTest = new Post("_test_", new Timestamp(2023, Month.AUGUST, 18, 10, 15, Timestamp.Meridiem.AM),
                new Location("tirane", 0, 0), new ArrayList<>(), 15, Sport.FOOTBALL, new ArrayList<>(), "");
        Post postTest1 = new Post("_test_1", new Timestamp(2023, Month.AUGUST, 18, 10, 15, Timestamp.Meridiem.AM),
                new Location("tirane", 0, 0), new ArrayList<>(), 15, Sport.FOOTBALL, new ArrayList<>(), "");
        Post postTest2 = new Post("_test_2", new Timestamp(2023, Month.AUGUST, 18, 10, 15, Timestamp.Meridiem.AM),
                new Location("tirane", 0, 0), new ArrayList<>(), 15, Sport.FOOTBALL, new ArrayList<>(), "");
        Set<Post> refSet = new HashSet<>();
        refSet.add(postTest);
        refSet.add(postTest1);
        refSet.add(postTest2);

        Set<Post> resultPosts = new HashSet<>(l);
        assertEquals(resultPosts, refSet);
    }

    @Test
    public void CorrectJoinOnPresentPost() throws ExecutionException, InterruptedException {
        // Creating FsPostm instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FsPostManager postm = new FsPostManager(db);

        // Creating a test post
        String postName = "_test_1";
        Post post = new Post(postName, new Timestamp(2023, Month.AUGUST, 18, 11, 15, Timestamp.Meridiem.AM),
                new Location("tirane", 0, 0), new ArrayList<>(), 15, Sport.FOOTBALL, new ArrayList<>(), "");

        //inserting the post
        Future<Boolean> insertResult = postm.insert(post);

        // Wait for the insert operation to complete
        boolean insertSuccess = insertResult.get();

        // Verify that the insert operation succeeded
        assertTrue(insertSuccess);

        //joining the post
        String name = "hassan";
        Future<Boolean> joinResult = postm.joinPost(postName, new DummyUser(name));
        boolean joinSuccess = joinResult.get();
        assertTrue(joinSuccess);

        //getting the new post from db
        Post updatedPost = postm.fetch(postName).get();
        String playerId = updatedPost.getPlayers().get(0).getUID();
        assertTrue(playerId.equals(name));

        boolean deletionSuccess = postm.delete(postName).get();
        assertTrue(deletionSuccess);

    }


    @Test
    public void CorrectFetchSpecific() throws ExecutionException, InterruptedException {
        // Creating FsPostm instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FsPostManager postm = new FsPostManager(db);

        // Creating a test post
        Location testLocation = new Location("tirane", 0, 0);
        String testPostName = "_test_";
        Post post = new Post(testPostName, new Timestamp(2023, Month.AUGUST, 18, 11, 15, Timestamp.Meridiem.AM),
                testLocation, new ArrayList<>(), 15, Sport.SOCCER, new ArrayList<>(), "");

        //inserting the post
        Future<Boolean> insertResult = postm.insert(post);

        // Wait for the insert operation to complete
        boolean insertSuccess = insertResult.get();

        // Verify that the insert operation succeeded
        assertTrue(insertSuccess);

        // Verify that the post was inserted into Firestore
        //by fetching it and then comparing
        List<Post> fetchedPosts = postm.fetchSpecific("location", testLocation).get();
        assertTrue(post.equals(fetchedPosts.get(0)));

        // Clean up the test data
        boolean deletionSuccess = postm.delete(testPostName).get();
        assertTrue(deletionSuccess);
    }


}
