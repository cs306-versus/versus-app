package com.github.versus;

import static org.junit.Assert.assertTrue;

import com.github.versus.offline.SportConverter;
import com.github.versus.offline.UserConverter;
import com.github.versus.sports.Sport;

import org.junit.Test;


import java.util.Arrays;
import java.util.List;

public class SportConverterTest {

    @Test
    public void convertSportWorks(){
        assertTrue(SportConverter.convertSport(Sport.BOXING).equals(Sport.BOXING.name()));
    }
    @Test
    public void convertBackSportFailsWithNoException(){
        assertTrue(SportConverter.convertSportBack("no sport")==null);
    }
    @Test
    public void convertBackSportWorks(){
        assertTrue(SportConverter.convertSportBack(Sport.BOXING.name()).equals(Sport.BOXING));
    }

    @Test
    public void convertListOfSportsWorks() {
        Sport sports[]= {Sport.FOOTBALL,Sport.CLIMBING,Sport.ROWING,Sport.SOCCER};
        String sep = "***";
        String conversion = SportConverter.convertListOfSports(Arrays.asList(sports),sep);
        String shouldBe= Sport.FOOTBALL.name()+sep+Sport.CLIMBING.name()+sep+Sport.ROWING.name()+sep+Sport.SOCCER.name();
        assertTrue(conversion.equals(shouldBe));
    }

    @Test
    public void convertListOnEmptyStrings() {
        Sport sports[]= {};
        String sep = "***";
        String conversion = SportConverter.convertListOfSports(Arrays.asList(sports),sep);
        String shouldBe= "";
        assertTrue(conversion.equals(shouldBe));
    }
    @Test
    public void convertBackToSportsWorks(){
        String sep = "&";
        String conversion ="FOOTBALL&CLIMBING&ROWING&SOCCER";
        List<Sport> sportList= SportConverter.convertBackToSports(conversion,sep);
        Sport shouldBe[]= {Sport.FOOTBALL,Sport.CLIMBING,Sport.ROWING,Sport.SOCCER};
        System.out.println(sportList);
        assertTrue(sportList.equals(Arrays.asList(shouldBe)));

    }

    @Test
    public void convertBackToSportsIsTheInverseOfConvertListOfSports(){
        Sport sports[]= {Sport.FOOTBALL,Sport.CLIMBING,Sport.ROWING,Sport.SOCCER};
        String sep = "&";
        String conversion = SportConverter.convertListOfSports(Arrays.asList(sports),sep);
        List<Sport> sportList= SportConverter.convertBackToSports(conversion,sep);
        assertTrue(sportList.equals(Arrays.asList(sports)));

    }


}
