package com.github.versus.offline;

import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;

import java.time.Month;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public final class SimpleTestPost implements Post {
    @Override
    public String getTitle() {
        return "Valid Post";
    }

    @Override
    public Timestamp getDate() {
        return new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.values()[0], 1, 8, 1, Timestamp.Meridiem.PM);
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

    public boolean equals(Post that){
        return this.getTitle().equals(that.getTitle()) &&
                this.getDate().equals(that.getDate())  &&
                this.getLocation().equals(that.getLocation()) &&
                this.getPlayerLimit()== that.getPlayerLimit();
    }

    public static Post postWith(String title,Timestamp timestamp,Location location, int limit){
       return  new Post() {
            @Override
            public String getTitle() {
                return title ;
            }

            @Override
            public Timestamp getDate() {
                return timestamp;
            }

            @Override
            public Location getLocation() {
                return location;
            }

            @Override
            public List<Object> getPlayers() {
                return null;
            }

            @Override
            public int getPlayerLimit() {
                return limit;
            }

            @Override
            public Map<String, Object> getAllAttributes() {
                return null;
            }
        };
    }
}
