package uk.co.rossbeazley.wear.android.ui.config;

import java.util.HashMap;
import java.util.List;

public class StubStringPersistence implements StringPersistence {

    private HashMap<String, List<String>> map;

    public StubStringPersistence(HashMap<String, List<String>> map) {
        this.map = map;
    }

    @Override
    public List<String> stringsForKey(String key) {
        return map.get(key);
    }

    @Override
    public boolean hasKey(String key){
        return map.containsKey(key);
    }

    @Override
    public void storeStringsForKey(String currentItemId, List<String> strings) {
        map.put(currentItemId,strings);
    }
}
