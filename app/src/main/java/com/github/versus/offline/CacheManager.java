package com.github.versus.offline;

import android.content.Context;

import androidx.room.Room;

import com.github.versus.db.DataBaseManager;
import com.github.versus.posts.Post;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public final class CacheManager implements DataBaseManager<Post> {

    private final PostDatabase db;
    private final PostDAO dao;
    private static CacheManager instance = null;


    private CacheManager(Context context){
        db= Room.databaseBuilder(context.getApplicationContext(),
                PostDatabase.class, "cache").build();
        dao= db.activityDao();
    }

    public static CacheManager getCacheManager(Context context){
        if(instance==null){
            instance= new CacheManager(context);
        }
        return instance;
    }
    @Override
    public Future<Boolean> insert(Post post) {
        CachedPost cached= CachedPost.match(post);
        if(cached.isEmpty){
            return CompletableFuture.completedFuture(Boolean.FALSE);
        }
        return CompletableFuture.runAsync((()->dao.insertAll(cached))).
                handle((r,e)-> e==null);
    }

    @Override
    public Future<Post> fetch(String id) {
        return CompletableFuture.supplyAsync(()->dao.loadById(id).revert())
                .handle((r,e)-> e==null? r:null);
    };

    @Override
    public Future<Boolean> delete(String id) {
        if(id==null){
            return CompletableFuture.completedFuture(Boolean.FALSE);
        }
        return CompletableFuture.runAsync((()->dao.deleteById(id)))
                .handle((r,e)-> e==null);
    }

    public PostDAO getDao(){
        return dao;
    }

    public PostDatabase getDb(){
        return db;
    }

}
