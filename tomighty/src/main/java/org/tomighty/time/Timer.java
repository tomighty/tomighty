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

import org.tomighty.bus.Bus;
import org.tomighty.bus.messages.time.TimerEnd;
import org.tomighty.bus.messages.time.TimerInterrupted;
import org.tomighty.bus.messages.time.TimerTick;

import javax.inject.Inject;

public class Timer {

	private static final int ONE_SECOND = 1000;
	
	@Inject private Bus bus;
	
	private Time time;
	private java.util.Timer timer;
	
	public void start(Time time) {
		stop();
		this.time = time;
		timer = new java.util.Timer(getClass().getSimpleName());
		timer.scheduleAtFixedRate(new Updater(), ONE_SECOND, ONE_SECOND);
	}

	public void stop() {
		if(timer != null) {
			timer.cancel();
			bus.publish(new TimerInterrupted());
		}
	}
	
	private class Updater extends TimerTask {
		@Override
		public void run() {
			time = time.minusOneSecond();
			bus.publish(new TimerTick(time));
			if(time.isZero()) {
				stop();
				bus.publish(new TimerEnd());
			}
		}
	}

}
