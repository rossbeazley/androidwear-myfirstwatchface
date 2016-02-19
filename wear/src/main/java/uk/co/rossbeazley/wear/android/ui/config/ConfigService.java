package uk.co.rossbeazley.wear.android.ui.config;

import java.util.List;

/**
 * Created by beazlr02 on 19/02/16.
 */
class ConfigService {

    private StringPersistence persistence;

    public ConfigService(StringPersistence persistence) {
        this.persistence = persistence;
    }

    public void configure(String item) {
        persistence.hasKey(item);
    }

    public List<String> configItemsList() {
        return persistence.stringsForKey("configItems");
    }
}
