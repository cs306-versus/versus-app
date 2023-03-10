package com.github.versus.offline;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDAO  {
    @Query("SELECT * FROM CachedPost")
    List<CachedPost> getAll();

    @Query("SELECT * FROM CachedPost WHERE id LIKE :id ")
    CachedPost loadById(String id);

    @Query("SELECT * FROM CachedPost WHERE id IN (:ids)")
    List<CachedPost> loadAllByIds(int[] ids);

    @Query("SELECT * FROM CachedPost ORDER BY RANDOM() LIMIT 1")
    CachedPost randomSelect();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(CachedPost... rows);

    @Delete
    void delete(CachedPost row);


}
