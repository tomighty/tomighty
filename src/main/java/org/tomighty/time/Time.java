package org.tomighty.time;

public class Time {
	
	private int seconds;
	
	private Time() {}

	public Time(int minutes) {
		this(minutes, 0);
	}

	public Time(int minutes, int seconds) {
		this.seconds = minutes * 60 + seconds;
	}

	public boolean isZero() {
		return seconds == 0;
	}

	public Time minusOneSecond() {
		Time time = new Time();
		time.seconds = this.seconds - 1;
		return time;
	}
	
	@Override
	public String toString() {
		int minutes = (int) Math.floor(seconds / 60.0);
		int secs = seconds - minutes * 60;
		return (minutes<10?"0":"") + minutes + ":" + (secs<10?"0":"") + secs;
	}

}
