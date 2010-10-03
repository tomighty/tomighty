package org.tomighty.ioc;

import java.lang.reflect.Field;

public class Injector {

	private final Container container;

	public Injector(Container container) {
		this.container = container;
	}

	public void inject(Object instance) {
		Class<?> clazz = instance.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields) {
			boolean injectable = field.isAnnotationPresent(Inject.class);
			if(injectable) {
				Class<?> dependencyType = field.getType();
				Object dependency = container.get(dependencyType);
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				try {
					field.set(instance, dependency);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				field.setAccessible(accessible);
			}
		}
	}

}
