package com.github.versus.posts;

import com.github.versus.sports.Sport;
import com.github.versus.user.DummyUser;
import com.github.versus.user.User;

import java.util.List;
import java.util.Map;

public class FakePost extends Post{

    private final String title;
    private final Timestamp date;
    private final Location location;
    private final List<DummyUser> players;
    private final int playerLimit;
    private final Sport sport;

    public FakePost(String title, Sport sport, Timestamp date, Location location, List<DummyUser> players, int playerLimit) {
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
    public List<DummyUser> getPlayers() {
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
