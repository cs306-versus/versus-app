package com.github.versus;

import org.junit.Test;

import static org.junit.Assert.*;

import com.github.versus.offline.CachedPost;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;

import java.time.Month;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import static com.github.versus.offline.CachedPost.postIsInvalid;
import static com.github.versus.offline.CachedPost.computeID;

public class CachedPostTest {
    @Test
    public void postIsInvalidWithNullPost() {
        assertTrue(CachedPost.postIsInvalid(null));
    }

    @Test
    public void postIsInvalidWithNullTitle() {
        Post post = new Post() {
            @Override
            public String getTitle() {
                return null;
            }

            @Override
            public Timestamp getDate() {
                return new Timestamp(2022, Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM);
            }

            @Override
            public Location getLocation() {
                return new Location("Lausanne", 10, 10);
            }

            @Override
            public List<Object> getPlayers() {
                return null;
            }

            @Override
            public int getPlayerLimit() {
                return 10;
            }

            @Override
            public Map<String, Object> getAllAttributes() {
                return null;
            }


        };
        assertTrue(postIsInvalid(post));
    }

    @Test
    public void postIsInvalidWithNullLocation() {
        Post post = new Post() {
            @Override
            public String getTitle() {
                return "Testing Post";
            }

            @Override
            public Timestamp getDate() {
                return new Timestamp(2022, Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM);
            }

            @Override
            public Location getLocation() {
                return null;
            }

            @Override
            public List<Object> getPlayers() {
                return null;
            }

            @Override
            public int getPlayerLimit() {
                return 10;
            }

            @Override
            public Map<String, Object> getAllAttributes() {
                return null;
            }
        };
        assertTrue(CachedPost.postIsInvalid(post));
    }

    @Test
    public void postIsInvalidWithNullDate() {
        Post post = new Post() {
            @Override
            public String getTitle() {
                return "Testing Post";
            }

            @Override
            public Timestamp getDate() {
                return null;
            }

            @Override
            public Location getLocation() {
                return new Location("Lausanne", 10, 10);
            }

            @Override
            public List<Object> getPlayers() {
                return null;
            }

            @Override
            public int getPlayerLimit() {
                return 10;
            }

            @Override
            public Map<String, Object> getAllAttributes() {
                return null;
            }
        };
        assertTrue(CachedPost.postIsInvalid(post));
    }

    @Test
    public void postIsInvalidWithValidPost() {
        Post post = new Post() {
            @Override
            public String getTitle() {
                return "Valid Post";
            }

            @Override
            public Timestamp getDate() {
                return new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY,1,8,1, Timestamp.Meridiem.PM);
            }

            @Override
            public Location getLocation() {
                return new Location("Lausanne", 10, 10);
            }

            @Override
            public List<Object> getPlayers() {
                return null;
            }

            @Override
            public int getPlayerLimit() {
                return 10;
            }

            @Override
            public Map<String, Object> getAllAttributes() {
                return null;
            }


        };
        assertFalse(postIsInvalid(post));

    }

    @Test
    public void emptyCachedPostHasCorrectID(){
        assertTrue(new CachedPost().id.equals(CachedPost.emptyID));
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
        Post post = new Post() {
            @Override
            public String getTitle() {
                return "Valid Post";
            }

            @Override
            public Timestamp getDate() {
                return new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM);
            }

            @Override
            public Location getLocation() {
                return new Location("Lausanne", 10, 10);
            }

            @Override
            public List<Object> getPlayers() {
                return null;
            }

            @Override
            public int getPlayerLimit() {
                return 10;
            }

            @Override
            public Map<String, Object> getAllAttributes() {
                return null;
            }
        };

        assertTrue(computeID(post).equals(String.valueOf(post.hashCode())));

        }

}
