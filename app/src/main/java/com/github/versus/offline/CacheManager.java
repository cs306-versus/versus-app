package com.github.versus.offline;

import com.github.versus.db.DataBaseManager;
import com.github.versus.posts.Post;

import java.util.concurrent.Future;

public final class CacheManager implements DataBaseManager<Post> {

    private static CacheManager instance = null;

    private CacheManager(){}

    public static CacheManager getCacheManager(){
        if(instance==null){
            instance= new CacheManager();
        }
        return instance;
    }
    @Override
    public Future<Boolean> insert(Post data) {
        return null;
    }

    @Override
    public Future<Post> fetch(String id) {
        return null;
    }

    @Override
    public Future<Boolean> delete(String id) {
        return null;
    }
}
