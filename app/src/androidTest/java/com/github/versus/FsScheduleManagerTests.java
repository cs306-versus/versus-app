package com.github.versus;

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
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Month;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

@RunWith(AndroidJUnit4.class)

public class FsScheduleManagerTests {
    @Test
    public void CorrectFsSchedInsert_Delete() throws ExecutionException, InterruptedException, TimeoutException
    {
        String testSchedName = "Abdess-xl";
        // Creating FsScheduleManager instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        Schedule p = schedm.fetch(testSchedName).get();
        assertTrue(testSchedule.equals(p));

        // Clean up the test data
        boolean deletionSuccess = schedm.delete(testSchedName).get();
        assertTrue(deletionSuccess);
    }

    @Test
    public void NullGetResultOnAbsentSched() throws ExecutionException, InterruptedException, TimeoutException
    {
        String hoaxName = "hoaxxxxxxxxxxxxx" ;
        // Creating FsPostm instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
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


    }





}
