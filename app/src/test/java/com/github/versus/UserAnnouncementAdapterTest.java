package com.github.versus;

import static org.junit.Assert.assertEquals;

import com.github.versus.announcements.PostAnnouncementAdapter;
import com.github.versus.friends.PlayerAnnouncementAdapter;
import com.github.versus.friends.UserAnnouncementAdapter;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.sports.Sport;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;

import org.junit.Test;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public final class UserAnnouncementAdapterTest {

    private final Post validPost = new Post() {
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
        public List<VersusUser> getPlayers() {
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
    public void createAdapterNullPosts() {
        UserAnnouncementAdapter aa = new UserAnnouncementAdapter(null,null, null, null);
    }

    @Test
    public void createAdapterPostsLength() {
        assertEquals(0, new UserAnnouncementAdapter(new ArrayList<>(), null, null, null).getItemCount());
        ArrayList<User> a = new ArrayList();
        a.add(new VersusUser());
        assertEquals(1, new UserAnnouncementAdapter(a, null, null, null).getItemCount());
    }

    @Test(expected = NullPointerException.class)
    public void onCreateViewHolderNull() {
        ArrayList<User> a = new ArrayList<>();
        a.add(new VersusUser());
        new UserAnnouncementAdapter(a, null,  null, null).onCreateViewHolder(null, 0);
    }

}
