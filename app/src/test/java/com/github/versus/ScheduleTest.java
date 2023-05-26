package com.github.versus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.github.versus.offline.SimpleTestPost;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.schedule.Schedule;
import com.github.versus.sports.Sport;

import org.checkerframework.framework.qual.DefaultQualifier;
import org.junit.Assert;
import org.junit.Test;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public final class ScheduleTest {

    // ============================================================================================
    // ================================ CONSTRUCTORS TESTS ========================================
    // ============================================================================================

    @Test
    public void testDefaultConstructor(){
        assertNotNull(new Schedule());
    }

    @Test
    public void testConstructorWithParameter(){
        assertNotNull(new Schedule("123456789"));
    }

    // ============================================================================================
    // ========================================== getUID TESTS ====================================
    // ============================================================================================

    @Test
    public void testGetUID(){
        String uid = "123456789";
        assertEquals(uid, new Schedule(uid).getUID());
    }

    // ============================================================================================
    // ==================================== GET POSTS TEST ========================================
    // ============================================================================================

    @Test
    public void testGetPost(){
        assertTrue(new Schedule().getPosts().isEmpty());
    }

    // ============================================================================================
    // ====================================== test getAllAttributes ===============================
    // ============================================================================================

    @Test
    public void testGetAllAttributes(){
        assertFalse(new Schedule().getAllAttributes().isEmpty());
    }

    //
    //
    // ============================================================================================

    @Test
    public void testAddPost(){
        new Schedule().addPost(null);
    }

    @Test
    public void testAddPosts(){
        new Schedule().addPosts(new ArrayList<>());
    }

    @Test
    public void testRemovePost(){
        new Schedule().removePost("uid");
    }

    // ============================================================================================
    // ================================= TEST Schedule::equals ====================================
    // ============================================================================================

    @Test
    public void testWithSameObject(){
        Schedule schedule = new Schedule();
        assertEquals(schedule, schedule);
    }

    @Test
    public void testWithDifferentType(){
        assertNotEquals(new Schedule(), new Object());
    }

    @Test
    public void testWithDifferentObject(){
        String uid = "123456789";
        Schedule schedule1 = new Schedule(uid);
        Schedule schedule2 = new Schedule(uid);
        assertEquals(schedule1, schedule2);
    }


    @Test
    public void startingFromDateYieldsCorrectPosts(){
        String uid = "123456789";
        Timestamp ts1= new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 10, 8, 1, Timestamp.Meridiem.PM);
        Timestamp ts2= new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM);
        Schedule schedule = new Schedule(uid);
        Post post1= SimpleTestPost.postWith("Valid",
                ts1, new Location("Lausanne",10,10),10, Sport.SOCCER);
        Post post2=  SimpleTestPost.postWith("Another Valid one",
                ts2, new Location("Lausanne",10,10),10, Sport.SOCCER);

        schedule.addPosts(List.of(post1,post2));
        Schedule result= schedule.startingFromDate(ts1);
        Assert.assertTrue(result.getPosts().contains(post1)&& !result.getPosts().contains(post2));

    }

    @Test
    public void startingFromDateYieldsCorrectUid(){
        String uid = "123456789";
        Timestamp ts1= new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 10, 8, 1, Timestamp.Meridiem.PM);
        Timestamp ts2= new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM);
        Schedule schedule = new Schedule(uid);
        Post post1= SimpleTestPost.postWith("Valid",
                ts1, new Location("Lausanne",10,10),10, Sport.SOCCER);
        Post post2=  SimpleTestPost.postWith("Another Valid one",
                ts2, new Location("Lausanne",10,10),10, Sport.SOCCER);

        schedule.addPosts(List.of(post1,post2));
        Schedule result= schedule.startingFromDate(ts1);
        assertEquals(result.getUID(), uid);

    }

    @Test
    public void OnDateYieldsCorrectPosts(){
        String uid = "123456789";
        Timestamp ts1= new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 10, 8, 1, Timestamp.Meridiem.PM);
        Timestamp ts2= new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM);
        Schedule schedule = new Schedule(uid);
        Post post1= SimpleTestPost.postWith("Valid",
                ts1, new Location("Lausanne",10,10),10, Sport.SOCCER);
        Post post2=  SimpleTestPost.postWith("Another Valid one",
                ts2, new Location("Lausanne",10,10),10, Sport.SOCCER);

        schedule.addPosts(List.of(post1,post2));
        Schedule result= schedule.onDate(ts1);
        Assert.assertTrue(result.getPosts().contains(post1)&& !result.getPosts().contains(post2));

    }

    @Test
    public void OnDateYieldsCorrectUid(){
        String uid = "123456789";
        Timestamp ts1= new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 10, 8, 1, Timestamp.Meridiem.PM);
        Timestamp ts2= new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 1, 8, 1, Timestamp.Meridiem.PM);
        Schedule schedule = new Schedule(uid);
        Post post1= SimpleTestPost.postWith("Valid",
                ts1, new Location("Lausanne",10,10),10, Sport.SOCCER);
        Post post2=  SimpleTestPost.postWith("Another Valid one",
                ts2, new Location("Lausanne",10,10),10, Sport.SOCCER);

        schedule.addPosts(List.of(post1,post2));
        Schedule result= schedule.onDate(ts1);
        assertEquals(result.getUID(), uid);

    }

    @Test
    public void getAllAttributesYieldCorrectOutput(){
        String uid = "123456789";
        Timestamp ts1= new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 10, 8, 1, Timestamp.Meridiem.PM);
        Schedule schedule = new Schedule(uid);
        Post post1= SimpleTestPost.postWith("Valid",
                ts1, new Location("Lausanne",10,10),10, Sport.SOCCER);

        schedule.addPost(post1);
        Map<String,Object> result= schedule.getAllAttributes();
        Map<String,Object> expected=  Map.of("UID",uid,"posts", List.of(post1));
        Assert.assertEquals(expected,result);
    }

    @Test
    public void equalsWithSelfWorks(){
        String uid = "123456789";
        Timestamp ts1= new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 10, 8, 1, Timestamp.Meridiem.PM);
        Schedule schedule = new Schedule(uid);
        Assert.assertEquals(schedule,schedule);
    }

    @Test
    public void equalsWithWrongScheduleFails(){
        String uid = "123456789";
        Timestamp ts1= new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 10, 8, 1, Timestamp.Meridiem.PM);
        Schedule schedule = new Schedule(uid);
        Assert.assertNotEquals(schedule,new Schedule());
    }

    @Test
    public void equalsWithCloseScheduleFails(){
        String uid = "123456789";
        Timestamp ts1= new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 10, 8, 1, Timestamp.Meridiem.PM);
        Schedule schedule = new Schedule(uid);
        Post post1= SimpleTestPost.postWith("Valid",
                ts1, new Location("Lausanne",10,10),10, Sport.SOCCER);

        schedule.addPost(post1);
        Assert.assertNotEquals(schedule,new Schedule(uid));
    }

    @Test
    public void equalsWithSameAttributesWorks(){
        String uid = "123456789";
        Timestamp ts1= new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 10, 8, 1, Timestamp.Meridiem.PM);
        Schedule schedule = new Schedule(uid);
        Post post1= SimpleTestPost.postWith("Valid",
                ts1, new Location("Lausanne",10,10),10, Sport.SOCCER);

        schedule.addPost(post1);

        Schedule similar= new Schedule(uid);
        similar.addPost(post1);
        Assert.assertEquals(schedule,similar);

    }

    @Test
    public void equalsWithSWrongObjectFails(){
        String uid = "123456789";
        Timestamp ts1= new Timestamp(Calendar.getInstance().get(Calendar.YEAR), Month.JANUARY, 10, 8, 1, Timestamp.Meridiem.PM);
        Schedule schedule = new Schedule(uid);
        Post post1= SimpleTestPost.postWith("Valid",
                ts1, new Location("Lausanne",10,10),10, Sport.SOCCER);
        schedule.addPost(post1);
        Assert.assertNotEquals(schedule,new Object());

    }

}
