package org.tomighty.bus.messages;

import org.tomighty.State;

public class ChangeState {

	private final Class<? extends State> stateClass;

	public ChangeState(Class<? extends State> stateClass) {
		this.stateClass = stateClass;
	}
	
	public Class<? extends State> getStateClass() {
		return stateClass;
	}

}
