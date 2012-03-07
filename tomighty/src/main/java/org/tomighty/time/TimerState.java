package org.tomighty.time;

import org.tomighty.Phase;

class TimerState {

    private Time time;
    private final Phase phase;

    public TimerState(Time time, Phase phase) {
        this.time = time;
        this.phase = phase;
    }

    public Time getTime() {
        return time;
    }

    public Phase getPhase() {
        return phase;
    }

    public void decreaseOneSecond() {
        time = time.minusOneSecond();
    }

}
