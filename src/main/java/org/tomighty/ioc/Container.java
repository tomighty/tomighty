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
	
	private final Binder binder;
	private final Factory factory = new Factory();
	private final Injector injector = new Injector();
	private final Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();

	public Container() {
		map.put(Container.class, this);
		map.put(Factory.class, factory);
		map.put(Injector.class, injector);
		factory.container(this);
		injector.container(this);
		binder = factory.create(Binder.class);
	}

	public Binder binder() {
		return binder;
	}
	
	public <T> T get(Class<T> clazz) {
		return get(clazz, null);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz, Object needer) {
		Object instance = map.get(clazz);
		if(instance == null) {
			instance = create(clazz, needer);
			map.put(clazz, instance);
		}
		return (T) instance;
	}
	
	private <T> T create(Class<T> clazz, Object needer) {
		Binding<T> binding = binder().bindingFor(clazz);
		if(binding != null) {
			return binding.instance(needer);
		}
		return factory.create(clazz, needer);
	}

}
