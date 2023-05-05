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
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@RunWith(AndroidJUnit4.class)
public class CacheManagerTest {
    @Rule
    public ActivityScenarioRule<MainActivity> testRule = new ActivityScenarioRule<>(MainActivity.class);

    private CacheManager manager;
    private static List<VersusUser> users = buildTestUsers();
    private static List<VersusUser> buildTestUsers() {
        List<Sport> preferredSports1= Arrays.asList(Sport.MARTIALARTS,Sport.CLIMBING,Sport.CRICKET,
                Sport.JUDO,Sport.GOLF, Sport.SURFING,Sport.WRESTLING);
        List<Sport> preferredSports2= Arrays.asList(Sport.BOXING);
        List<Sport> preferredSports3= Arrays.asList(Sport.FOOTBALL,Sport.BASKETBALL);

        VersusUser.Builder builder ;
        builder= new VersusUser.Builder("007");
        builder.setFirstName("James");
        builder.setLastName("Bond");
        builder.setUserName("NotInfiltrated");
        builder.setMail("notfakemail@gmail.com");
        builder.setPhone("0000077777");
        builder.setRating(5);
        builder.setCity("London");
        builder.setZipCode(700);
        builder.setPreferredSports(preferredSports1);
        VersusUser JamesBond=   builder.build();

        builder = new VersusUser.Builder("1");
        builder.setFirstName("Mohammad");
        builder.setLastName("Ali");
        builder.setUserName("TheGoat");
        builder.setMail("IamTheDancingMaster@gmail.com");
        builder.setPhone("0000000001");
        builder.setRating(5);
        builder.setCity("Ring");
        builder.setZipCode(1);
        builder.setPreferredSports(preferredSports2);
        VersusUser MohammedAli= builder.build();

        builder = new VersusUser.Builder("999");
        builder.setFirstName("Regular");
        builder.setLastName("Guy");
        builder.setUserName("BasicGuy");
        builder.setMail("ILoveBallSports@gmail.com");
        builder.setPhone("999999999");
        builder.setRating(3);
        builder.setCity("FootballClub");
        builder.setZipCode(99);
        builder.setPreferredSports(preferredSports3);
        VersusUser RegularGuy= builder.build();
        return Arrays.asList(JamesBond,MohammedAli,RegularGuy);
    }

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
        Post post = SimpleTestPost.postWith("Valid",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, users.get(0));
       Future<Boolean> inserted= manager.insert(post);
       assertTrue(inserted.get());
    }
    @Test
    public void insertAllOfValidPostsIsSuccessful() throws Exception {
        Post post1= SimpleTestPost.postWith("Valid",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, users.get(0));
        Post post2=  SimpleTestPost.postWith("Another Valid one",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, users);
        Future<Boolean> inserted= manager.insertAll(post1,post2);
        assertTrue(inserted.get());
    }


    @Test
    public void insertOfInvalidPostIsUnsuccessful() throws ExecutionException, InterruptedException {
        assertFalse(manager.insert(null).get());
    }

    @Test
    public void insertAllOfInvalidPostsIsUnsuccessful() throws ExecutionException, InterruptedException {
        assertFalse(manager.insertAll(null,new SimpleTestPost()).get());
    }

    @Test
    public void insertAllOfInvalidAndValidPostsIsUnsuccessful() throws ExecutionException, InterruptedException {
        Post valid=  SimpleTestPost.postWith("Nothing to see here",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, users);
        assertFalse(manager.insertAll(null,valid).get());
    }
    @Test
    public void CacheManagerIsUnique() {
        CacheManager secondManager= CacheManager.getCacheManager(ApplicationProvider.getApplicationContext());
        assertTrue(secondManager==manager);
    }

    @Test
    public void fetchRetrievesTheRightPost() throws ExecutionException, InterruptedException {
        Post post =  SimpleTestPost.postWith("fetch me harder",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, users);

        manager.insert(post).get();

        Post retrieved = manager.fetch(CachedPost.computeID(post)).get();
        assertTrue(post.equals(retrieved));
    }

    @Test
    public void deleteWorksWithValidPost() throws ExecutionException, InterruptedException {
        Post post =  SimpleTestPost.postWith("delete me",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, users);
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
        assertTrue(manager.fetch(CachedPost.INVALID_ID).get()==null);
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
        Post post =  SimpleTestPost.postWith("fetch me in public",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, users);
        manager.insert(post).get();
        assertTrue(post.equals(manager.getAllPosts().get().get(0)));
    }

    @Test
    public void fetchAllByIdsRetrievesCorrectPost() throws ExecutionException, InterruptedException {
        Post post1 =  SimpleTestPost.postWith("fetch me before",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, users);
        Post post2 =  SimpleTestPost.postWith("fetch me after",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, users);

        manager.insertAll(post1,post2).get();
        Set<Post> retrieved= new HashSet<>(manager.fetchAllByIds(CachedPost.computeID(post1),
                                            CachedPost.computeID(post2)).get());
        assertTrue(retrieved.contains(post1) && retrieved.contains(post2) && retrieved.size()==2);
    }

    @Test
    public void fetchAllByIdsUnavailablePosts() throws ExecutionException, InterruptedException {
        assertTrue(manager.fetchAllByIds(CachedPost.INVALID_ID).get().isEmpty());
    }


    @Test
    public void NoSportNoCache() throws ExecutionException, InterruptedException {
        Post post= SimpleTestPost.postWith("I don't play soccer, i prefer sleeping",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR),
                        Month.values()[0], 1, 8, 1, Timestamp.Meridiem.PM)
                ,new Location("Lausanne", 10, 10),10, null,users);
        assertFalse(manager.insert(post).get());
    }
    @Test
    public void fetchByTimestampYieldsCorrectPost() throws ExecutionException, InterruptedException {
        Timestamp ts=  new Timestamp(Calendar.getInstance().get(Calendar.YEAR),
                Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM);

        Post post =  SimpleTestPost.postWith("Correct post!", ts,
                new Location("Lausanne",10,10),10, Sport.SOCCER, users);

        Post trap =  SimpleTestPost.postWith("That's a trap!",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR),
                        Month.values()[0], 1, 8, 1, Timestamp.Meridiem.AM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, users);
        manager.insertAll(post,trap).get();
        List<Post> fetched= manager.fetchByTimestamp(ts).get();
        assertTrue(fetched.size()==1 && fetched.contains(post) );

    }

    @Test
    public void fetchByTimestampYieldsCorrectMultiplePosts() throws ExecutionException, InterruptedException {
        Timestamp ts=  new Timestamp(Calendar.getInstance().get(Calendar.YEAR),
                Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM);

        Post post1 =  SimpleTestPost.postWith("Correct post #1!", ts,
                new Location("Lausanne",10,10),10, Sport.BOXING, users);

        Post post2 =  SimpleTestPost.postWith("Correct post #2!", ts,
                new Location("Lausanne",10,10),10, Sport.BOXING, users);
        Post trap =  SimpleTestPost.postWith("That's a trap!",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR),
                        Month.values()[0], 1, 8, 1, Timestamp.Meridiem.AM),
                new Location("Lausanne",10,10),10, Sport.BOXING, users);
        manager.insertAll(post1,post2,trap).get();
        Set<Post> fetched= new HashSet<>(manager.fetchByTimestamp(ts).get());
        assertTrue(fetched.size()==2 && fetched.containsAll(Arrays.asList(post1,post2)));

    }

    @Test
    public void fetchBySportYieldsCorrectPost() throws ExecutionException, InterruptedException {

        Post post =  SimpleTestPost.postWith("Correct post!",  new Timestamp(Calendar.getInstance().get(Calendar.YEAR),
                        Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.BOXING, users);

        Post trap =  SimpleTestPost.postWith("That's a trap!",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR),
                        Month.values()[0], 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, users);
        manager.insertAll(post,trap).get();
        List<Post> fetched= manager.fetchBySport(Sport.BOXING).get();
        assertTrue(fetched.size()==1 && fetched.contains(post) );

    }

    @Test
    public void fetchBySportYieldsCorrectMultiplePosts() throws ExecutionException, InterruptedException {

        Post post1 =  SimpleTestPost.postWith("Correct post! #1",  new Timestamp(Calendar.getInstance().get(Calendar.YEAR),
                        Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.BOXING, users);

        Post post2 =  SimpleTestPost.postWith("Correct post! #2",  new Timestamp(Calendar.getInstance().get(Calendar.YEAR),
                        Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.BOXING, users);

        Post trap =  SimpleTestPost.postWith("That's a trap!",
                new Timestamp(Calendar.getInstance().get(Calendar.YEAR),
                        Month.values()[0], 1, 8, 1, Timestamp.Meridiem.PM),
                new Location("Lausanne",10,10),10, Sport.SOCCER, users);
        manager.insertAll(post1,post2,trap).get();
        Set<Post> fetched= new HashSet<>(manager.fetchBySport(Sport.BOXING).get());
        assertTrue(fetched.size()==2 && fetched.containsAll(Arrays.asList(post1,post2)));

    }

}
