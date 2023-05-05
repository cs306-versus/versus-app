package com.github.versus.offline;

import com.github.versus.sports.Sport;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class UserConverter {
    /**
     * Converts a user to a string
     * @param user
     * @return
     * The string encoding of a user
     */
    public static String convertUser(VersusUser user) {
        return String.format(Locale.ENGLISH,"%s|%s|%s|%s|%s|%s|%d|%s|%d|%s",
                user.getUID(), user.getFirstName(), user.getLastName(),
                user.getUserName(), user.getMail(), user.getPhone(),
                user.getRating(), user.getCity(), user.getZipCode(),
                SportConverter.convertListOfSports(user.getPreferredSports()));

    }

    /**
     * Converts back a string to a user
     * @param userString
     * @return
     * The User matching the string
     */
    public static VersusUser convertBackUser(String userString) {
        String[] tokens = userString.split("\\|");
        String uid = tokens[0];
        String firstName = tokens[1];
        String lastName = tokens[2];
        String userName = tokens[3];
        String mail = tokens[4];
        String phone = tokens[5];
        int rating = Integer.parseInt(tokens[6]);
        String city = tokens[7];
        int zipCode = Integer.parseInt(tokens[8]);
        List<Sport> preferredSports = SportConverter.convertBackToSports(tokens[9]);
        VersusUser.Builder builder = new VersusUser.Builder(uid);
        builder.setFirstName(firstName);
        builder.setLastName(lastName);
        builder.setUserName(userName);
        builder.setMail(mail);
        builder.setPhone(phone);
        builder.setRating(rating);
        builder.setCity(city);
        builder.setZipCode(zipCode);
        builder.setPreferredSports(preferredSports);
        return builder.build();
    }

    /**
     * Converts a list of users to string
     * @param userList
     * @return
     * The string encoding of the list of users
     */
    public static String convertListOfUsers(List<VersusUser> userList) {
        return userList.stream()
                .map(UserConverter::convertUser)
                .collect(Collectors.joining("[u]"));
    }

    /**
     * Converts back a string to a list of users
     * @param usersString
     * @return
     * The list of users matching the string
     */
    public static List<VersusUser> convertBackListOfUsers(String usersString) {
        String[] userStrings = usersString.split("\\[u\\]");
        List<VersusUser> userList = new ArrayList<>();
        for (String userString : userStrings) {
            userList.add(convertBackUser(userString));
        }
        return userList;
    }



}
