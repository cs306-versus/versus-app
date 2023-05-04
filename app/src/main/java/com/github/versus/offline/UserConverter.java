package com.github.versus.offline;

import com.github.versus.sports.Sport;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class UserConverter {
    public static String convertUser(User user) {
        String sportsString= SportConverter.convertListOfSports(user.getPreferredSports());
        return String.format(Locale.ENGLISH,"%s|%s|%s|%s|%s|%s|%d|%s|%d|%s",
                user.getUID(), user.getFirstName(), user.getLastName(),
                user.getUserName(), user.getMail(), user.getPhone(),
                user.getRating(), user.getCity(), user.getZipCode(),
                SportConverter.convertListOfSports(user.getPreferredSports()));

    }
    public static User convertBackUser(String userString) {
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
        return new VersusUser(builder);
    }

}
