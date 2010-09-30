/*
Copyright 2010 Célio Cidral Junior

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package org.tomighty.time;

import java.util.TimerTask;

public class Timer {

	private static final int ONE_SECOND = 1000;
	private final String name;
	private Time time;
	private TimerListener listener;
	private java.util.Timer timer;
	
	public Timer(String name) {
		this.name = name;
	}

	public void listener(TimerListener listener) {
		this.listener = listener;
	}

	public void start(Time time) {
		if(timer != null) {
			throw new IllegalStateException("Cannot start more than once");
		}
		this.time = time;
		timer = new java.util.Timer(getClass().getName()+": "+name);
		timer.scheduleAtFixedRate(new Updater(), ONE_SECOND, ONE_SECOND);
	}

	public void stop() {
		timer.cancel();
	}
	
	private class Updater extends TimerTask {
		@Override
		public void run() {
			time = time.minusOneSecond();
			listener.tick(time);
			if(time.isZero()) {
				stop();
			}
		}
	}

}
