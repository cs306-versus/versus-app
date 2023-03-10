package com.github.versus.offline;

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

    @PrimaryKey
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

    private CachedPost(Post post){

        id= String.valueOf(post.hashCode());
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

    }
    public static CachedPost match(Post post){
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


}
