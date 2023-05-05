package com.github.versus;

import static com.github.versus.offline.CachedPost.computeID;
import static com.github.versus.offline.CachedPost.match;
import static com.github.versus.offline.CachedPost.postIsInvalid;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.versus.offline.CachedPost;
import com.github.versus.offline.LocationConverter;
import com.github.versus.offline.SimpleTestPost;
import com.github.versus.offline.TimeStampConverter;
import com.github.versus.offline.UserConverter;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.sports.Sport;
import com.github.versus.user.DummyUser;
import com.github.versus.user.VersusUser;

import org.junit.Test;

import java.time.Month;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class CachedPostTest {
    private static List<VersusUser> users = buildTestUsers();
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
    @Test
    public void postIsInvalidWithNullPost() {
        assertTrue(CachedPost.postIsInvalid(null));
    }

    @Test
    public void postIsInvalidWithNullTitle() {

        Post post= new SimpleTestPost(null);
        assertTrue(postIsInvalid(post));
    }

    @Test
    public void postIsInvalidWithNullLocation() {

        Post post = SimpleTestPost.postWith("Invalid post",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                null,10);
        assertTrue(CachedPost.postIsInvalid(post));
    }

    @Test
    public void postIsInvalidWithNullDate() {
        Post post= SimpleTestPost.postWith("Invalid Post",null,
                new Location("Lausanne", 10, 10),10);
        assertTrue(CachedPost.postIsInvalid(post));
    }

    @Test
    public void postWithNoSportIsInvalid(){
        Post post = SimpleTestPost.postWith("Invalid post",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10,null);
        assertTrue(postIsInvalid(post));
    }

    @Test
    public void postWithNoPlayerIsInvalid(){
        Post post = SimpleTestPost.postWith("Invalid post",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER);
        assertTrue(postIsInvalid(post));
    }


    @Test
    public void postMatchWithInvalidPost(){
        assertTrue(CachedPost.match(null)==null);
    }

    @Test
    public void computeIDWithEmptyPost(){
        assertTrue(computeID(null).equals(CachedPost.INVALID_ID));
    }
    @Test
    public void computeIDWithInvalidPost(){
        Post post = new SimpleTestPost();
        assertTrue(computeID(post).equals(CachedPost.INVALID_ID));

        }
    @Test
    public void computeIDWithValidPost(){
        Post post = SimpleTestPost.postWith("Valid",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, users.get(0));
        assertTrue(CachedPost.computeID(post).equals(String.valueOf(post.getTitle().hashCode())));
    }

    @Test
    public void matchComputesCorrectUsers(){
        Post post = SimpleTestPost.postWith("Valid post",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, users);
        CachedPost data = CachedPost.match(post);
        assertTrue(data.players.equals(UserConverter.convertListOfUsers(users)));
    }

    @Test
    public void matchComputesCorrectTimeStamp(){
        Post post = SimpleTestPost.postWith("Valid post",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, users);
        CachedPost data = CachedPost.match(post);
        assertTrue(data.timestamp.equals(TimeStampConverter.convertTimeStamp(post.getDate())));
    }

    @Test
    public void matchComputesCorrectLocation(){
        Post post = SimpleTestPost.postWith("Valid post",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, users);
        CachedPost data = CachedPost.match(post);
        assertTrue(data.location.equals(LocationConverter.convertLocation(post.getLocation())));
    }

    @Test
    public void revertWorksGivesBackOriginalPost(){
        Post post = SimpleTestPost.postWith("Valid post",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, users);
        CachedPost data = CachedPost.match(post);
        assertTrue(data.revert().equals(post));
    }

}
