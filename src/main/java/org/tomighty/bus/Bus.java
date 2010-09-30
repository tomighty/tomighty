package org.tomighty.bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bus {
	
	private static Map<Class<?>, List<Subscriber<?>>> map = new HashMap<Class<?>, List<Subscriber<?>>>();

	public static <T> void subscribe(Subscriber<T> subscriber, Class<T> messageType)
	{
		List<Subscriber<?>> list = map.get(messageType);
		if(list == null) {
			list = new ArrayList<Subscriber<?>>();
			map.put(messageType, list);
		}
		list.add(subscriber);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void publish(Object message) {
		List<Subscriber<?>> list = map.get(message.getClass());
		if(list == null) {
			return;
		}
		for(Subscriber subscriber : list) {
			subscriber.receive(message);
		}
	}

}
