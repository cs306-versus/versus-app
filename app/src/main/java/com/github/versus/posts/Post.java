package com.github.versus.posts;

import java.util.List;
import java.util.Map;

/**
 * Post in the Versus app
 */
public interface Post {

    /**
     *
     * @return title of the post
     */
    public String getTitle();



    /**
     *
     * @return date of the post
     */
    public Timestamp getDate();

    /**
     *
     * @return The location of the game
     */
    public Location getLocation();

    /**
     /*TODO : Object needs to be changed to user once the interface is available
     * @return the users who joined the post
     */
    public List<Object> getPlayers();


    /**
     *
     * @return the limit of players that could join the post
     */
    public int getPlayerLimit();

    /**
     *
     * @return all the attributes of the post in a map fashion
     */
    public Map<String, Object> getAllAttributes();

}
