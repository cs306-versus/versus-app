package com.github.versus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.db.FsPostManager;
import com.github.versus.db.FsScheduleManager;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.schedule.Schedule;
import com.github.versus.sports.Sport;
import com.github.versus.utils.FirebaseEmulator;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

@RunWith(AndroidJUnit4.class)
public class FsScheduleManagerTest {
    @Test
    public void CorrectFsSchedInsert_Delete() throws ExecutionException, InterruptedException, TimeoutException
    {
        String testSchedName = "Abderr-xl";
        // Creating FsScheduleManager instance
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
        FsScheduleManager schedm = new FsScheduleManager(db);

        // Creating a test schedule
        Schedule testSchedule = new Schedule(testSchedName);

        //inserting the schedule
        Future<Boolean> insertResult = schedm.insert(testSchedule);

        // Wait for the insert operation to complete
        boolean insertSuccess = insertResult.get();

        // Verify that the insert operation succeeded
        assertTrue(insertSuccess);

        // Verify that the schedule was inserted into Firestore
        //by fetching it and then comparing
        Schedule fertchedSched = schedm.fetch(testSchedName).get();
        assertTrue(testSchedule.equals(fertchedSched));

        // Clean up the test data
        boolean deletionSuccess = schedm.delete(testSchedName).get();
        assertTrue(deletionSuccess);
    }

    @Test
    public void NullGetResultOnAbsentSched() throws ExecutionException, InterruptedException, TimeoutException
    {
        String hoaxName = "hoaxxxxxxxxxxxxx" ;
        // Creating FsPostm instance
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
        FsPostManager postm = new FsPostManager(db);

        // delete the test post if it's there
        boolean deletionSuccess = postm.delete(hoaxName).get();
        assertTrue(deletionSuccess);

        // Verify that the getting the non existent schedule in Firestore will return null
        Post p = postm.fetch(hoaxName).get();
        assertNull(p);
    }

    @Test
    public void addPostsToScheduleTest() throws ExecutionException, InterruptedException, TimeoutException {
        String testSchedName = "Abdess-xl";
        // Creating FsScheduleManager instance
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
        FsScheduleManager schedm = new FsScheduleManager(db);

        // Creating a test schedule
        Schedule testSchedule = new Schedule(testSchedName);

        // Wait for the insert operation to complete
        boolean insertSuccess = schedm.insert(testSchedule).get();

        // Verify that the insert operation succeeded
        assertTrue(insertSuccess);

        //adding the post to the schedule
        String postName = "tkherbi9a";
        Post testPost = new Post( postName, new Timestamp(2023, Month.AUGUST, 18, 11, 15, Timestamp.Meridiem.AM) ,
                new Location("tirane", 0, 0), new ArrayList<>(), 15, Sport.FOOTBALL);

        Future<Boolean> postAdditionResult = schedm.addPostToSchedule(testSchedName, testPost);
        boolean joinSuccess = postAdditionResult.get();
        assertTrue(joinSuccess);

        //getting the modified schedule from the db
        Schedule updatedSchedule = schedm.fetch(testSchedName).get();
        assertTrue(updatedSchedule.getPosts().get(0).equals(testPost));

        //cleaning up
        boolean deletionSuccess = schedm.delete(postName).get();
        assertTrue(deletionSuccess);
    }

    //testing the getSchedule on and after date :
    @Test
    public void getScheduleFromDateTest() throws ExecutionException, InterruptedException, TimeoutException {
        List<Post> posts = new ArrayList<>();;
        int startingDay = 15;
        int nbPosts = 25;
        for (int i = 1; i <= nbPosts; i++) {
            posts.add( new Post( String.format("random_num_%d", i) , new Timestamp(2023, Month.AUGUST, i, 11, 15, Timestamp.Meridiem.AM) ,
                    new Location("tirane", 0, 0), new ArrayList<>(), 15, Sport.FOOTBALL));

        }
        // creating the schedule and adding the post
        String testSchedName = "testSched";
        Schedule schedule = new Schedule(testSchedName);
        schedule.addPosts(posts);
        // creating the schedule manager and inserting the post
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
        FsScheduleManager schedm = new FsScheduleManager(db);

        assertTrue(schedm.insert(schedule).get());

        // getting the schedule starting from a date
        Timestamp t = new Timestamp(2023, Month.AUGUST, startingDay, 0, 10, Timestamp.Meridiem.AM);
        Schedule fetchedSched = schedm.getScheduleStartingFromDate(testSchedName, t).get();

        for (int i = 1; i < startingDay; i++) {
            schedule.removePost(String.format("random_num_%d", i));
        }
        assertEquals(fetchedSched.getPosts(), schedule.getPosts().subList(startingDay - 1, nbPosts));

    }
    @Test
    public void getScheduleOnDateTest() throws ExecutionException, InterruptedException, TimeoutException {
        List<Post> posts = new ArrayList<>();;
        int startingDay = 15;
        int nbPosts = 25;
        for (int i = 1; i <= nbPosts; i++) {
            posts.add( new Post( String.format("random_num_%d", i) , new Timestamp(2023, Month.AUGUST, i, 11, 15, Timestamp.Meridiem.AM) ,
                    new Location("tirane", 0, 0), new ArrayList<>(), 15, Sport.FOOTBALL));

        }
        // creating the schedule and adding the post
        String testSchedName = "testSched";
        Schedule schedule = new Schedule(testSchedName);
        schedule.addPosts(posts);
        // creating the schedule manager and inserting the post
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
        FsScheduleManager schedm = new FsScheduleManager(db);

        assertTrue(schedm.insert(schedule).get());

        // getting the schedule starting from a date
        Timestamp t = new Timestamp(2023, Month.AUGUST, startingDay, 0, 10, Timestamp.Meridiem.AM);
        Schedule fetchedSched = schedm.getScheduleOnDate(testSchedName, t).get();

        assertEquals(fetchedSched.getPosts(), schedule.getPosts().subList(startingDay - 1, startingDay));

    }

}