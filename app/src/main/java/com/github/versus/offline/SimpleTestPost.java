package com.github.versus.offline;

import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.sports.Sport;
import com.github.versus.user.DummyUser;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * A class designed for testing the Cache manager
 */
public final class SimpleTestPost extends Post {
    /**
     * Creates an instance of the simple post with a default title
     */
    public SimpleTestPost(){
      this("Invalid Post");
    }

    /**
     ** Creates a simple test instance with the given title
     * @param title: Title of the simple post
     */
    public SimpleTestPost(String title){
        super(title,new Timestamp(Calendar.getInstance().get(Calendar.YEAR),
                Month.values()[0], 1, 8, 1, Timestamp.Meridiem.PM)
                ,new Location("Lausanne", 10, 10),new ArrayList<>(),10,Sport.FOOTBALL, "0");

    }

    /**
     * Creates an simple post instance with the given parameters
     * @param title: Title of the post
     * @param timestamp: Date of the post
     * @param location: Location of the post
     * @param limit: player limit of the post
     * @return the created post
     */

    public static Post postWith(String title,Timestamp timestamp,Location location, int limit){
       return  new Post(title,timestamp,location,new ArrayList<>(),limit, Sport.FOOTBALL, "0");
    }

    /**
     * Creates an simple post instance with the given parameters
     * @param title: Title of the post
     * @param timestamp: Date of the post
     * @param location: Location of the post
     * @param limit: player limit of the post
     * @param sport: The sport to be played
     * @return The created post
     */
    public static Post postWith(String title,Timestamp timestamp,Location location, int limit,Sport sport){
        return  new Post(title,timestamp,location,new ArrayList<>(),limit, sport, "0");
    }

    public static Post postWith(String title,Timestamp timestamp,Location location, int limit,Sport sport,VersusUser creator){

        return new Post(title,timestamp,location, Arrays.asList(creator),limit, sport, creator.getUID());
    }

    public static Post postWith(String title,Timestamp timestamp,Location location, int limit,Sport sport,List<VersusUser> players){

        return new Post(title,timestamp,location, players,limit, sport, players.get(0).getUID());
    }
}
