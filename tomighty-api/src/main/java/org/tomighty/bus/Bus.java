package org.tomighty.bus;

public interface Bus {

    <T> void subscribe(Subscriber<T> subscriber, Class<T> messageType);

    <T> void unsubscribe(Subscriber<T> subscriber, Class<T> messageType);

    void publish(Object message);

}
