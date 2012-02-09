package org.tomighty.ui.model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GaugeButtonModel extends DefaultButtonModel {

    private final List<Light> lights;

    public GaugeButtonModel(int numberOfLights) {
        lights = new ArrayList<Light>();
        for(int number = 0; number < numberOfLights; number++)
            lights.add(new Light(number));
    }

    public List<Light> lights() {
        return lights;
    }

    public void turnNextLightOn() {
        Light light = findFirstLightOff();
        if(light != null)
            light.turnOn();
    }

    public boolean areAllLightsOn() {
        return numberOfLightsOn() == lights.size();
    }

    public void turnAllLightsOff() {
        for(Light light : lights)
            light.turnOff();
    }

    private Light findFirstLightOff() {
        for(Light light : lights)
            if(!light.isOn())
                return light;
        return null;
    }

    private int numberOfLightsOn() {
        int count = 0;
        for(Light light : lights)
            if(light.isOn())
                count++;
        return count;
    }

}
