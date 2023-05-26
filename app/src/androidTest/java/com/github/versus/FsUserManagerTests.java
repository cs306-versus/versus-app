package com.github.versus;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.db.FsScheduleManager;
import com.github.versus.db.FsUserManager;
import com.github.versus.utils.FirebaseEmulator;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

@RunWith(AndroidJUnit4.class)

public class FsUserManagerTests {

    @Test
    public void FsUserManagerDoesNotAddNonExistingFriends() throws ExecutionException, InterruptedException {
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
        FsUserManager manager= new FsUserManager(db);
        Assert.assertFalse(manager.createFriendship("Adam Lkharita","AbdessPiquant").get());
    }
    @Test
    public void FsUserManagerCreatesFriendship() throws ExecutionException, InterruptedException {
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
        FsUserManager manager= new FsUserManager(db);
        Assert.assertTrue(manager.createFriendship("fRZYnqU7K5bKtI0oKKDUerLu1Xm1","h4VJb8UPmiogeJ411P8eFHveWJp2").get());
    }
}
