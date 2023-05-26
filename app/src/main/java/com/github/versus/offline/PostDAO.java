package com.github.versus.offline;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * This intereface represents the object that interacts with the database using SQL queries
 */
@Dao
public interface  PostDAO  {
    @Query("SELECT * FROM CachedPost")
    List<CachedPost> getAll();

    @Query("SELECT * FROM CachedPost WHERE id LIKE :id ")
    CachedPost loadById(String id);

    @Query("SELECT * FROM CachedPost WHERE id IN (:ids)")
    List<CachedPost> loadAllByIds(String ids[]);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(CachedPost... rows);

    @Query("DELETE FROM CachedPost WHERE id LIKE :id")
    void deleteById(String id);

    @Query("SELECT * FROM CachedPost WHERE sport LIKE :activity")
    List<CachedPost> fetchBySport(String activity);
    @Query("SELECT * FROM CachedPost WHERE timestamp LIKE :schedule")
    List<CachedPost> fetchByTimeStamp(String schedule);

}
