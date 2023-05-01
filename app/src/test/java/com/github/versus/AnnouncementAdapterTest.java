package com.github.versus;

import static org.junit.Assert.assertEquals;

import android.view.ViewGroup;

import com.github.versus.announcements.AnnouncementAdapter;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.sports.Sport;
import com.github.versus.user.DummyUser;

import org.junit.Test;

import java.time.Month;
import java.util.ArrayList;
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
            return Sport.ROCKCLIMBING;
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
        assertEquals(0, new AnnouncementAdapter(new ArrayList<>()).getItemCount());
        ArrayList<Post> a = new ArrayList();
        a.add(new Post());
        assertEquals(1, new AnnouncementAdapter(a).getItemCount());
    }
    @Test(expected = NullPointerException.class)
    public void onCreateViewHolderNull(){
        ArrayList<Post> a = new ArrayList<>();

        a.add(validPost);
      new AnnouncementAdapter(a).onCreateViewHolder(null, 0);
    }

}
