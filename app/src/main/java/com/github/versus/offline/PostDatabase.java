package com.github.versus.offline;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CachedPost.class}, version = 1)
/**
 * This class represents the database used to implement the cache
 */
public abstract class PostDatabase extends RoomDatabase {
    /**
     *Returns the database object used to query the database
     * @return
     * DAO
     */
    public abstract PostDAO activityDao();
}
