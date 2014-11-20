package uk.co.rossbeazley.wear.days;

import java.util.HashMap;
import java.util.Map;

/**
* Created by beazlr02 on 20/11/2014.
*/
public class DefaultMap<Key, Value> {
    final private Map<Key, Value> map;
    private final Value defaultValue;

    DefaultMap(Value defaultValue) {
        this.defaultValue = defaultValue;
        map = new HashMap<Key, Value>();
    }

    public void put(Key key, Value value) {
        map.put(key,value);
    }

    public Value get(Key value) {
        return map.containsKey(value)? map.get(value) : defaultValue;
    }
}
