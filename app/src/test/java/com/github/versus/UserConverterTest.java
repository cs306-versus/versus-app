package com.github.versus;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import com.github.versus.offline.CachedPost;
import com.github.versus.offline.UserConverter;
import com.github.versus.sports.Sport;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserConverterTest {
   private static String strUser= "007|James|Bond|NotInfiltrated|notfakemail.@gmail.com|0000077777|5|London|7|BOXING, CLIMBING, CRICKET, BASKETBALL, FOOTBALL";

   private static User user = buildTestUser();

    private static User buildTestUser() {
        List<Sport> preferredSports= Arrays.asList(Sport.BOXING,Sport.CLIMBING,Sport.CRICKET,
                Sport.BASKETBALL,Sport.FOOTBALL);
        VersusUser.Builder builder = new VersusUser.Builder("007");
        builder.setFirstName("James");
        builder.setLastName("Bond");
        builder.setUserName("NotInfiltrated");
        builder.setMail("notfakemail.@gmail.com");
        builder.setPhone("0000077777");
        builder.setRating(5);
        builder.setCity("London");
        builder.setZipCode(007);
        builder.setPreferredSports(preferredSports);
        return  builder.build();
    }

    @Test
public void convertUserConvertsCorrectly(){
    assertTrue(UserConverter.convertUser(user).equals(strUser));
}

}
