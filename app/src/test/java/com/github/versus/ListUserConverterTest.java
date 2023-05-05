package com.github.versus;

import com.github.versus.offline.UserConverter;
import com.github.versus.sports.Sport;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;

import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

public class ListUserConverterTest {
    private static String strUsers= buildTestString();

    private static List<VersusUser> users = buildTestUsers();

    private static List<VersusUser> reverted= UserConverter.convertBackListOfUsers(strUsers);

    private static List<VersusUser> buildTestUsers() {
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
        VersusUser JamesBond=   builder.build();

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
        VersusUser MohammedAli= builder.build();

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
        VersusUser RegularGuy= builder.build();
        return Arrays.asList(JamesBond,MohammedAli,RegularGuy);
    }
    private static String buildTestString(){
       return "007|James|Bond|NotInfiltrated|notfakemail@gmail.com|0000077777|5|London|700|MARTIALARTS, CLIMBING, CRICKET, JUDO, GOLF, SURFING, WRESTLING[u]1|Mohammad|Ali|TheGoat|IamTheDancingMaster@gmail.com|0000000001|5|Ring|1|BOXING[u]999|Regular|Guy|BasicGuy|ILoveBallSports@gmail.com|999999999|3|FootballClub|99|FOOTBALL, BASKETBALL";
    }

    @Test
    public void convertListOfUsersConvertsCorrectly(){
        assertTrue(UserConverter.convertListOfUsers(users).equals(strUsers));
    }

    @Test
    public void convertBackListUsersYieldsCorrectUID(){
        boolean uid0=(users.get(0).getUID().equals(reverted.get(0).getUID()));
        boolean uid1=(users.get(1).getUID().equals(reverted.get(1).getUID()));
        boolean uid2=(users.get(2).getUID().equals(reverted.get(2).getUID()));
        assertTrue(uid0 && uid1 && uid2);
    }

    @Test
    public void convertBackListUsersYieldsCorrectFirstName(){
        boolean u0=(users.get(0).getFirstName().equals(reverted.get(0).getFirstName()));
        boolean u1=(users.get(1).getFirstName().equals(reverted.get(1).getFirstName()));
        boolean u2=(users.get(2).getFirstName().equals(reverted.get(2).getFirstName()));
        assertTrue(u0 && u1 && u2);
    }
    @Test
    public void convertBackListUsersYieldsCorrectLastName(){
        boolean u0=(users.get(0).getLastName().equals(reverted.get(0).getLastName()));
        boolean u1=(users.get(1).getLastName().equals(reverted.get(1).getLastName()));
        boolean u2=(users.get(2).getLastName().equals(reverted.get(2).getLastName()));
        assertTrue(u0 && u1 && u2);
    }

    @Test
    public void convertBackListUsersYieldsCorrectMail(){
        boolean u0=(users.get(0).getMail().equals(reverted.get(0).getMail()));
        boolean u1=(users.get(1).getMail().equals(reverted.get(1).getMail()));
        boolean u2=(users.get(2).getMail().equals(reverted.get(2).getMail()));
        assertTrue(u0 && u1 && u2);
    }

    @Test
    public void convertBackListUsersYieldsCorrectPhone(){
        boolean u0=(users.get(0).getPhone().equals(reverted.get(0).getPhone()));
        boolean u1=(users.get(1).getPhone().equals(reverted.get(1).getPhone()));
        boolean u2=(users.get(2).getPhone().equals(reverted.get(2).getPhone()));
        assertTrue(u0 && u1 && u2);
    }

    @Test
    public void convertBackListUsersYieldsCorrectRating(){
        boolean u0=(users.get(0).getRating()==(reverted.get(0).getRating()));
        boolean u1=(users.get(1).getRating()==(reverted.get(1).getRating()));
        boolean u2=(users.get(2).getRating()==(reverted.get(2).getRating()));
        assertTrue(u0 && u1 && u2);
    }

    @Test
    public void convertBackListUsersYieldsCorrectCity(){
        boolean u0=(users.get(0).getCity().equals(reverted.get(0).getCity()));
        boolean u1=(users.get(1).getCity().equals(reverted.get(1).getCity()));
        boolean u2=(users.get(2).getCity().equals(reverted.get(2).getCity()));
        assertTrue(u0 && u1 && u2);
    }
    @Test
    public void convertBackListUsersYieldsCorrectZIP(){
        boolean u0=(users.get(0).getZipCode()==(reverted.get(0).getZipCode()));
        boolean u1=(users.get(1).getZipCode()==(reverted.get(1).getZipCode()));
        boolean u2=(users.get(2).getZipCode()==(reverted.get(2).getZipCode()));
        assertTrue(u0 && u1 && u2);
    }
    @Test
    public void convertBackListUsersYieldsCorrectPreferredSports(){
        boolean u0=(users.get(0).getPreferredSports().equals(reverted.get(0).getPreferredSports()));
        boolean u1=(users.get(1).getPreferredSports().equals(reverted.get(1).getPreferredSports()));
        boolean u2=(users.get(2).getPreferredSports().equals(reverted.get(2).getPreferredSports()));
        assertTrue(u0 && u1 && u2);
    }


    @Test
    public void convertBackListUsersFailsOnEmptyString(){
        assertThrows(Exception.class,()-> UserConverter.convertBackListOfUsers(""));
    }
}
