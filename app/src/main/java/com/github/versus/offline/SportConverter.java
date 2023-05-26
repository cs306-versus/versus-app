package com.github.versus.offline;

import com.github.versus.sports.Sport;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SportConverter {
    /**
     * converts a sport to a string to make it possible to cache
     * @param sport
     * @return
     * the string representation
     */
    public static String convertSport(Sport sport){
        return sport.name();
    }

    /**
     * converts back a string to the corresponding sport
     * @param sport
     * @return
     * the corresponding sport or null if none matches
     */
    public static Sport convertSportBack(String sport){
        try {
            return Sport.valueOf(sport);
        } catch (IllegalArgumentException e) {
            return null;
        }

    }
    /**
     * Coverts a list of sports to a string
     * @param sportList
     * @return
     * The string representation of the list of sports
     */
    public static String convertListOfSports(List<Sport> sportList){

        return sportList.stream()
                .map(Sport::name)
                .collect(Collectors.joining(", "));

    }

    /**
     * Converts a String to a list of sports
     * @param sportsString
     * @return
     * Corresponding list of sports that was encoded in the string
     *
     */
    public static List<Sport> convertBackToSports(String sportsString){
        List<Sport> sports = new ArrayList<>();
        String[] sportNames = sportsString.split(",\\s*");
        for (String name : sportNames) {
            try {
                Sport sport = Sport.valueOf(name.toUpperCase());
                sports.add(sport);
            } catch (IllegalArgumentException e) {
               continue;
            }
        }
        return sports;
    }
}
