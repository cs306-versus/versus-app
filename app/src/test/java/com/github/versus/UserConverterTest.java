package com.github.versus;

import static org.junit.Assert.assertTrue;

import com.github.versus.offline.CachedPost;
import com.github.versus.offline.UserConverter;
import com.github.versus.sports.Sport;

import org.junit.Test;

import java.util.Arrays;


public class UserConverterTest {

    @Test
    public void convertListOfSportsWorks() {
        Sport sports[]= {Sport.FOOTBALL,Sport.ClIMBING,Sport.ROWING,Sport.SOCCER};
        String sep = "***";
        String conversion = UserConverter.convertListOfSports(Arrays.asList(sports),sep);
        String shouldBe= Sport.FOOTBALL.name()+sep+Sport.ClIMBING.name()+sep+Sport.ROWING.name()+sep+Sport.SOCCER.name();
        System.out.println(conversion);
        assertTrue(conversion.equals(shouldBe));
    }

    @Test
    public void convertListOnEmptyStrings() {
        Sport sports[]= {};
        String sep = "***";
        String conversion = UserConverter.convertListOfSports(Arrays.asList(sports),sep);
        String shouldBe= "";
        assertTrue(conversion.equals(shouldBe));
    }
}
