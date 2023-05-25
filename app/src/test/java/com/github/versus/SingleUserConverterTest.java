package com.github.versus;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.github.versus.offline.UserConverter;
import com.github.versus.sports.Sport;
import com.github.versus.user.VersusUser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SingleUserConverterTest {
   private static String strUser= buildTestString();

   private static VersusUser user = buildTestUser();

   private static VersusUser reverted= UserConverter.convertBackUser(strUser);

   private static VersusUser buildTestUser() {
        List<Sport> preferredSports= Arrays.asList(Sport.BOXING,Sport.CLIMBING,Sport.CRICKET,
                Sport.BASKETBALL,Sport.FOOTBALL);
        VersusUser.VersusBuilder builder = new VersusUser.VersusBuilder("007");
        builder.setFirstName("James");
        builder.setLastName("Bond");
        builder.setUserName("NotInfiltrated");
        builder.setMail("notfakemail@gmail.com");
        builder.setPhone("0000077777");
        builder.setRating(5);
        builder.setCity("London");
        builder.setZipCode(700);
        builder.setPreferredSports(preferredSports);
        return  builder.build();
    }
    private static String buildTestString(){
       return "007|James|Bond|NotInfiltrated|notfakemail@gmail.com|0000077777|5|London|700|BOXING, CLIMBING, CRICKET, BASKETBALL, FOOTBALL";
    }

    @Test
      public void convertUserConvertsCorrectly(){
        assertTrue(UserConverter.convertUser(user).equals(strUser));
    }

    @Test
    public void convertBackUserYieldsCorrectUID(){
        assertTrue(user.getUID().equals(reverted.getUID()));
    }

    @Test
    public void convertBackUserYieldsCorrectFirstName(){
        assertTrue(user.getFirstName().equals(reverted.getFirstName()));
    }

    @Test
    public void convertBackUserYieldsCorrectLastName(){
        assertTrue(user.getLastName().equals(reverted.getLastName()));
    }

    @Test
    public void convertBackUserYieldsCorrectUserName(){
        assertTrue(user.getUserName().equals(reverted.getUserName()));
    }

    @Test
    public void convertBackUserYieldsCorrectMail(){
        assertTrue(user.getMail().equals(reverted.getMail()));
    }

    @Test
    public void convertBackUserYieldsCorrectPhone(){
        assertTrue(user.getPhone().equals(reverted.getPhone()));
    }

    @Test
    public void convertBackUserYieldsCorrectRating(){
        assertTrue(user.getRating()==(reverted.getRating()));
    }

    @Test
    public void convertBackUserYieldsCorrectCity(){
        assertTrue(user.getCity().equals(reverted.getCity()));
    }
    @Test
    public void convertBackUserYieldsCorrectZIP(){
        assertTrue(user.getZipCode()==(reverted.getZipCode()));
    }
    @Test
    public void convertBackUserYieldsCorrectPreferredSports(){
        assertTrue(user.getPreferredSports().equals(reverted.getPreferredSports()));
    }


    @Test
    public void convertEmptyUserFails(){
        assertThrows(Exception.class,()->UserConverter.convertUser(new VersusUser()));
    }

    @Test
    public void convertBackEmptyStringFails(){
        assertThrows(Exception.class,()->UserConverter.convertBackUser(""));
    }

    @Test
    public void canConvertUsersWithEmptySportPreferences(){
        List<Sport> preferredSports= List.of();
        VersusUser.VersusBuilder builder = new VersusUser.VersusBuilder("error");
        builder.setFirstName("Buggy");
        builder.setLastName("User");
        builder.setUserName("I don't like anything");
        builder.setMail("bugs@gmail.com");
        builder.setPhone("that's a problem");
        builder.setRating(1);
        builder.setCity("BugCity");
        builder.setZipCode(700);
        builder.setPreferredSports(preferredSports);
        VersusUser buggyUser= builder.build();
        VersusUser buggyConversion= UserConverter.convertBackUser(UserConverter.convertUser(buggyUser));
        Assert.assertTrue(buggyConversion.getPreferredSports().isEmpty()&&
                                    buggyConversion.getUID().equals(buggyUser.getUID()));
    }


}
