package org.tomighty.ui.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LightTest {

    private Light light;

    @Before
    public void setUp() throws Exception {
        light = new Light(123);
    }

    @Test
    public void isOffByDefault() {
        assertFalse(light.isOn());
    }

    @Test
    public void turnOn() {
        light.turnOn();
        assertTrue(light.isOn());
    }

    @Test
    public void turnOnThenOff() {
        light.turnOn();
        light.turnOff();
        assertFalse(light.isOn());
    }

    @Test
    public void number() {
        assertEquals(123, light.number());
    }

}
