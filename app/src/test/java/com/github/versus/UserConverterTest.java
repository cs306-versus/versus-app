package com.github.versus;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import com.github.versus.offline.CachedPost;
import com.github.versus.offline.UserConverter;
import com.github.versus.sports.Sport;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UserConverterTest {

    @Test
    public void convertListOfSportsWorks() {
        Sport sports[]= {Sport.FOOTBALL,Sport.ClIMBING,Sport.ROWING,Sport.SOCCER};
        String sep = "***";
        String conversion = UserConverter.convertListOfSports(Arrays.asList(sports),sep);
        String shouldBe= Sport.FOOTBALL.name()+sep+Sport.ClIMBING.name()+sep+Sport.ROWING.name()+sep+Sport.SOCCER.name();
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
    @Test
    public void convertBackToSportsWorks(){
        String sep = "&";
        String conversion ="FOOTBALL&ClIMBING&ROWING&SOCCER";
        List<Sport>  sportList= UserConverter.convertBackToSports(conversion,sep);
        Sport shouldBe[]= {Sport.FOOTBALL,Sport.ClIMBING,Sport.ROWING,Sport.SOCCER};
        System.out.println(sportList);
        assertTrue(sportList.equals(Arrays.asList(shouldBe)));

    }
    @Test
    public void convertBackToSportsWorksWithEmptyString(){
        String sep = "&";
        String conversion ="";
        List<Sport> sportList= new ArrayList<>();
        System.out.println(sportList);
        Sport shouldBe[]= {};
        assertTrue(sportList.equals(Arrays.asList(shouldBe)));
    }

    @Test
    public void convertBackToSportsIsTheInverseOfConvertListOfSports(){
        Sport sports[]= {Sport.FOOTBALL,Sport.ClIMBING,Sport.ROWING,Sport.SOCCER};
        String sep = "&";
        String conversion = UserConverter.convertListOfSports(Arrays.asList(sports),sep);
        List<Sport> sportList= UserConverter.convertBackToSports(conversion,sep);
        assertTrue(sportList.equals(Arrays.asList(sports)));

    }

}
