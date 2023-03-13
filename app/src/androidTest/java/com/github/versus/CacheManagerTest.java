package com.github.versus;

import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.offline.CacheManager;
import com.github.versus.offline.CachedPost;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.Future;

@RunWith(AndroidJUnit4.class)
public class CacheManagerTest {
    private CacheManager manager;

    @Before
    public void createDb() {
        manager = CacheManager.getCacheManager(ApplicationProvider.getApplicationContext());
    }

    @After
    public void closeDb() throws IOException {
        //manager.getDb().clearAllTables();
        manager.getDb().close();
    }


    @Test
    public void insertOfValidPostIsSuccessful() throws Exception {
       SimpleTestPost post= new SimpleTestPost();
       Future<Boolean> inserted= manager.insert(post);
       assertTrue(inserted.get());

    }


}
