package org.tomighty.util;

public class New {

	public static <T> T instanceOf(Class<T> clazz) {
		try {
			T instance = clazz.newInstance();
			return instance;
		} catch (Exception e) {
			throw new RuntimeException("Failed to create an instance of "+clazz, e);
		}
	}

}
