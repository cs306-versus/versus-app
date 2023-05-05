package com.github.versus.offline;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.sports.Sport;
import com.github.versus.user.DummyUser;
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
    public  String id;
    @ColumnInfo(name = "title")
    public  String title;

    @ColumnInfo(name = "limit")
    public  int limit;

    @ColumnInfo(name = "locationName")
    public  String locationName;

    @ColumnInfo(name = "latitude")
    public  double latitude;

    @ColumnInfo(name = "longitude")
    public  double longitude;

    @ColumnInfo(name = "year")
    public  int year;
    @ColumnInfo(name = "month")
    public  String month;

    @ColumnInfo(name = "day")
    public  int day;

    @ColumnInfo(name = "hour")
    public  int hour;

    @ColumnInfo(name = "minutes")
    public  int minutes;

    @ColumnInfo(name = "seconds")
    public  int seconds;

    @ColumnInfo(name = "meridiem")
    public  String  meridiem;

    @ColumnInfo(name = "empty")
    public  boolean  isEmpty;

    @ColumnInfo(name = "sport")
    public String sport;

    @ColumnInfo(name = "userID")
    public String uid;
    public static final String EMPTY_ID = "Empty";
    private CachedPost(Post post){

        id= computeID(post);
        title= post.getTitle();
        limit= post.getPlayerLimit();
        Location location= post.getLocation();
        locationName= location.getName();
        latitude= location.getLatitude();
        longitude= location.getLongitude();
        Timestamp timestamp= post.getDate();
        year=  timestamp.getYear();
        month= timestamp.getMonth().name();
        day= timestamp.getDay();
        hour= timestamp.getHour();
        minutes= timestamp.getMinutes();
        seconds= timestamp.getSeconds();
        meridiem= timestamp.getMeridiem().name();
        sport= post.getSport().name();
        uid= post.getPlayers().size()==0?null:post.getPlayers().get(0).getUID();
        isEmpty= false;

    }


    /**
     * Creates an empty cached post
     */
    public CachedPost(){
        isEmpty= true;
        id= EMPTY_ID;
    }

    /**
     * Creates a cached post that matches the given parameter post
     * @param post
     * @return
     */
    public static CachedPost match(Post post){
        if(postIsInvalid(post)) {
            return new CachedPost();
        }
        return new CachedPost(post);
    }

    /**
     * Reverts back a cached post
     * @return
     * The equivalent post.
     */

    public Post revert(){
        Timestamp timestamp= new Timestamp(year,Month.valueOf(month),day,hour,minutes, Timestamp.Meridiem.valueOf(meridiem));
        Location location = new Location(locationName,latitude,longitude);
        List<VersusUser> postCreator= new ArrayList<>();
        if(uid!=null) {
            postCreator.add(new VersusUser.Builder(uid).build());
        }
        return new  Post(title, timestamp, location,postCreator,  limit, Sport.valueOf(sport), "");
    }

    /**
     * Computes the id of the post
     * @param post
     * @return
     * The String id of the post, which is the primary key in the db.
     */
    public static String computeID(Post post){
        if(postIsInvalid(post)){
            return EMPTY_ID;
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
        return post==null||post.getTitle()==null||post.getLocation()==null||post.getDate()==null || post.getSport()==null;
    }

}
