package com.github.versus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.github.versus.posts.Location;

import org.junit.Test;

public class LocationTest {

    @Test
    public void testToString(){
        assertEquals("l (1.0,1.0)", new Location("l", 1d, 1d).toString());
    }

    @Test
    public void testSame(){
        Location l = new Location("l", 1d, 1d);
        assertEquals(l, l);
    }

    @Test
    public void testDifferentType(){
        assertNotEquals(new Location("l", 1d, 1d), new Object());
    }

}
