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
