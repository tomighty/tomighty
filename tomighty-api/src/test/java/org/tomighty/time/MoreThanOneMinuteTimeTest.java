package org.tomighty.time;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoreThanOneMinuteTimeTest {

    private Time time;

    @Before
    public void setUp() throws Exception {
        time = Time.seconds(3 * 60 + 15);
    }

    @Test
    public void isNotZero() {
        assertFalse(time.isZero());
    }
    
    @Test
    public void hasThreeMinutes() {
        assertEquals(3, time.minutes());
    }

    @Test
    public void hasFifteenSeconds() {
        assertEquals(15, time.seconds());
    }

    @Test
    public void shortestStringRepresentation() {
        assertEquals("4", time.shortestString());
    }

    @Test
    public void standardStringRepresentation() {
        assertEquals("03:15", time.toString());
    }

}
