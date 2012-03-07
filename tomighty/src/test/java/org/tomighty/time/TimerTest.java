package org.tomighty.time;

import org.junit.Before;
import org.junit.Test;
import org.tomighty.Phase;
import org.tomighty.bus.messages.timer.TimerFinished;
import org.tomighty.bus.messages.timer.TimerInterrupted;
import org.tomighty.bus.messages.timer.TimerStarted;
import org.tomighty.bus.messages.timer.TimerTick;
import org.tomighty.mock.bus.MockBus;

import java.util.List;

import static junit.framework.Assert.*;

public class TimerTest {

    private MockBus bus;
    private Timer timer;

    @Before
    public void setUp() throws Exception {
        bus = new MockBus();
        timer = new DefaultTimer(bus);
    }

    @Test(timeout = 5000)
    public void startTimerAndWaitToFinish() {
        timer.start(Time.seconds(3), Phase.POMODORO);

        List<Object> messages = bus.waitUntilNumberOfMessagesReach(5);

        assertEquals("Amount of published messages", 5, messages.size());
        assertEquals("First message", TimerStarted.class, messages.get(0).getClass());
        assertEquals("Second message", TimerTick.class, messages.get(1).getClass());
        assertEquals("Third message", TimerTick.class, messages.get(2).getClass());
        assertEquals("Fourth message", TimerTick.class, messages.get(3).getClass());
        assertEquals("Fifth message", TimerFinished.class, messages.get(4).getClass());

        TimerStarted timerStarted = (TimerStarted) messages.get(0);
        assertEquals("Initial time", Time.seconds(3), timerStarted.getTime());
        assertEquals("Phase when timer started", Phase.POMODORO, timerStarted.getPhase());

        TimerTick firstTick = (TimerTick) messages.get(1);
        assertEquals("First tick's time", Time.seconds(2), firstTick.getTime());
        assertEquals("First tick's phase", Phase.POMODORO, firstTick.getPhase());

        TimerTick secondTick = (TimerTick) messages.get(2);
        assertEquals("Second tick's time", Time.seconds(1), secondTick.getTime());
        assertEquals("Second tick's phase", Phase.POMODORO, secondTick.getPhase());

        TimerTick thirdTick = (TimerTick) messages.get(3);
        assertEquals("Third tick's time", Time.seconds(0), thirdTick.getTime());
        assertEquals("Third tick's phase", Phase.POMODORO, thirdTick.getPhase());
        
        TimerFinished timerFinished = (TimerFinished) messages.get(4);
        assertEquals("Phase when timer finished", Phase.POMODORO, timerFinished.getPhase());
    }

    @Test(timeout = 5000)
    public void startTimerAndStopAfterFirstTick() {
        timer.start(Time.seconds(3), Phase.BREAK);

        List<Object> messages = bus.waitUntilNumberOfMessagesReach(2);

        timer.interrupt();

        assertEquals("Amount of published messages", 3, messages.size());
        assertEquals("First message", TimerStarted.class, messages.get(0).getClass());
        assertEquals("Second message", TimerTick.class, messages.get(1).getClass());
        assertEquals("Third message", TimerInterrupted.class, messages.get(2).getClass());

        TimerStarted timerStarted = (TimerStarted) messages.get(0);
        assertEquals("Initial time", Time.seconds(3), timerStarted.getTime());
        assertEquals("Phase when timer started", Phase.BREAK, timerStarted.getPhase());

        TimerTick tick = (TimerTick) messages.get(1);
        assertEquals("Tick's time", Time.seconds(2), tick.getTime());
        assertEquals("Tick's phase", Phase.BREAK, tick.getPhase());

        TimerInterrupted timerInterrupted = (TimerInterrupted) messages.get(2);
        assertEquals("Time when timer was interrupted", Time.seconds(2), timerInterrupted.getTime());
        assertEquals("Phase when timer was interrupted", Phase.BREAK, timerInterrupted.getPhase());
    }

}
