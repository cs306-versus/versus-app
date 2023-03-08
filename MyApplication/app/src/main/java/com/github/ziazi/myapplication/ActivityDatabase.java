package com.github.ziazi.myapplication;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CachedActivity.class}, version = 1)
public abstract class ActivityDatabase  extends RoomDatabase {
    public abstract ActivityDAO activityDao();
}
