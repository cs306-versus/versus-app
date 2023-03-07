package com.github.versus.database;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class FireStorePost implements Post{

    private final String title;
    private final Timestamp date;
    private final Location location;
    private final List<Object> Players;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Timestamp getDate() {
        return null;
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
        return 0;
    }

    @Override
    public Map<String, Object> getAllAttributes() {
        return null;
    }
}
