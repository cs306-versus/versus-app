package com.github.versus.offline;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.sports.Sport;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//TODO: Use Class Converter instead of dumb caching
/*
 Class that represents how posts are cached in memory
 */
@Entity
public final class CachedPost {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    public String id;

    @ColumnInfo(name = "title")
    public  String title;
    @ColumnInfo(name = "limit")
    public  int limit;

    @ColumnInfo(name = "sport")
    public String sport;

    @ColumnInfo(name = "userID")
    public String uid;

    @ColumnInfo(name= "location")
    public String location;

    @ColumnInfo(name= "timestamp")
    public String timestamp;

    @ColumnInfo(name= "players")
    public String players;

    public  static final String INVALID_ID= "INVALID";

    /**
     * Creates a cached post that matches the given parameter post
     * @param post
     * @return
     */
    public static CachedPost match(Post post){
        if(postIsInvalid(post)) {
            return null;
        }
        CachedPost data= new CachedPost();
        data.id= computeID(post);
        data.title= post.getTitle();
        data.uid= post.getUid();
        data.limit= post.getPlayerLimit();
        data.location= LocationConverter.convertLocation(post.getLocation());
        data.timestamp= TimeStampConverter.convertTimeStamp(post.getDate());
        data.sport= SportConverter.convertSport(post.getSport());
        data.players= UserConverter.convertListOfUsers(post.getPlayers());
        return data;
    }

    /**
     * Reverts back a cached post
     * @return
     * The equivalent post.
     */

    public Post revert(){
        Timestamp timestamp= TimeStampConverter.convertBackToTimeStamp(this.timestamp);
        Location location = LocationConverter.convertBackLocation(this.location);
        List<VersusUser> players= UserConverter.convertBackListOfUsers(this.players);
        return new Post(title, timestamp, location,players, limit, Sport.valueOf(sport), this.uid);
    }

    /**
     * Computes the id of the post
     * @param post
     * @return
     * The String id of the post, which is the primary key in the db.
     */
    public static String computeID(Post post){
        if(postIsInvalid(post)){
            return INVALID_ID;
        }
        return String.valueOf(post.getTitle().hashCode());
    }

    /**
     * Computes if a post is valid and can be cached
     * @param post
     * @return
     * true iff the post is valid
     */
    public static boolean postIsInvalid(Post post){
        return post==null||post.getTitle()==null||post.getLocation()==null
                ||post.getDate()==null || post.getSport()==null
                || post.getPlayers()==null || post.getPlayers().isEmpty()
                || post.getUid()==null ;
    }

}
