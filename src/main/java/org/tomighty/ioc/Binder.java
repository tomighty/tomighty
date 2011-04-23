/*
 * Copyright (c) 2010-2011 Célio Cidral Junior
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

public class Binder {
	
	@Inject private Factory factory;
	private Map<Class<?>, Binding<?>> bindings;
	
	public Binder() {
		bindings = new HashMap<Class<?>, Binding<?>>();
	}

	@SuppressWarnings("unchecked")
	public <T> Binding<T> bind(Class<T> type) {
		Binding<T> binding = factory.create(Binding.class);
		bindings.put(type, binding);
		return binding;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Binding<T> bindingFor(Class<T> type) {
		return (Binding<T>) bindings.get(type);
	}

}
