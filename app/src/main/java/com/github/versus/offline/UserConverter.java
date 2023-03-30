package com.github.versus.offline;

import com.github.versus.sports.Sport;
import com.github.versus.user.User;

import java.util.List;

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
}
