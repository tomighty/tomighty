package org.tomighty.ui.model;

public class Light {

    private final int number;
    private boolean isOn;

    public Light(int number) {
        this.number = number;
    }

    public boolean isOn() {
        return isOn;
    }

    public int number() {
        return number;
    }

    public void turnOff() {
        isOn = false;
    }

    public void turnOn() {
        isOn = true;
    }

}
