package org.tomighty.time;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

public class TimeEquivalenceTest {

    private Time time;

    @Before
    public void setUp() throws Exception {
        time = Time.minutes(999);
    }

    @Test
    public void twoEqualTimes() {
        Time sameTime = Time.minutes(999);
        assertTrue("Should be equal", time.equals(sameTime));
    }

    @Test
    public void twoDifferentTimes() {
        Time differentTime = Time.minutes(123);
        assertFalse("Should not be equal", time.equals(differentTime));
    }

    @Test
    public void callEqualsPassingAnotherObject() {
        assertFalse("Should not be equal", time.equals("not a time"));
    }

    @Test
    public void callEqualsPassingNull() {
        assertFalse("Should not be equal", time.equals(null));
    }

}
