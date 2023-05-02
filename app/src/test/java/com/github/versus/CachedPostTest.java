package com.github.versus;

import static com.github.versus.offline.CachedPost.computeID;
import static com.github.versus.offline.CachedPost.match;
import static com.github.versus.offline.CachedPost.postIsInvalid;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.versus.offline.CachedPost;
import com.github.versus.offline.SimpleTestPost;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.sports.Sport;
import com.github.versus.user.DummyUser;
import com.github.versus.user.VersusUser;

import org.junit.Test;

import java.time.Month;
import java.util.Calendar;

public class CachedPostTest {
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
    public void postIsInvalidWithValidPost() {
        Post post = new SimpleTestPost();
        assertFalse(postIsInvalid(post));
    }
    @Test
    public void postWithNoSportIsInvalid(){
        Post post = SimpleTestPost.postWith("Invalid post",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10,null);
        assertTrue(postIsInvalid(post));
    }

    @Test
    public void emptyCachedPostHasCorrectID(){
        assertTrue(new CachedPost().id.equals(CachedPost.EMPTY_ID));
    }

    @Test
    public void validCachedPostIsNotEmpty(){
        Post post = new SimpleTestPost();
        assertFalse(match(post).isEmpty);
    }
    @Test
    public void postMatchWithInvalidPost(){
        assertTrue(CachedPost.match(null).isEmpty);
    }

    @Test
    public void computeIDWithEmptyPost(){
        assertTrue(computeID(null).equals(CachedPost.EMPTY_ID));
    }
    @Test
    public void computeIDWithValidPost(){
        Post post = new SimpleTestPost();
        assertTrue(computeID(post).equals(String.valueOf(post.getTitle().hashCode())));

        }

    @Test
    public void revertValidPostsGivesBackTheSameFields(){
        SimpleTestPost post = new SimpleTestPost();
        CachedPost cached = CachedPost.match(post);
        assertTrue(post.equals(cached.revert()));
    }

    @Test
    public void matchPreservesUserId(){
        VersusUser user = new VersusUser.Builder("uid").build();
        Post post = SimpleTestPost.postWith("Invalid post",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, user);
        CachedPost cached = CachedPost.match(post);
        assertTrue(cached.uid.equals(user.getUID()));
    }
    @Test
    public void matchDoesNotIntroduceInconsistentId(){
        Post post = SimpleTestPost.postWith("Invalid post",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER);
        CachedPost cached = CachedPost.match(post);
        assertTrue(cached.uid==null);
    }

    @Test
    public void revertPreservesUserId(){
        VersusUser user = new VersusUser.Builder("uid").build();
        Post post = SimpleTestPost.postWith("Invalid post",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, user);
        CachedPost cached = CachedPost.match(post);
        Post reverted = cached.revert();
        assertTrue(reverted.getPlayers().get(0).equals(user));
    }

    @Test
    public void revertDoesNotIntroduceInconsistentId(){
        Post post = SimpleTestPost.postWith("Invalid post",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER);
        CachedPost cached = CachedPost.match(post);
        Post reverted = cached.revert();
        assertTrue(reverted.getPlayers().isEmpty());
    }






}
