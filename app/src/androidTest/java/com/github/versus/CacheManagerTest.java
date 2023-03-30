package com.github.versus;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.offline.CacheManager;
import com.github.versus.offline.CachedPost;
import com.github.versus.offline.SimpleTestPost;
import com.github.versus.posts.Post;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RunWith(AndroidJUnit4.class)
public class CacheManagerTest {
    @Rule
    public ActivityScenarioRule<MainActivity> testRule = new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void onePlusOneIsTwo(){
        assertTrue(1+1==2);
    }


    private CacheManager manager;

    @Before
    public void createDb() {
        manager = CacheManager.getCacheManager(ApplicationProvider.getApplicationContext());
    }


    @After
    public void ClearAndClose() {
        manager.clearDB();
        manager= null;

    }

    @Test
    public void insertOfValidPostIsSuccessful() throws Exception {
       SimpleTestPost post= new SimpleTestPost();
       Future<Boolean> inserted= manager.insert(post);
       assertTrue(inserted.get());
    }
    @Test
    public void insertAllOfValidPostsIsSuccessful() throws Exception {
        Post post1= new SimpleTestPost();
        Post post2= new SimpleTestPost("Another Valid one");
        Future<Boolean> inserted= manager.insertAll(post1,post2);
        assertTrue(inserted.get());
    }


    @Test
    public void insertOfInvalidPostIsUnsuccessful() throws ExecutionException, InterruptedException {
        assertFalse(manager.insert(null).get());
    }

    @Test
    public void insertAllOfInvalidPostsIsUnsuccessful() throws ExecutionException, InterruptedException {
        assertFalse(manager.insertAll(null,null).get());
    }

    @Test
    public void insertAllOfInvalidAndValidPostsIsUnsuccessful() throws ExecutionException, InterruptedException {
        assertFalse(manager.insertAll(null,new SimpleTestPost()).get());
    }
    @Test
    public void CacheManagerIsUnique() {
        CacheManager secondManager= CacheManager.getCacheManager(ApplicationProvider.getApplicationContext());
        assertTrue(secondManager==manager);
    }

    @Test
    public void fetchRetrievesTheRightPost() throws ExecutionException, InterruptedException {
        SimpleTestPost post =  new SimpleTestPost("to fetch");

        manager.insert(post).get();

        Post retrieved = manager.fetch(CachedPost.computeID(post)).get();
        assertTrue(post.equals(retrieved));
    }

    @Test
    public void deleteWorksWithValidPost() throws ExecutionException, InterruptedException {
        SimpleTestPost post =  new SimpleTestPost("To Be Deleted");
        manager.insert(post).get();
        boolean result= manager.delete(CachedPost.computeID(post)).get();
        assertTrue(result);
    }
    @Test
    public void deleteWorksWithNullPost() throws ExecutionException, InterruptedException {
        assertFalse( manager.delete(null).get());
    }

    @Test
    public void fetchUnavailablePost() throws ExecutionException, InterruptedException {
        assertTrue(manager.fetch(CachedPost.EMPTY_ID).get()==null);
    }


    @Test
    public void databaseISOpen() {
        assertTrue(manager.DBAvailable());
    }

    @Test
    public void getAllPostsWorksWhenNoPostIsPresent() throws ExecutionException, InterruptedException {
        assertTrue(manager.getAllPosts().get().size()==0);
    }

    @Test
    public void getAllPostsRetrievesPostsCorrectly() throws ExecutionException, InterruptedException {
        SimpleTestPost post =  new SimpleTestPost("to fetch");
        manager.insert(post).get();
        assertTrue(post.equals(manager.getAllPosts().get().get(0)));
    }
}
