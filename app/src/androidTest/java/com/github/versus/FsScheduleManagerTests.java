package com.github.versus;

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
    public void CorrectFsPostInsert_Get() throws ExecutionException, InterruptedException, TimeoutException
    {
        // Creating FsScheduleManager instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FsScheduleManager schedm = new FsScheduleManager(db);

        // Creating a test schedule
        Schedule testSchedule = new Schedule("Abdess");

        //inserting the schedule
        Future<Boolean> insertResult = schedm.insert(testSchedule);

        // Wait for the insert operation to complete
        boolean insertSuccess = insertResult.get();

        // Verify that the insert operation succeeded
        assertTrue(insertSuccess);

        // Verify that the schedule was inserted into Firestore
        //by fetching it and then comparing
        Schedule p = schedm.fetch("_test_").get();
        assertTrue(testSchedule.equals(p));

        // Clean up the test data
        boolean deletionSuccess = schedm.delete("_test_").get();
        assertTrue(deletionSuccess);
    }

}
