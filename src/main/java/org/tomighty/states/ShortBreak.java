package org.tomighty.states;

import org.tomighty.time.Time;

public class ShortBreak extends Break {

	@Override
	protected String lengthName() {
		return "Short";
	}

	@Override
	protected Time time() {
		return new Time(0, 5);
	}

}
