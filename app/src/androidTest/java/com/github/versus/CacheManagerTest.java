package com.github.versus;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.offline.CacheManager;
import com.github.versus.offline.CachedPost;
import com.github.versus.offline.SimpleTestPost;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.sports.Sport;
import com.github.versus.user.DummyUser;
import com.github.versus.user.VersusUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Month;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@RunWith(AndroidJUnit4.class)
public class CacheManagerTest {
    @Rule
    public ActivityScenarioRule<MainActivity> testRule = new ActivityScenarioRule<>(MainActivity.class);

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
    public void randomSelectEmptyCache() throws ExecutionException, InterruptedException {
        assertTrue(manager.randomSelect().get().isEmpty());
    }

    @Test
    public void randomSelectRetrievesPosts() throws ExecutionException, InterruptedException {
        SimpleTestPost post =  new SimpleTestPost("only one to select");
        manager.insert(post).get();
        Post retrieved = manager.randomSelect().get().get(0);
        assertTrue(post.equals(retrieved));
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

    @Test
    public void fetchAllByIdsRetrievesCorrectPost() throws ExecutionException, InterruptedException {
        Post post1 =  new SimpleTestPost("to fetch");
        Post post2= new SimpleTestPost("Valid");
        manager.insertAll(post1,post2).get();
        List<String> retrieved= manager.fetchAllByIds(CachedPost.computeID(post1),CachedPost.computeID(post2))
                                .get().stream().map(p -> p.getTitle())
                                .collect(Collectors.toList());
        assertTrue(retrieved.contains(post1.getTitle())&&retrieved.contains(post2.getTitle()));
    }

    @Test
    public void fetchAllByIdsUnavailablePosts() throws ExecutionException, InterruptedException {
        assertTrue(manager.fetchAllByIds(CachedPost.EMPTY_ID).get().isEmpty());
    }

    @Test
    public void sportsAreCachedCorrectly() throws ExecutionException, InterruptedException {
        Post post= SimpleTestPost.postWith("I don't play soccer, i prefer rowing",
        new Timestamp(Calendar.getInstance().get(Calendar.YEAR),
                Month.values()[0], 1, 8, 1, Timestamp.Meridiem.PM)
                ,new Location("Lausanne", 10, 10),10, Sport.CLIMBING);

        String key= CachedPost.computeID(post);
        manager.insert(post).get();
        Post fetched =manager.fetch(key).get();
        assertTrue(fetched.getSport()==post.getSport());
    }

    @Test
    public void NoSportNoCache() throws ExecutionException, InterruptedException {
        Post post= SimpleTestPost.postWith("I don't play soccer, i prefer rowing",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR),
                        Month.values()[0], 1, 8, 1, Timestamp.Meridiem.PM)
                ,new Location("Lausanne", 10, 10),10, null);
        assertFalse(manager.insert(post).get());
    }

    @Test
    public  void userPresentUIDCachedCorrectly() throws ExecutionException, InterruptedException {
        VersusUser user = new VersusUser.Builder("uid").build();
        Post post = SimpleTestPost.postWith("Looking for football team",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, user);
        manager.insert(post).get();
        manager.fetch(CachedPost.computeID(post)).get();
        assertTrue(manager.fetch(CachedPost.computeID(post)).get().getPlayers().get(0).equals(user));
    }

    @Test
    public  void databaseDoesNotIntroduceInconsistencies() throws ExecutionException, InterruptedException {
        Post post = SimpleTestPost.postWith("looking for football team  but im not playing",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER);
        manager.insert(post).get();
        manager.fetch(CachedPost.computeID(post)).get();
        assertTrue(manager.fetch(CachedPost.computeID(post)).get().getPlayers().isEmpty());
    }

    @Test
    public void loadBySportsGivesCorrectResult() throws ExecutionException, InterruptedException {
        Post post = SimpleTestPost.postWith("looking for football team  but im not playing",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER);
        manager.insert(post).get();
        assertTrue(manager.loadBySport(Sport.SOCCER).get().getTitle().equals(post.getTitle()));

    }

}
