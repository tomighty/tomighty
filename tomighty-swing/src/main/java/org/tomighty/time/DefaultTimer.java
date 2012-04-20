/*
 * Copyright (c) 2010-2012 Célio Cidral Junior.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package org.tomighty.time;

import java.util.TimerTask;

import org.tomighty.Phase;
import org.tomighty.bus.Bus;
import org.tomighty.bus.messages.timer.TimerFinished;
import org.tomighty.bus.messages.timer.TimerInterrupted;
import org.tomighty.bus.messages.timer.TimerStarted;
import org.tomighty.bus.messages.timer.TimerTick;

import javax.inject.Inject;

public class DefaultTimer implements Timer {

	private static final int ONE_SECOND = 1000;

    private TimerState state;
    private java.util.Timer timer;
    private final Bus bus;

    @Inject
    public DefaultTimer(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void start(Time time, Phase phase) {
        interrupt();
        state = new TimerState(time, phase);
        scheduleTimer();
        bus.publish(new TimerStarted(time, phase));
    }

    @Override
	public void interrupt() {
		if(timer != null) {
			timer.cancel();
			bus.publish(new TimerInterrupted(state.getTime(), state.getPhase()));
		}
	}

    private void scheduleTimer() {
        timer = new java.util.Timer(getClass().getSimpleName());
        timer.scheduleAtFixedRate(new Tick(), ONE_SECOND, ONE_SECOND);
    }

    private void tick() {
        state.decreaseOneSecond();

        bus.publish(new TimerTick(state.getTime(), state.getPhase()));

        if(state.getTime().isZero())
            finish();
    }

    private void finish() {
        timer.cancel();
        bus.publish(new TimerFinished(state.getPhase()));
    }

    private class Tick extends TimerTask {
        @Override
        public void run() {
            tick();
        }
    }

}

