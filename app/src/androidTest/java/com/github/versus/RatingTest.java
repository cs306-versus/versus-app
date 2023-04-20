package com.github.versus;

import com.github.versus.rating.Rating;
import com.github.versus.user.DummyUser;
import com.github.versus.user.VersusUser;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class RatingTest {

    @Test
    public void EloComputationstaysBelowMax() throws ExecutionException, InterruptedException, TimeoutException{
        VersusUser u1 = new VersusUser.Builder("abdess1").setRating(Rating.MAX_ELO).build();
        VersusUser u2 = new VersusUser.Builder("abdess2").setRating(Rating.MAX_ELO).build();
        float newU2Rating = new Rating(u2,u1, 5).computeNewRating();
        assert(newU2Rating<=Rating.MAX_ELO);
    }
    @Test
    public void EloComputationstaysAboveMin() throws ExecutionException, InterruptedException, TimeoutException{
        VersusUser u1 = new VersusUser.Builder("abdess1").setRating(Rating.MIN_RATING).build();
        VersusUser u2 = new VersusUser.Builder("abdess2").setRating(Rating.MIN_RATING).build();
        float newU2Rating = new Rating(u2,u1, 1).computeNewRating();
        assert(newU2Rating>=Rating.MIN_ELO);
    }

    @Test
    public void EloRatingFromBetterPlayerGivesBetterResult() throws ExecutionException, InterruptedException, TimeoutException{
        VersusUser u1 = new VersusUser.Builder("abdess1").setRating(4000).build();
        VersusUser u2 = new VersusUser.Builder("abdess2").setRating(4500).build();
        VersusUser u3 = new VersusUser.Builder("abdess3").setRating(3000).build();
        float newU2Rating = new Rating(u1,u3, 3).computeNewRating();
        float newU3Rating = new Rating(u2,u3, 3).computeNewRating();

        assert(newU2Rating<=newU3Rating);
    }
}
