package uk.co.rossbeazley.wear.android.ui.config.service;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.wear.Announcer;

import static java.util.Arrays.asList;

public class ConfigService {

    private final Announcer<Listener> listenerAnnouncer;
    private String currentItemId;

    public List<String> selectedConfigOptions() {
        return persistence.stringsForKey(currentItemId);
    }

    public void chooseOption(String expectedOption) {

        persistence.storeStringsForKey(currentItemId + "Choice", asList(expectedOption));
    }

    public String currentOptionForItem(String id) {
        return persistence.stringsForKey(id + "Choice").get(0);
    }

    public void initialiseDefaults(ConfigItem... configItems) {

        if (alreadyHasDataStored()) return;

        storeDefaults(configItems);
    }

    private void storeDefaults(ConfigItem[] configItems) {
        List<String> IDs = new ArrayList<>(configItems.length);
        for (ConfigItem configItem : configItems) {
            String id = configItem.itemId();
            IDs.add(id);
            persistence.storeStringsForKey(id, configItem.options());
            persistence.storeStringsForKey(id + "Choice", asList(configItem.defaultOption()));
        }
        persistence.storeStringsForKey("configItems", IDs);
    }

    private boolean alreadyHasDataStored() {
        List<String> currentItems = persistence.stringsForKey("configItems");
        return currentItems != null && currentItems.size() > 0;
    }

    public interface Listener {
        void configuring(String item);

        void error(KeyNotFound keyNotFound);

        class KeyNotFound {
            private final String noneExistentKey;

            public KeyNotFound(String noneExistentKey) {
                this.noneExistentKey = noneExistentKey;
            }

            @Override
            public String toString() {
                return "KeyNotFound \"" + noneExistentKey + "\"";
            }

            @Override
            public boolean equals(Object o) {
                if (!(o instanceof KeyNotFound)) return false;
                KeyNotFound keyNotFound = (KeyNotFound) o;
                boolean rtn = (keyNotFound.noneExistentKey) != null ? keyNotFound.noneExistentKey.equals(noneExistentKey) : false;
                return rtn;
            }
        }
    }

    public <T extends Listener> T addListener(T listener) {
        listenerAnnouncer.addListener(listener);
        return listener;
    }

    public void configureItem(String item) {
        if (persistence.hasKey(item)) {
            this.currentItemId = item;
            listenerAnnouncer.announce().configuring(item);
        } else {
            listenerAnnouncer.announce().error(new Listener.KeyNotFound(item));
        }

        //loads the current item into memory so the next method can have access to it :S
    }

    public List<String> configItemsList() {
        return persistence.stringsForKey("configItems");
    }

    private StringPersistence persistence;

    public ConfigService(StringPersistence persistence) {
        this.persistence = persistence;
        listenerAnnouncer = Announcer.to(Listener.class);
    }

}
