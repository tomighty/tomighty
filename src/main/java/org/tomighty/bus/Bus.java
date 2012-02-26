/*
 * Copyright (c) 2010-2012 Célio Cidral Junior.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package org.tomighty.bus;

import org.tomighty.log.Log;

import java.util.*;

public class Bus {

    private final Log log = new Log(getClass());

	private Map<Class<?>, List<Subscriber<?>>> subscribersByType;
	
	public Bus() {
		subscribersByType = Collections.synchronizedMap(new HashMap<Class<?>, List<Subscriber<?>>>());
	}

	public <T> void subscribe(Subscriber<T> subscriber, Class<T> messageType) {
		List<Subscriber<?>> list = subscribersByType.get(messageType);
		if(list == null) {
			list = new ArrayList<Subscriber<?>>();
			subscribersByType.put(messageType, list);
		}
		synchronized (list) {
			list.add(subscriber);
		}
	}
	
	public <T> void unsubscribe(Subscriber<T> subscriber, Class<T> messageType) {
		List<Subscriber<?>> list = subscribersByType.get(messageType);
		if(list != null) {
			synchronized (list) {
				list.remove(subscriber);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void publish(Object message) {
		Class<? extends Object> messageType = message.getClass();
		List<Subscriber<?>> list = subscribersOf(messageType);
		if(list == null) {
			return;
		}
		for(Subscriber subscriber : list) {
			try {
				subscriber.receive(message);
			} catch(Throwable error) {
				log.error("Error delivering message to subscriber: "+subscriber, error);
			}
		}
	}

	private <T> List<Subscriber<?>> subscribersOf(Class<T> messageType) {
		List<Class<?>> types = typesCompatibleWith(messageType);
		if(types == null) {
			return null;
		}
		List<Subscriber<?>> result = new ArrayList<Subscriber<?>>();
		for(Class<?> type : types) {
			List<Subscriber<?>> list = subscribersByType.get(type);
			if(list != null) {
				synchronized (list) {
					result.addAll(list);
				}
			}
		}
		return result;
	}
	
	private List<Class<?>> typesCompatibleWith(Class<?> type) {
		List<Class<?>> list = null;
		synchronized (subscribersByType) {
			for(Class<?> candidate : subscribersByType.keySet()) {
				if(candidate.isAssignableFrom(type)) {
					if(list == null) {
						list = new ArrayList<Class<?>>();
					}
					list.add(candidate);
				}
			}
		}
		return list;
	}

}
