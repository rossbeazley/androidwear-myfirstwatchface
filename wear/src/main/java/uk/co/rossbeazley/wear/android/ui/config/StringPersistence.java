package uk.co.rossbeazley.wear.android.ui.config;

import java.util.List;

/**
 * Created by beazlr02 on 19/02/16.
 */
interface StringPersistence {

    List<String> stringsForKey(String key);

    void hasKey(String key);
}
