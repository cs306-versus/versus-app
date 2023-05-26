package com.github.versus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.github.versus.posts.Timestamp;

import org.junit.Test;

import java.time.Month;

public final class TimestampTest {

    @Test
    public void testEquals(){
        Timestamp timestamp = new Timestamp(2023, Month.APRIL, 7, 7, 0, Timestamp.Meridiem.AM);
        assertEquals(timestamp, timestamp);
    }

    @Test
    public void testEquals2(){
        Timestamp timestamp = new Timestamp(2023, Month.APRIL, 7, 7, 0, Timestamp.Meridiem.AM);
        assertNotEquals(timestamp, new Object());
    }

    @Test
    public void testIsBefore(){
        Timestamp timestamp = new Timestamp(2023, Month.APRIL, 7, 7, 0, Timestamp.Meridiem.AM);
        Timestamp timestamp2 = new Timestamp(2022, Month.APRIL, 7, 7, 0, Timestamp.Meridiem.AM);
        assertEquals(1, timestamp.isBefore(timestamp2));
    }

    @Test
    public void testIsBefore2(){
        Timestamp timestamp = new Timestamp(2023, Month.APRIL, 7, 7, 0, Timestamp.Meridiem.AM);
        Timestamp timestamp2 = new Timestamp(2023, Month.MARCH, 7, 7, 0, Timestamp.Meridiem.AM);
        assertEquals(1, timestamp.isBefore(timestamp2));
    }

    @Test
    public void testIsBefore3(){
        Timestamp timestamp = new Timestamp(2023, Month.APRIL, 7, 7, 0, Timestamp.Meridiem.AM);
        Timestamp timestamp2 = new Timestamp(2023, Month.APRIL, 7, 7, 1, Timestamp.Meridiem.AM);
        assertEquals(-1, timestamp.isBefore(timestamp2));
    }

}
