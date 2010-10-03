package org.tomighty.ioc;

import java.util.HashMap;
import java.util.Map;

import org.tomighty.util.New;

public class Container {
	
	private Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
	
	private Injector injector = new Injector(this);
	
	public Container() {
		map.put(Container.class, this);
		map.put(Injector.class, injector);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz) {
		Object instance = map.get(clazz);
		if(instance == null) {
			instance = New.instanceOf(clazz);
			injector.inject(instance);
			map.put(clazz, instance);
		}
		return (T) instance;
	}

}
