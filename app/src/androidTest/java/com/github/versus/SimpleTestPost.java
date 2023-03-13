package com.github.versus;

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

    public boolean equals(SimpleTestPost that){

        return this.getTitle().equals(that.getTitle()) &&
                this.getDate().equals(that.getDate())  &&
                this.getLocation().equals(that.getLocation()) &&
                this.getPlayerLimit()== that.getPlayerLimit();
    }
}
