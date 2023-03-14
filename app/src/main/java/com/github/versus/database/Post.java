package com.github.versus.database;

import com.github.versus.sports.Sport;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Post in the Versus app
 */
public class Post {

    private final String title;
    private final Timestamp date;
    private final Location location;
    private List<User> players;
    private int playerLimit;
    private final Sport sport ;
    public Post(String title, Timestamp date, Location location, List<User> players, int playerLimit, Sport sport) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.players = players;
        this.playerLimit = playerLimit;
        this.sport = sport;
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
    public List<User> getPlayers() {
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
         return res;

    }


}
