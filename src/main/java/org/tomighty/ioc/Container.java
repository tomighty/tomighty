package org.tomighty.ioc;

import java.util.HashMap;
import java.util.Map;

public class Container {
	
	private final Factory factory = new Factory();
	
	private final Injector injector = new Injector(this, factory);
	
	private final Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
	
	public Container() {
		factory.injector(injector);
		map.put(Container.class, this);
		map.put(Factory.class, factory);
		map.put(Injector.class, injector);
	}
	
	public <T> T get(Class<T> clazz) {
		return get(clazz, null);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz, Object needer) {
		Object instance = map.get(clazz);
		if(instance == null) {
			instance = factory.create(clazz, needer);
			map.put(clazz, instance);
		}
		return (T) instance;
	}

}
