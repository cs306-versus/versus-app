package com.github.versus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.github.versus.schedule.Schedule;

import org.junit.Test;

public final class ScheduleTest {

    @Test
    public void testDefaultConstructor(){
        assertNotNull(new Schedule());
    }

    @Test
    public void testConstructorWithParameter(){
        assertNotNull(new Schedule("123456789"));
    }

    @Test
    public void testGetUID(){
        String uid = "123456789";
        assertEquals(uid, new Schedule(uid).getUID());
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
