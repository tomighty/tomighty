package org.tomighty.ioc;

import java.lang.reflect.Field;

public class Injector {

	private final Container container;
	
	private final Factory factory;

	public Injector(Container container, Factory factory) {
		this.container = container;
		this.factory = factory;
	}

	public void inject(Object instance) {
		Class<?> clazz = instance.getClass();
		do {
			inject(instance, clazz);
			clazz = clazz.getSuperclass();
		} while(!clazz.equals(Object.class));
	}

	private void inject(Object instance, Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields) {
			boolean injectable = field.isAnnotationPresent(Inject.class);
			if(!injectable) {
				continue;
			}
			Class<?> dependencyClass = field.getType();
			Object dependency;
			boolean createNew = field.isAnnotationPresent(New.class);
			if(createNew) {
				dependency = factory.create(dependencyClass, instance);
			} else {
				dependency = container.get(dependencyClass, instance);
			}
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
