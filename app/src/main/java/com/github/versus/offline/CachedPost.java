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
