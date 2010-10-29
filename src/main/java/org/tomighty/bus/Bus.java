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

package org.tomighty.bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bus {
	
	private Map<Class<?>, List<Subscriber<?>>> map = new HashMap<Class<?>, List<Subscriber<?>>>();

	public <T> void subscribe(Subscriber<T> subscriber, Class<T> messageType) {
		List<Subscriber<?>> list = map.get(messageType);
		if(list == null) {
			list = new ArrayList<Subscriber<?>>();
			map.put(messageType, list);
		}
		list.add(subscriber);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void publish(Object message) {
		List<Subscriber<?>> list = map.get(message.getClass());
		if(list == null) {
			return;
		}
		for(Subscriber subscriber : list) {
			subscriber.receive(message);
		}
	}

}
