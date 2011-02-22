/*
 * Copyright (c) 2010 Célio Cidral Junior
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.tomighty.ioc;

import java.util.HashMap;
import java.util.Map;

public class Container {
	
	private final Factory factory = new Factory();
	
	private final Injector injector = new Injector();
	
	private final Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
	
	public Container() {
		map.put(Container.class, this);
		map.put(Factory.class, factory);
		map.put(Injector.class, injector);
		factory.container(this);
		injector.container(this);
	}
	
	public <T> T get(Class<T> clazz) {
		return get(clazz, null);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String className) {
		try {
			Class<T> clazz = (Class<T>) Class.forName(className);
			return get(clazz, null);
		} catch (ClassNotFoundException cause) {
			throw new IllegalArgumentException("Class not found: "+className, cause);
		}
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
