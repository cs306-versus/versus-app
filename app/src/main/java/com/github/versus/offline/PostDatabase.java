package com.github.versus.offline;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CachedPost.class}, version = 1)
public abstract class PostDatabase extends RoomDatabase {
    public abstract PostDAO activityDao();
}
