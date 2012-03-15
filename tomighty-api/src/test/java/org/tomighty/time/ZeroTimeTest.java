package org.tomighty.time;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZeroTimeTest {

    private Time time;

    @Before
    public void setUp() throws Exception {
        time = Time.minutes(0);
    }

    @Test
    public void isZero() {
        assertTrue(time.isZero());
    }
    
    @Test
    public void hasZeroMinutes() {
        assertEquals(0, time.minutes());
    }

    @Test
    public void hasZeroSeconds() {
        assertEquals(0, time.seconds());
    }

    @Test
    public void shortestStringRepresentation() {
        assertEquals("0", time.shortestString());
    }

    @Test
    public void standardStringRepresentation() {
        assertEquals("00:00", time.toString());
    }

}
