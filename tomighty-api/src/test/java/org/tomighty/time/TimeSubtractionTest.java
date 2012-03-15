package org.tomighty.time;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeSubtractionTest {
    
    @Test                           
    public void subtractingOneSecond() {
        Time threeSeconds = Time.seconds(3);
        Time twoSeconds = Time.seconds(2);
        assertEquals(twoSeconds, threeSeconds.minusOneSecond());
    }
    
    
}
