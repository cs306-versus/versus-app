package com.github.ziazi.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ActivityDAO {
    @Query("SELECT * FROM CachedActivity")
    List<CachedActivity> getAll();

    @Query("SELECT * FROM CachedActivity WHERE id LIKE :id ")
    CachedActivity loadById(int id);

    @Query("SELECT * FROM CachedActivity WHERE id IN (:ids)")
    List<CachedActivity> loadAllByIds(int[] ids);

    @Query("SELECT * FROM CachedActivity ORDER BY RANDOM() LIMIT 1")
    CachedActivity randomSelect();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(CachedActivity... rows);

    @Delete
    void delete(CachedActivity row);
}
