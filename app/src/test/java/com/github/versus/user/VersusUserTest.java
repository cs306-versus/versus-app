package com.github.versus.user;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link VersusUser}
 */
public final class VersusUserTest {

    // ======================================= USER'S DATA =========================================
    private static final String UID        = "";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME  = "Doe";
    private static final String USERNAME   = "test_user";
    private static final String MAIL       = "test@versus.ch";
    private static final String PHONE      = "+41782345678";
    private static final int RATING        = 5;
    private static final String CITY       = "Lausanne";
    private static final int ZIP_CODE      = 1234;

    // ============================================================================================

    private User user;

    @Before
    public void buildUser(){
        VersusUser.Builder builder = new VersusUser.Builder(UID);
        builder.setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .setUserName(USERNAME)
                .setMail(MAIL)
                .setPhone(PHONE)
                .setRating(RATING)
                .setCity(CITY)
                .setZipCode(ZIP_CODE);
        user = builder.build();
    }

    @Test
    public void testUid(){
        assertEquals(UID, user.getUID());
    }

    @Test
    public void testFirstName(){
        assertEquals(FIRST_NAME, user.getFirstName());
    }

    @Test
    public void testLastName(){
        assertEquals(LAST_NAME, user.getLastName());
    }

    @Test
    public void testUserName(){
        assertEquals(USERNAME, user.getUserName());
    }

    @Test
    public void testMail(){
        assertEquals(MAIL, user.getMail());
    }

    @Test
    public void testPhone(){
        assertEquals(PHONE, user.getPhone());
    }

    @Test
    public void testRating(){
        assertEquals(RATING, user.getRating());
    }

    @Test
    public void testCity(){
        assertEquals(CITY, user.getCity());
    }

    @Test
    public void testZip(){
        assertEquals(ZIP_CODE, user.getZipCode());
    }

    @Test
    public void testEqualsWithDifferentType(){
        assertNotEquals(user, new Object());
    }

    @Test
    public void testWithSameUID(){
        VersusUser.Builder builder = new VersusUser.Builder(UID);
        assertEquals(user, builder.build());
    }

}