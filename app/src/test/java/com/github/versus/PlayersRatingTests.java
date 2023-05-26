package com.github.versus;

import org.junit.Assert;
import org.junit.Test;

public class PlayersRatingTests {

    @Test
    public void ConstructorYieldsInstance(){
        Assert.assertNotNull(new PlayerToBeRated(true,"AbdessPiquant","4"));
    }
    @Test
    public void RatingIsCorrect(){
        Assert.assertEquals("4", new PlayerToBeRated(true, "AbdessPiquant", "4").get_rate());
    }

    @Test
    public void RatedIsVisible(){
        Assert.assertTrue( new PlayerToBeRated(true, "AbdessPiquant", "4").isPlayerRated());
    }

    @Test
    public void NameIsCorrect(){
        Assert.assertEquals("AbdessPiquant", new PlayerToBeRated(true, "AbdessPiquant", "4").get_pseudo_name());
    }
}
