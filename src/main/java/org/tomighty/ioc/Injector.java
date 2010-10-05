/*
Copyright 2010 Célio Cidral Junior

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

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
