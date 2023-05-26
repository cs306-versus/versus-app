package com.github.versus.db;

import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.schedule.Schedule;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface ScheduleManager extends DataBaseManager<Schedule> {



    /**
     * gets the schedule of a user starting from a certain date
     * @param UID The user id for which we want to get the schedule
     * @param startingDate The date from which we start the schedule
     * @return a future wrapping a list of posts corresponding to the player schedule
     */
    public CompletableFuture<Schedule> getScheduleStartingFromDate(String UID, Timestamp startingDate);

    /**
     * adds a new post to a schedule to the database
     * @param UID the user ID
     * @param post the post to add
     * @return a future wrapping the state of the addition completion
     */
    public Future<Boolean> addPostToSchedule(String UID, Post post) ;

}
