package com.github.versus.db;

import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.user.User;

import java.util.List;
import java.util.concurrent.Future;

public interface ScheduleManager {

    /**
     * gets the schedule (posts the a player joined)
     * @param UID The user id for which we want to get the schedule
     * @return a future wrapping a list of posts corresponding to the player schedule
     */
    public Future<List<Post>> getSchedule(String UID);

    /**
     * gets the schedule of a user starting from a certain date
     * @param UID The user id for which we want to get the schedule
     * @param startingDate The date from which we start the schedule
     * @return a future wrapping a list of posts corresponding to the player schedule
     */
    public Future<List<Post>> getScheduleStartingFromDate(String UID, Timestamp startingDate);



}
