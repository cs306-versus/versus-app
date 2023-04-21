package com.github.versus.offline;

import com.github.versus.sports.Sport;
import com.github.versus.user.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {
    /*
    public static String convertUser(User user){
        user.getUID();
        user.getUserName();
        user.getFirstName();
        user.getLastName();
        user.getMail();
        user.getPhone();
        user.getCity();
        user.getZipCode();
        user.getRating();
        user.getPreferredSports();

        return null ;
    }*/

    /**
     * Coverts a list of sports to a string
     * @param sportList
     * @param sep
     * @return
     * The string representation of the list of sports
     */
    public static String convertListOfSports(List<Sport> sportList,String sep){
        StringBuilder builder = new StringBuilder();
        for(int i =0 ; i<sportList.size();++i){
            builder.append(sportList.get(i));
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
        return Arrays.asList(split).stream().map(s -> Sport.valueOf(s)).collect(Collectors.toList());
    }
}
