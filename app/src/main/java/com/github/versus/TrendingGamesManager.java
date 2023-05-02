package com.github.versus;
import com.github.versus.sports.Sport;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrendingGamesManager {
    private final Duration Threshold = Duration.ofHours(2);
    private LocalTime lastComputedTime;
    private Map<Sport,Integer> postsPerSport;
    private Month currentMonth ;

    public TrendingGamesManager(){
        lastComputedTime=LocalTime.now();
        postsPerSport=new HashMap<>();
        LocalDate currentDate = LocalDate.now();
        currentMonth = currentDate.getMonth();
        computePostsPerMonth();



    }
    private void computePostsPerMonth(){
        // dummy data , this wil be replaced by true values in next sprnt when we connect it to the DB.

        for (Sport sport : Sport.values()) {
            postsPerSport.put(sport,2);
        }
        postsPerSport.put(Sport.FOOTBALL,23);
        postsPerSport.put(Sport.TENNIS,18);
        postsPerSport.put(Sport.BASKETBALL,14);
        postsPerSport.put(Sport.BOXING,6);
        postsPerSport.put(Sport.HANDBALL,10);



    }
    public Sport topSportAtPosition(int position){
        LocalDate currentDate = LocalDate.now();
        Month month = currentDate.getMonth();
        LocalTime time_now = LocalTime.now();
       if(month!=currentMonth || Duration.between(lastComputedTime,time_now).compareTo(Threshold) >0)
       {   computePostsPerMonth();}
        List<Map.Entry<Sport, Integer>> list = new ArrayList<>(postsPerSport.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Sport, Integer>>() {
            @Override
            public int compare(Map.Entry<Sport, Integer> o1, Map.Entry<Sport, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        return  list.get(position-1).getKey();

    }
    public String getSportNameAtPosition(int position){
        LocalDate currentDate = LocalDate.now();
        Month month = currentDate.getMonth();
        LocalTime time_now = LocalTime.now();
        if(month!=currentMonth || Duration.between(lastComputedTime,time_now).compareTo(Threshold) >0)
        {   computePostsPerMonth();}
        List<Map.Entry<Sport, Integer>> list = new ArrayList<>(postsPerSport.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Sport, Integer>>() {
            @Override
            public int compare(Map.Entry<Sport, Integer> o1, Map.Entry<Sport, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        return list.get(position-1).getKey().name;
    }
    public LocalTime getLastComputedTime(){
        return lastComputedTime;

    }
    public int getSportImageAtPos(int position){
        switch(topSportAtPosition(position)) {
         case FOOTBALL:
            return R.drawable.football_picture;
            case HANDBALL:
                return R.drawable.handball_picture;
            case TENNIS:
                return R.drawable.tennis_picture;
            case BOXING:
                return R.drawable.boxing_picture;
            case BASKETBALL:
               return  R.drawable.basketball_picture;
    }
    return R.drawable.handball_picture;
    }
    public int getNumberPosts(Sport sport){
      return postsPerSport.get(sport);
    }



}
