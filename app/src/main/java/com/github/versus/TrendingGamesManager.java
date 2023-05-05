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

/**
 * This class manages trending games by sport for a given month. It keeps track of the number of posts per sport
 * and provides methods to retrieve information about the top sports and their number of posts.
 */
public class TrendingGamesManager {

    // Time threshold after which data is recomputed
    private final Duration threshold = Duration.ofHours(2);

    // Time of the last computation
    private LocalTime lastComputedTime;

    // Map of sports to the number of posts for each sport
    private Map<Sport,Integer> postsPerSport;

    // Current month for which the trending games are being computed
    private Month currentMonth ;

    /**
     * Constructor for the TrendingGamesManager class. Initializes the lastComputedTime, postsPerSport,
     * and currentMonth fields and computes the postsPerSport data.
     */
    public TrendingGamesManager(){
        lastComputedTime=LocalTime.now();
        postsPerSport=new HashMap<>();
        LocalDate currentDate = LocalDate.now();
        currentMonth = currentDate.getMonth();
        computePostsPerMonth();
    }

    /**
     * A helper method to compute the number of posts for each sport for the current month. This method is called
     * by the constructor and when the data is stale.
     */
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

    /**
     * Returns the sport at the specified position in the trending games list for the current month.
     * @param position the position of the sport in the trending games list (starting from 1)
     * @return the sport at the specified position
     */
    public Sport topSportAtPosition(int position){
        LocalDate currentDate = LocalDate.now();
        Month month = currentDate.getMonth();
        LocalTime time_now = LocalTime.now();
        if(month!=currentMonth || Duration.between(lastComputedTime,time_now).compareTo(threshold) >0)
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
    /**
     * Returns the name of the sport at a given position in the ranking.
     *
     * @param position the position of the sport in the ranking
     * @return the name of the sport at the given position
     */
    public String getSportNameAtPosition(int position) {
        LocalDate currentDate = LocalDate.now();
        Month month = currentDate.getMonth();
        LocalTime time_now = LocalTime.now();

        // If the current month has changed or the time threshold has passed since the last update,
        // we recompute the number of posts per sport.
        if (month != currentMonth || Duration.between(lastComputedTime, time_now).compareTo(threshold) > 0) {
            computePostsPerMonth();
        }

        // Sort the sports by number of posts in descending order and return the name of the sport
        // at the given position in the ranking.
        List<Map.Entry<Sport, Integer>> list = new ArrayList<>(postsPerSport.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Sport, Integer>>() {
            @Override
            public int compare(Map.Entry<Sport, Integer> o1, Map.Entry<Sport, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        return list.get(position - 1).getKey().name();
    }

    /**
     * Returns the local time when the last computation of the number of posts per sport was made.
     *
     * @return the local time of the last computation
     */
    public LocalTime getLastComputedTime() {
        return lastComputedTime;
    }

    /**
     * Returns the ID of the image associated with the sport at a given position in the ranking.
     *
     * @param position the position of the sport in the ranking
     * @return the ID of the image associated with the sport at the given position
     */
    public int getSportImageAtPosition(int position) {
        Sport sport = topSportAtPosition(position);
        switch (sport) {
            case FOOTBALL:
                return R.drawable.football_picture;
            case HANDBALL:
                return R.drawable.handball_picture;
            case TENNIS:
                return R.drawable.tennis_picture;
            case BOXING:
                return R.drawable.boxing_picture;
            case BASKETBALL:
                return R.drawable.basketball_picture;
            default:
                return R.drawable.handball_picture;
        }
    }
    /**
     * Returns the image resource ID for the sport at the specified position in the
     * list of trending sports.
     *
     * @param position the position of the sport in the list of trending sports
     * @return the image resource ID for the sport
     */
    public int getSportImageAtPos(int position) {
        // Determine the sport at the specified position
        Sport sport = topSportAtPosition(position);

        // Return the appropriate image resource ID based on the sport
        switch (sport) {
            case FOOTBALL:
                return R.drawable.football_picture;
            case HANDBALL:
                return R.drawable.handball_picture;
            case TENNIS:
                return R.drawable.tennis_picture;
            case BOXING:
                return R.drawable.boxing_picture;
            case BASKETBALL:
                return R.drawable.basketball_picture;
            default:
                // This should never happen, but if it does, return the handball picture
                return R.drawable.handball_picture;
        }
    }

    /**
     * Returns the number of posts made for a given sport in the current month.
     *
     * @param sport the sport to get the number of posts for
     * @return the number of posts made for the given sport
     */
    public int getNumberPosts(Sport sport) {
        return postsPerSport.get(sport);
    }
}



