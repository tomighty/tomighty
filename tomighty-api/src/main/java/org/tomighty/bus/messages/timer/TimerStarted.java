package org.tomighty.bus.messages.timer;

import org.tomighty.Phase;
import org.tomighty.time.Time;

public class TimerStarted extends TimerEvent {

    public TimerStarted(Time time, Phase phase) {
        super(time, phase);
    }

}
