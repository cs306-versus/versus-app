package com.github.versus.offline;

import com.github.versus.sports.Sport;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SportConverter {

    public static String convertSport(Sport sport){
        return sport.name();
    }
    public static Sport convertSportBack(String sport){
        return Sport.valueOf(sport);
    }
    /**
     * Coverts a list of sports to a string
     * @param sportList
     * @param sep
     * @return
     * The string representation of the list of sports
     */
    public static String convertListOfSports(List<Sport> sportList, String sep){
        StringBuilder builder = new StringBuilder();
        for(int i =0 ; i<sportList.size();++i){
            builder.append(convertSport(sportList.get(i)));
            if(i<sportList.size()-1){
                builder.append(sep);
            }
        }
        return builder.toString();
    }

    /**
     * Converts a String to a list of sports
     * @param conversion
     * @param sep
     * @return
     * The string representation of the sport.
     */
    public static List<Sport> convertBackToSports(String conversion,String sep){
        String split[]= conversion.split(sep);
        return Arrays.asList(split).stream().map(s -> convertSportBack(s)).collect(Collectors.toList());
    }
}
