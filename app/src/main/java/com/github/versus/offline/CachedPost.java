package com.github.versus.offline;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;

import java.time.Month;
import java.util.List;
import java.util.Map;

//TODO: Use Class Converter instead of dumb caching

@Entity
public final class CachedPost {

    @PrimaryKey @NonNull
    public final String id;
    @ColumnInfo(name = "title")
    public final String title;

    @ColumnInfo(name = "limit")
    public final int limit;

    @ColumnInfo(name = "locationName")
    public final String locationName;

    @ColumnInfo(name = "latitude")
    public final double latitude;

    @ColumnInfo(name = "longitude")
    public final double longitude;

    @ColumnInfo(name = "year")
    public final int year;
    @ColumnInfo(name = "month")
    public final String month;

    @ColumnInfo(name = "day")
    public final int day;

    @ColumnInfo(name = "hour")
    public final int hour;

    @ColumnInfo(name = "minutes")
    public final int minutes;

    @ColumnInfo(name = "seconds")
    public final int seconds;

    @ColumnInfo(name = "meridiem")
    public final String  meridiem;


    @ColumnInfo(name = "empty")
    public final boolean  isEmpty;
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
        meridiem= timestamp.getM().name();
        isEmpty= false;

    }
    public CachedPost(){
        isEmpty= true;
        id= "Empty post";
        title= null;
        limit= -1;
        Location location= null;
        locationName= null;
        latitude=-1;
        longitude= -1;
        year=  -1;
        month= null;
        day= -1;
        hour= -1;
        minutes= -1;
        seconds= -1;
        meridiem= null;
    }

    public static CachedPost match(Post post){
        if(post==null||post.getLocation()==null||post.getDate()==null) {
            return new CachedPost();
        }
        return new CachedPost(post);
    }

    public Post revert(){
        return new Post() {
            @Override
            public String getTitle() {
                return title;
            }

            @Override
            public Timestamp getDate() {
                return new Timestamp(year,Month.valueOf(month),day,hour,minutes, Timestamp.Meridiem.valueOf(meridiem));
            }

            @Override
            public Location getLocation() {
                return new Location(locationName,latitude,longitude);
            }

            @Override
            public List<Object> getPlayers() {
                 throw new RuntimeException("Not implemented");
            }

            @Override
            public int getPlayerLimit() {
                return limit;
            }

            @Override
            public Map<String, Object> getAllAttributes() {
                throw  new RuntimeException("Not Implemented");
            }
        };
    }

    public static String computeID(Post post){
        return String.valueOf(post.hashCode());
    }

}
