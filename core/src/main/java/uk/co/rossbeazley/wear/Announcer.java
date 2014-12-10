package uk.co.rossbeazley.wear;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** nicked from GOOS, modifed by me*/
public class Announcer<T> {
    private final T proxy;
    private final List<T> listeners = new ArrayList<T>();
    private Producer<T> producer;


    private Announcer(Class<? extends T> listenerType) {
        producer = new Producer<T>() {
            @Override
            public void observed(T observer) {
            }
        };

        proxy = listenerType.cast(Proxy.newProxyInstance(
                listenerType.getClassLoader(),
                new Class<?>[]{listenerType},
                new InvocationHandler() {
                    public Object invoke(Object aProxy, Method method, Object[] args) throws Throwable {
                        announce(method, args);
                        return null;
                    }
                }));
    }

    public void addListener(T listener) {
        listeners.add(listener);
        producer.observed(listener);
    }

    public Announcer<T> addListeners(T... listeners) {
        for(T t : listeners) {
            addListener(t);
        }
        return this;
    }

    public void removeListener(T listener) {
        listeners.remove(listener);
    }

    public T announce() {
        return proxy;
    }

    private void announce(Method m, Object[] args) {
        for (T listener : listeners) {
        try {

                m.invoke(listener, args);

        }
        catch (IllegalAccessException e) {
            throw new IllegalArgumentException("could not to listener", e);
        }
        catch (InvocationTargetException e) {
            Throwable cause = e.getCause();

            if (cause instanceof RuntimeException) {
                throw (RuntimeException)cause;
            }
            else if (cause instanceof Error) {
                throw (Error)cause;
            }
            else {
                throw new UnsupportedOperationException("listener threw exception", cause);
            }
        }
        }
    }

    public static <T> Announcer<T> to(Class<? extends T> listenerType) {
        return new Announcer<T>(listenerType);
    }

    public void registerProducer(Producer<T> producer) {

        this.producer = producer;
    }

    public static interface Producer<T> {

        public abstract void observed(T observer);
    }
}