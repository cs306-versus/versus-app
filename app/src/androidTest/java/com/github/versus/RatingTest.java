package com.github.versus;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.github.versus.rating.Rating;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;
import com.github.versus.utils.FirebaseEmulator;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public final class RatingTest {

    static {
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
    }

    @Test
    public void EloComputationstaysBelowMax() {
        User u1 = new VersusUser.VersusBuilder("abdess1").setRating(Rating.MAX_ELO).build();
        User u2 = new VersusUser.VersusBuilder("abdess2").setRating(Rating.MAX_ELO).build();
        float newU2Rating = new Rating(u2, u1, 5).computeNewRating();
        assertTrue(newU2Rating <= Rating.MAX_ELO);
    }

    @Test
    public void EloComputationstaysAboveMin() {
       User u1 = new VersusUser.VersusBuilder("abdess1").setRating(Rating.MIN_RATING).build();
        User u2 = new VersusUser.VersusBuilder("abdess2").setRating(Rating.MIN_RATING).build();
        float newU2Rating = new Rating(u2, u1, 1).computeNewRating();
        assertTrue(newU2Rating >= Rating.MIN_ELO);
    }

    @Test
    public void EloRatingFromBetterPlayerGivesBetterResult() {
        User u1 = new VersusUser.VersusBuilder("abdess1").setRating(4000).build();
        User u2 = new VersusUser.VersusBuilder("abdess2").setRating(4500).build();
        User u3 = new VersusUser.VersusBuilder("abdess3").setRating(3000).build();
        float newU2Rating = new Rating(u1, u3, 3).computeNewRating();
        float newU3Rating = new Rating(u2, u3, 3).computeNewRating();
        assertTrue(newU2Rating <= newU3Rating);
    }


    @Test
    public void testConstructor1(){
        assertThrows(IllegalArgumentException.class, () -> new Rating(null, null, 0));
    }

    @Test
    public void testConstructor2(){
        assertThrows(IllegalArgumentException.class, () -> new Rating(null, null, 6));
    }

    @Test
    public void testGetRated(){
        assertNull(new Rating(null, null, 3).getRated());
    }


}
