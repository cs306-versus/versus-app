package com.github.versus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.github.versus.offline.SportConverter;
import com.github.versus.sports.Sport;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public final class SportConverterTest {

    @Test
    public void convertSportWorks() {
        assertEquals(SportConverter.convertSport(Sport.BOXING), Sport.BOXING.name());
    }

    @Test
    public void convertBackSportFailsWithNoException() {
        assertNull(SportConverter.convertSportBack("no sport"));
    }

    @Test
    public void convertBackSportWorks() {
        assertEquals(SportConverter.convertSportBack(Sport.BOXING.name()), Sport.BOXING);
    }

    @Test
    public void convertListOfSportsWorks() {
        Sport[] sports = {Sport.FOOTBALL, Sport.CLIMBING, Sport.ROWING, Sport.SOCCER};
        String conversion = SportConverter.convertListOfSports(Arrays.asList(sports));
        String shouldBe = Sport.FOOTBALL.name() + ", " + Sport.CLIMBING.name() + ", " + Sport.ROWING.name() + ", " + Sport.SOCCER.name();
        assertEquals(conversion, shouldBe);
    }

    @Test
    public void convertListOnEmptyStrings() {
        Sport[] sports = {};
        String conversion = SportConverter.convertListOfSports(Arrays.asList(sports));
        String shouldBe = "";
        assertEquals(conversion, shouldBe);
    }

    @Test
    public void convertBackToSportsWorks() {
        String sep = "&";
        String conversion = "FOOTBALL, CLIMBING, ROWING, SOCCER";
        List<Sport> sportList = SportConverter.convertBackToSports(conversion);
        Sport[] shouldBe = {Sport.FOOTBALL, Sport.CLIMBING, Sport.ROWING, Sport.SOCCER};
        System.out.println(sportList);
        assertEquals(sportList, Arrays.asList(shouldBe));
    }

    @Test
    public void convertBackToSportsIsTheInverseOfConvertListOfSports() {
        Sport[] sports = {Sport.FOOTBALL, Sport.CLIMBING, Sport.ROWING, Sport.SOCCER};
        String conversion = SportConverter.convertListOfSports(Arrays.asList(sports));
        List<Sport> sportList = SportConverter.convertBackToSports(conversion);
        assertEquals(sportList, Arrays.asList(sports));
    }

}