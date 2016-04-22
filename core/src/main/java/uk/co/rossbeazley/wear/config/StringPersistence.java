package uk.co.rossbeazley.wear.config;

import java.util.List;

public interface StringPersistence {

    List<String> stringsForKey(String key);

    boolean hasKey(String key);

    void storeStringsForKey(String currentItemId, List<String> strings);
}
