package org.tomighty.ui.swing.gauge;

import org.junit.Before;
import org.junit.Test;
import org.tomighty.ui.swing.gauge.GaugeButtonModel;
import org.tomighty.ui.swing.gauge.Light;

import java.util.List;

import static junit.framework.Assert.*;

public class GaugeButtonModelTest {

    private GaugeButtonModel model;

    @Before
    public void setUp() throws Exception {
        model = new GaugeButtonModel(3);
    }

    @Test
    public void lightListIsNotNull() {
        List<Light> lights = model.lights();
        assertNotNull("Light list should not be null", lights);
    }

    @Test
    public void lightCountIsThree() {
        List<Light> lights = model.lights();
        assertEquals("There should be 3 lights", 3, lights.size());
    }

    @Test
    public void lightsAreSequentiallyNumberedStartingWithZero() {
        List<Light> lights = model.lights();
        assertEquals("Wrong light number", 0, lights.get(0).number());
        assertEquals("Wrong light number", 1, lights.get(1).number());
        assertEquals("Wrong light number", 2, lights.get(2).number());
    }

    @Test
    public void allLightsAreOffByDefault() {
        List<Light> lights = model.lights();
        assertFalse("Light should be off", lights.get(0).isOn());
        assertFalse("Light should be off", lights.get(1).isOn());
        assertFalse("Light should be off", lights.get(2).isOn());
    }

    @Test
    public void turnNextLightOn() {
        model.turnNextLightOn();

        List<Light> lights = model.lights();
        assertTrue("Light should be on", lights.get(0).isOn());
        assertFalse("Light should be off", lights.get(1).isOn());
        assertFalse("Light should be off", lights.get(2).isOn());

        model.turnNextLightOn();

        lights = model.lights();
        assertTrue("Light should be on", lights.get(0).isOn());
        assertTrue("Light should be on", lights.get(1).isOn());
        assertFalse("Light should be off", lights.get(2).isOn());

        model.turnNextLightOn();

        lights = model.lights();
        assertTrue("Light should be on", lights.get(0).isOn());
        assertTrue("Light should be on", lights.get(1).isOn());
        assertTrue("Light should be on", lights.get(2).isOn());
    }

    @Test
    public void tryToTurnNextLightOnAfterAllLightsAreOn() {
        model.turnNextLightOn();
        model.turnNextLightOn();
        model.turnNextLightOn();
        model.turnNextLightOn();

        List<Light> lights = model.lights();
        assertTrue("Light should be on", lights.get(0).isOn());
        assertTrue("Light should be on", lights.get(1).isOn());
        assertTrue("Light should be on", lights.get(2).isOn());
    }

    @Test
    public void areAllLightsOn() {
        assertFalse("Not all lights should be on", model.areAllLightsOn());

        model.turnNextLightOn();
        assertFalse("Not all lights should be on", model.areAllLightsOn());

        model.turnNextLightOn();
        assertFalse("Not all lights should be on", model.areAllLightsOn());

        model.turnNextLightOn();
        assertTrue("All lights should be on", model.areAllLightsOn());
    }

    @Test
    public void turnAllLightsOff() {
        model.turnNextLightOn();
        model.turnNextLightOn();
        model.turnNextLightOn();

        model.turnAllLightsOff();

        List<Light> lights = model.lights();
        assertFalse("Light should be off", lights.get(0).isOn());
        assertFalse("Light should be off", lights.get(1).isOn());
        assertFalse("Light should be off", lights.get(2).isOn());
    }

}
