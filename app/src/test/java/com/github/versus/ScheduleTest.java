package com.github.versus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.github.versus.schedule.Schedule;

import org.junit.Test;

import java.util.ArrayList;

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

}
