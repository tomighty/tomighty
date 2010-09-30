package org.tomighty.bus;

public interface Subscriber<T> {

	void receive(T message);
	
}
