package com.github.versus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.github.versus.offline.UserConverter;
import com.github.versus.sports.Sport;
import com.github.versus.user.VersusUser;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public final class SingleUserConverterTest {
    private static final String strUser = buildTestString();

    private static final VersusUser user = buildTestUser();

    private static final VersusUser reverted = UserConverter.convertBackUser(strUser);

    private static VersusUser buildTestUser() {
        List<Sport> preferredSports = Arrays.asList(Sport.BOXING, Sport.CLIMBING, Sport.CRICKET,
                Sport.BASKETBALL, Sport.FOOTBALL);
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
        return builder.build();
    }

    private static String buildTestString() {
        return "007|James|Bond|NotInfiltrated|notfakemail@gmail.com|0000077777|5|London|700|BOXING, CLIMBING, CRICKET, BASKETBALL, FOOTBALL";
    }

    @Test
    public void convertUserConvertsCorrectly() {
        assertEquals(UserConverter.convertUser(user), strUser);
    }

    @Test
    public void convertBackUserYieldsCorrectUID() {
        assertEquals(user.getUID(), reverted.getUID());
    }

    @Test
    public void convertBackUserYieldsCorrectFirstName() {
        assertEquals(user.getFirstName(), reverted.getFirstName());
    }

    @Test
    public void convertBackUserYieldsCorrectLastName() {
        assertEquals(user.getLastName(), reverted.getLastName());
    }

    @Test
    public void convertBackUserYieldsCorrectUserName() {
        assertEquals(user.getUserName(), reverted.getUserName());
    }

    @Test
    public void convertBackUserYieldsCorrectMail() {
        assertEquals(user.getMail(), reverted.getMail());
    }

    @Test
    public void convertBackUserYieldsCorrectPhone() {
        assertEquals(user.getPhone(), reverted.getPhone());
    }

    @Test
    public void convertBackUserYieldsCorrectRating() {
        assertEquals(user.getRating(), (reverted.getRating()));
    }

    @Test
    public void convertBackUserYieldsCorrectCity() {
        assertEquals(user.getCity(), reverted.getCity());
    }

    @Test
    public void convertBackUserYieldsCorrectZIP() {
        assertEquals(user.getZipCode(), (reverted.getZipCode()));
    }

    @Test
    public void convertBackUserYieldsCorrectPreferredSports() {
        assertEquals(user.getPreferredSports(), reverted.getPreferredSports());
    }

    @Test
    public void convertEmptyUserFails() {
        assertThrows(Exception.class, () -> UserConverter.convertUser(new VersusUser()));
    }

    @Test
    public void convertBackEmptyStringFails() {
        assertThrows(Exception.class, () -> UserConverter.convertBackUser(""));
    }

}
