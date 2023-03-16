package com.github.versus.posts;

import com.github.versus.sports.Sport;

import java.util.List;
import java.util.Map;

public class FakePost implements Post{

    private final String title;
    private final Timestamp date;
    private final Location location;
    private final List<Object> players;
    private final int playerLimit;
    private final Sport sport;

    public FakePost(String title, Sport sport, Timestamp date, Location location, List<Object> players, int playerLimit) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.players = players;
        this.playerLimit = playerLimit;
        this.sport = sport;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Sport getSport(){ return sport; }
    @Override
    public Timestamp getDate() {
        return date;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public List<Object> getPlayers() {
        return players;
    }

    @Override
    public int getPlayerLimit() {
        return playerLimit;
    }

    @Override
    public Map<String, Object> getAllAttributes() {
        return null;
    }
}
