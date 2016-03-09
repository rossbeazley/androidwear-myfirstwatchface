package uk.co.rossbeazley.wear.android.ui.config.service;

import java.util.List;

public interface StringPersistence {

    List<String> stringsForKey(String key);

    boolean hasKey(String key);

    void storeStringsForKey(String currentItemId, List<String> strings);
}
