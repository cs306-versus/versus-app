package com.github.versus.posts;

import com.github.versus.rating.Rating;
import com.github.versus.sports.Sport;
import com.github.versus.user.DummyUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Post in the Versus app
 */
public class Post {

    private  String title;
    private  Timestamp date;
    private  Location location;
    private List<DummyUser> players;
    private int playerLimit;
    private Sport sport ;

    private String CreatorId;

    private List<Rating> ratings;

    public Post(String title, Timestamp date, Location location, List<DummyUser> players, int playerLimit, Sport sport, List<Rating> ratings) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.players = players;
        this.playerLimit = playerLimit;
        this.sport = sport;
        this.ratings = ratings;
    }
    public Post(String title, Timestamp date, Location location, List<DummyUser> players, int playerLimit, Sport sport) {
        this(title, date, location, players, playerLimit, sport, new ArrayList<>());
    }

    public Post(){

    }
    /**
     *
     * @return title of the post
     */
    public String getTitle(){
        return title;
    }




    /**
     *
     * @return date of the post
     */
    public Timestamp getDate(){
        return date;
    }

    /**
     *
     * @return The location of the game
     */
    public Location getLocation(){
        return location;
    }

    /**
     * @return the users who joined the post
     */
    public List<DummyUser> getPlayers() {
        return players;
    }

    /**
     * @return the limit of players that could join the post
     */
    public int getPlayerLimit() {
        return playerLimit;
    }

    /**
     * @return the sport of the post
     */
    public Sport getSport() {
        return sport;
    }
    /**
     * @return the player to player ratings of the game
     */
    public List<Rating> getRatings() {
        return ratings;
    }
    /**
     * @return all the attributes of the post in a map fashion
     */
    public Map<String, Object> getAllAttributes() {
        Map<String, Object> res =  new HashMap<String, Object>();
        res.put("title", title );
        res.put("date", date);
        res.put("location",location) ;
        res.put("playerLimit", playerLimit);
        res.put("players", players);
        res.put("sport", sport);
        res.put("ratings", ratings);
        return res;
    }


    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Post)) {
            return false;
        }

        Post other = (Post) obj;
        return this.title.equals(other.title)
                && this.date.equals(other.date)
                && this.location.equals(other.location)
                && this.players.equals(other.players)
                && this.playerLimit == other.playerLimit
                && this.sport.equals(other.sport);
    }


}