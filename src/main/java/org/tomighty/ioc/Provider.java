package org.tomighty.ioc;

public interface Provider<T> {

	T createFor(Object needer);

}
