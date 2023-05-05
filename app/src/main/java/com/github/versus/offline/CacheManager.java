package com.github.versus.offline;

import android.content.Context;

import androidx.room.Room;

import com.github.versus.db.DataBaseManager;
import com.github.versus.posts.Post;
import com.github.versus.sports.Sport;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * This class represents the manager of the post cache
 */
public final class CacheManager implements DataBaseManager<Post> {

    private final PostDatabase db;
    private final PostDAO dao;
    private static CacheManager instance = null;


    private CacheManager(Context context){
        db= Room.databaseBuilder(context.getApplicationContext(),
                PostDatabase.class, "cache").build();
        dao= db.activityDao();
    }

    /**
     * Returns a unique instance of cache manager
     * @param context
     * @return
     * A new cache cache manager if none exists, the already existing instance otherwise
     */
    public static CacheManager getCacheManager(Context context){
        if(instance==null){
            instance= new CacheManager(context);
        }
        return instance;
    }

    /**
     * Inserts the post in the cache
     * @param post
     * @return
     * A Completable future indicating if the operation was successful or not
     */
    @Override
    public Future<Boolean> insert(Post post) {
        CachedPost cached= CachedPost.match(post);
        if(cached==null){
            return CompletableFuture.completedFuture(Boolean.FALSE);
        }
        return CompletableFuture.runAsync((()->dao.insertAll(cached))).
                handle((r,e)-> e==null);
    }

    /**
     * Fetches the post that matches the given id from the database
     * @param id
     * @return
     * A completable future resulting in the post if cached and null otherwise
     */
    @Override
    public Future<Post> fetch(String id) {
        return CompletableFuture.supplyAsync(()->dao.loadById(id).revert())
                .handle((r,e)-> e==null? r:null);
    };

    /**
     * Removes the post with the given id
     * @param id id of the entry to remove
     * @return
     * A Completable future indicating if the operation was successful or not
     */
    @Override
    public Future<Boolean> delete(String id) {
        if(id==null){
            return CompletableFuture.completedFuture(Boolean.FALSE);
        }
        return CompletableFuture.runAsync((()->dao.deleteById(id)))
                .handle((r,e)-> e==null);
    }


    /**
     *  Inserts all the posts in the cache
     * @param posts
     * @return
     * A Completable future indicating if the operation was successful or not
     */
    public Future<Boolean> insertAll(Post ...posts){
        CachedPost match[]= new CachedPost[posts.length];
        for (int i = 0; i < posts.length; i++) {
            match[i]= CachedPost.match(posts[i]);
            if(match[i]==null){
                return CompletableFuture.completedFuture(Boolean.FALSE);
            }
        }
        return CompletableFuture.runAsync(()->dao.insertAll(match))
                .handle((r,e)-> e==null);
    }

    /**
     * Fetches the all the posts that matches the given ids from the database
     * @param ids
     * @return
     *  A Completable future resulting in a list of posts if present and null otherwise
     */
    public Future<List<Post>> fetchAllByIds(String ...ids){
        return CompletableFuture.supplyAsync(()->dao.loadAllByIds(ids).stream()
                        .map(cachedPost -> cachedPost.revert()).collect(Collectors.toList()))
                        .handle((r,e)-> e==null? r:null);

    }

    /**
     * fetches all the cached posts
     * @return
     *  A Completable future resulting the content of the cache
     */
    public Future<List<Post>> getAllPosts(){
        return CompletableFuture.supplyAsync(()-> dao.getAll().stream()
                        .map(CachedPost::revert).collect(Collectors.toList()))


                        .handle((r,e)-> e==null ? r:null);
    }



    /**
     *Erases the cache
     */
    public void clearDB(){
        db.clearAllTables();
    }


    /**
     * determines if the cache is operational
     * @return
     * true iff the cache is available
     */
    public boolean DBAvailable(){
        return db.isOpen();
    }


}
