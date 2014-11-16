package uk.co.rossbeazley.wear;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.co.rossbeazley.wear.minutes.MinutesFromTick;
import uk.co.rossbeazley.wear.seconds.Seconds;

/** nicked from GOOS*/
public class Announcer<T> {
    private final T proxy;
    private final List<T> listeners = new ArrayList<T>();


    public Announcer(Class<? extends T> listenerType) {
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
    }

    public Announcer<T> addListeners(T... listeners) {
        Collections.addAll(this.listeners, listeners);
        return this;
    }

    public void removeListener(T listener) {
        listeners.remove(listener);
    }

    public T announce() {
        return proxy;
    }

    private void announce(Method m, Object[] args) {
        try {
            for (T listener : listeners) {
                m.invoke(listener, args);
            }
        }
        catch (IllegalAccessException e) {
            throw new IllegalArgumentException("could not invoke listener", e);
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

    public static <T> Announcer<T> to(Class<? extends T> listenerType) {
        return new Announcer<T>(listenerType);
    }
}