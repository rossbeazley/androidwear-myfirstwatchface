package uk.co.rossbeazley.wear;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BiMap<Key, Value> {

    private HashMap<Key, Value> keyToValueMap = new HashMap<>();
    private HashMap<Value, Key> valuetoKeyMap = new HashMap<>();

    public void put(Key key, Value value) {
        keyToValueMap.put(key, value);
        valuetoKeyMap.put(value, key);
    }

    public Key keyForValue(Value value) {
        return valuetoKeyMap.get(value);
    }

    public Value valueForKey(Key key) {
        return keyToValueMap.get(key);
    }

    public List<Key> keyList() {
        return new ArrayList<>(valuetoKeyMap.values());
    }
}
