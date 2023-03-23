package com.github.versus.db;

import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.user.User;

import java.util.List;
import java.util.concurrent.Future;

public class FsScheduleManager implements ScheduleManager {
    @Override
    public Future<List<Post>> getSchedule(User user){
        return null;
    }

    @Override
    public Future<List<Post>> getScheduleStartingFromDate(User user, Timestamp startingDate) {
        return null;
    }


}
