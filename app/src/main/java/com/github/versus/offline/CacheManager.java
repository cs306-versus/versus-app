package com.github.versus.offline;

import android.content.Context;

import androidx.room.Room;

import com.github.versus.db.DataBaseManager;
import com.github.versus.posts.Post;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public final class CacheManager implements DataBaseManager<Post> {
    private final PostDAO dao;
    private static CacheManager instance = null;


    private CacheManager(Context context){
        dao= Room.databaseBuilder(context.getApplicationContext(),
                PostDatabase.class, "cache").build().activityDao();
    }

    public static CacheManager getCacheManager(Context context){
        if(instance==null){
            instance= new CacheManager(context);
        }
        return instance;
    }
    @Override
    public Future<Boolean> insert(Post post) {

        return CompletableFuture.runAsync((()->dao.insertAll(CachedPost.match(post)))).
                handle((r,e)->{
                    if(e!=null){
                        return false;
                    }
                    return true;
                });
    }

    @Override
    public Future<Post> fetch(String id) {
        return CompletableFuture.supplyAsync(()->dao.loadById(id).revert());
    };

    @Override
    public Future<Boolean> delete(String id) {
        return CompletableFuture.runAsync((()->dao.delete(dao.loadById(id)))).
                handle((r,e)->{
                    if(e!=null){
                        return false;
                    }
                    return true;
                });
    }
}
