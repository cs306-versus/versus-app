package com.github.versus;

import com.github.versus.offline.UserConverter;
import com.github.versus.sports.Sport;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ListUserConverterTest {
    private static String strUsers= buildTestString();

    private static List<User> users = buildTestUsers();

    //private static User reverted= UserConverter.convertBackUser(strUser);

    private static List<User> buildTestUsers() {
        List<Sport> preferredSports1= Arrays.asList(Sport.MARTIALARTS,Sport.CLIMBING,Sport.CRICKET,
                Sport.JUDO,Sport.GOLF, Sport.SURFING,Sport.WRESTLING);

        List<Sport> preferredSports2= Arrays.asList(Sport.BOXING);

        List<Sport> preferredSports3= Arrays.asList(Sport.FOOTBALL,Sport.BASKETBALL);

        VersusUser.Builder builder ;
        builder= new VersusUser.Builder("007");
        builder.setFirstName("James");
        builder.setLastName("Bond");
        builder.setUserName("NotInfiltrated");
        builder.setMail("notfakemail@gmail.com");
        builder.setPhone("0000077777");
        builder.setRating(5);
        builder.setCity("London");
        builder.setZipCode(700);
        builder.setPreferredSports(preferredSports1);
        User JamesBond=   builder.build();

        builder = new VersusUser.Builder("1");
        builder.setFirstName("Mohammad");
        builder.setLastName("Ali");
        builder.setUserName("TheGoat");
        builder.setMail("IamTheDancingMaster@gmail.com");
        builder.setPhone("0000000001");
        builder.setRating(5);
        builder.setCity("Ring");
        builder.setZipCode(1);
        builder.setPreferredSports(preferredSports2);
        User MohammedAli= builder.build();

        builder = new VersusUser.Builder("999");
        builder.setFirstName("Regular");
        builder.setLastName("Guy");
        builder.setUserName("BasicGuy");
        builder.setMail("ILoveBallSports@gmail.com");
        builder.setPhone("999999999");
        builder.setRating(3);
        builder.setCity("FootballClub");
        builder.setZipCode(99);
        builder.setPreferredSports(preferredSports3);
        User RegularGuy= builder.build();
        return Arrays.asList(JamesBond,MohammedAli,RegularGuy);
    }
    private static String buildTestString(){
       return "007|James|Bond|NotInfiltrated|notfakemail@gmail.com|0000077777|5|London|700|MARTIALARTS, CLIMBING, CRICKET, JUDO, GOLF, SURFING, WRESTLING[u]1|Mohammad|Ali|TheGoat|IamTheDancingMaster@gmail.com|0000000001|5|Ring|1|BOXING[u]999|Regular|Guy|BasicGuy|ILoveBallSports@gmail.com|999999999|3|FootballClub|99|FOOTBALL, BASKETBALL";
    }

    @Test
    public void convertListOfUsersConvertsCorrectly(){
        System.out.println(UserConverter.convertListOfUsers(users));
    }




}
