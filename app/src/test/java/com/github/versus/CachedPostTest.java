package com.github.versus;

import static com.github.versus.offline.CachedPost.computeID;
import static com.github.versus.offline.CachedPost.postIsInvalid;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
import com.github.versus.user.VersusUser;

import org.junit.Test;

import java.time.Month;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public final class CachedPostTest {
    private static final List<VersusUser> users = buildTestUsers();

    private static List<VersusUser> buildTestUsers() {
        List<Sport> preferredSports1 = Arrays.asList(Sport.MARTIALARTS, Sport.CLIMBING, Sport.CRICKET,
                Sport.JUDO, Sport.GOLF, Sport.SURFING, Sport.WRESTLING);
        List<Sport> preferredSports2 = List.of(Sport.BOXING);
        List<Sport> preferredSports3 = Arrays.asList(Sport.FOOTBALL, Sport.BASKETBALL);

        VersusUser.VersusBuilder builder;
        builder = new VersusUser.VersusBuilder("007");
        builder.setFirstName("James");
        builder.setLastName("Bond");
        builder.setUserName("NotInfiltrated");
        builder.setMail("notfakemail@gmail.com");
        builder.setPhone("0000077777");
        builder.setRating(5);
        builder.setCity("London");
        builder.setZipCode(700);
        builder.setPreferredSports(preferredSports1);
        VersusUser JamesBond = builder.build();

        builder = new VersusUser.VersusBuilder("1");
        builder.setFirstName("Mohammad");
        builder.setLastName("Ali");
        builder.setUserName("TheGoat");
        builder.setMail("IamTheDancingMaster@gmail.com");
        builder.setPhone("0000000001");
        builder.setRating(5);
        builder.setCity("Ring");
        builder.setZipCode(1);
        builder.setPreferredSports(preferredSports2);
        VersusUser MohammedAli = builder.build();

        builder = new VersusUser.VersusBuilder("999");
        builder.setFirstName("Regular");
        builder.setLastName("Guy");
        builder.setUserName("BasicGuy");
        builder.setMail("ILoveBallSports@gmail.com");
        builder.setPhone("999999999");
        builder.setRating(3);
        builder.setCity("FootballClub");
        builder.setZipCode(99);
        builder.setPreferredSports(preferredSports3);
        VersusUser RegularGuy = builder.build();
        return Arrays.asList(JamesBond, MohammedAli, RegularGuy);
    }

    @Test
    public void postIsInvalidWithNullPost() {
        assertTrue(CachedPost.postIsInvalid(null));
    }

    @Test
    public void postIsInvalidWithNullTitle() {
        Post post = new SimpleTestPost(null);
        assertTrue(postIsInvalid(post));
    }

    @Test
    public void postIsInvalidWithNullLocation() {
        Post post = SimpleTestPost.postWith("Invalid post",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                null, 10);
        assertTrue(CachedPost.postIsInvalid(post));
    }

    @Test
    public void postIsInvalidWithNullDate() {
        Post post = SimpleTestPost.postWith("Invalid Post", null,
                new Location("Lausanne", 10, 10), 10);
        assertTrue(CachedPost.postIsInvalid(post));
    }

    @Test
    public void postWithNoSportIsInvalid() {
        Post post = SimpleTestPost.postWith("Invalid post",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne", 10, 10), 10, null);
        assertTrue(postIsInvalid(post));
    }

    @Test
    public void postWithNoPlayerIsInvalid() {
        Post post = SimpleTestPost.postWith("Invalid post",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne", 10, 10), 10, Sport.SOCCER);
        assertTrue(postIsInvalid(post));
    }

    @Test
    public void postMatchWithInvalidPost() {
        assertNull(CachedPost.match(null));
    }

    @Test
    public void computeIDWithEmptyPost() {
        assertEquals(CachedPost.INVALID_ID, computeID(null));
    }

    @Test
    public void computeIDWithInvalidPost() {
        Post post = new SimpleTestPost();
        assertEquals(CachedPost.INVALID_ID, computeID(post));
    }

    @Test
    public void computeIDWithValidPost() {
        Post post = SimpleTestPost.postWith("Valid",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne", 10, 10), 10, Sport.SOCCER, users.get(0));
        assertEquals(computeID(post), String.valueOf(post.getTitle().hashCode()));
    }

    @Test
    public void matchComputesCorrectUsers() {
        Post post = SimpleTestPost.postWith("Valid post",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne", 10, 10), 10, Sport.SOCCER, users);
        CachedPost data = CachedPost.match(post);
        assertEquals(data.players, UserConverter.convertListOfUsers(users));
    }

    @Test
    public void matchComputesCorrectTimeStamp() {
        Post post = SimpleTestPost.postWith("Valid post",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne", 10, 10), 10, Sport.SOCCER, users);
        CachedPost data = CachedPost.match(post);
        assertEquals(data.timestamp, TimeStampConverter.convertTimeStamp(post.getDate()));
    }

    @Test
    public void matchComputesCorrectLocation() {
        Post post = SimpleTestPost.postWith("Valid post",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne", 10, 10), 10, Sport.SOCCER, users);
        CachedPost data = CachedPost.match(post);
        assertEquals(data.location, LocationConverter.convertLocation(post.getLocation()));
    }

    @Test
    public void revertWorksGivesBackOriginalPost() {
        Post post = SimpleTestPost.postWith("Valid post",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne", 10, 10), 10, Sport.SOCCER, users);
        CachedPost data = CachedPost.match(post);
        assertEquals(data.revert(), post);
    }

}