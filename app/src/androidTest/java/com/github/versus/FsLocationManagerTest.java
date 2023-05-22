package com.github.versus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.db.FsPostManager;
import com.github.versus.db.FsLocationManager;
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

public class FsLocationManagerTest {

    static {
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
    }

    @Test
    public void CorrectFsLocationInsert_Delete() throws ExecutionException, InterruptedException, TimeoutException
    {
        Location l = new Location("tirane", 0, 0);
        // Creating FsLocationManager instance
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
        FsLocationManager locm = new FsLocationManager(db);

        //inserting the schedule
        Future<Boolean> insertResult = locm.insert(l);

        // Wait for the insert operation to complete
        boolean insertSuccess = insertResult.get();

        // Verify that the insert operation succeeded
        assertTrue(insertSuccess);

        // Verify that the schedule was inserted into Firestore
        //by fetching it and then comparing
        Location fetchedLocation = locm.fetch(l.getName()).get();
        assertTrue(fetchedLocation.equals(l));

        // Clean up the test data
        boolean deletionSuccess = locm.delete(l.getName()).get();
        assertTrue(deletionSuccess);
    }

    @Test
    public void NullGetResultOnAbsentLoc() throws ExecutionException, InterruptedException, TimeoutException
    {
        String hoaxName = "hoaxxxxxxxxxxxxx" ;
        // Creating FsLocm instance
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
        FsLocationManager locm = new FsLocationManager(db);

        // delete the test post if it's there
        boolean deletionSuccess = locm.delete(hoaxName).get();
        assertTrue(deletionSuccess);

        // Verify that the getting the non existent schedule in Firestore will return null
        Location p = locm.fetch(hoaxName).get();
        assertNull(p);
    }


}
