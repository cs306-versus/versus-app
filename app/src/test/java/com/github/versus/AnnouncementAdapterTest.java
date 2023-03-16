package com.github.versus;

import static org.junit.Assert.assertEquals;

import com.github.versus.announcements.AnnouncementAdapter;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.sports.Sport;
import com.github.versus.user.DummyUser;

import org.junit.Test;

import java.time.Month;
import java.util.List;
import java.util.Map;


public class AnnouncementAdapterTest {

    private Post validPost = new Post(){
        @Override
        public String getTitle() {
            return "title";
        }
        @Override
        public Sport getSport() {
            return Sport.ROWING;
        }

        @Override
        public Timestamp getDate() {
            return new Timestamp(2001, Month.values()[4], 5, 5, 35, Timestamp.Meridiem.AM);
        }

        @Override
        public Location getLocation() {
            return null;
        }

        @Override
        public List<DummyUser> getPlayers() {
            return null;
        }

        @Override
        public int getPlayerLimit() {
            return 0;
        }

        @Override
        public Map<String, Object> getAllAttributes() {
            return null;
        }
    };

    @Test(expected = IllegalArgumentException.class)
    public void createAdapterNullPosts(){
        AnnouncementAdapter aa = new AnnouncementAdapter(null);
    }

    @Test
    public void createAdapterPostsLength(){
        assertEquals(0, new AnnouncementAdapter(new Post[]{}).getItemCount());
        assertEquals(1, new AnnouncementAdapter(new Post[]{validPost}).getItemCount());
    }
    @Test(expected = NullPointerException.class)
    public void onCreateViewHolderNull(){
      new AnnouncementAdapter(new Post[]{}).onCreateViewHolder(null, 0);
    }
}
