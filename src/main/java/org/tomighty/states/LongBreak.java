package org.tomighty.states;

import org.tomighty.time.Time;

public class LongBreak extends Break {

	@Override
	protected String lengthName() {
		return "Long";
	}

	@Override
	protected Time time() {
		return new Time(15);
	}

}
