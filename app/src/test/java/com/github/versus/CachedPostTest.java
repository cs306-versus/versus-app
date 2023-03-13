package com.github.versus;

import org.junit.Test;

import static org.junit.Assert.*;

import com.github.versus.offline.CachedPost;
import com.github.versus.offline.SimpleTestPost;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;

import java.time.Month;
import java.util.Calendar;


import static com.github.versus.offline.CachedPost.match;
import static com.github.versus.offline.CachedPost.postIsInvalid;
import static com.github.versus.offline.CachedPost.computeID;

public class CachedPostTest {
    @Test
    public void postIsInvalidWithNullPost() {
        assertTrue(CachedPost.postIsInvalid(null));
    }

    @Test
    public void postIsInvalidWithNullTitle() {

        Post post= SimpleTestPost.postWith(null,
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR)
                        , Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne", 10, 10),10);

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
    public void emptyCachedPostHasCorrectID(){
        assertTrue(new CachedPost().id.equals(CachedPost.emptyID));
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
        assertTrue(computeID(null).equals(CachedPost.emptyID));
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





}
