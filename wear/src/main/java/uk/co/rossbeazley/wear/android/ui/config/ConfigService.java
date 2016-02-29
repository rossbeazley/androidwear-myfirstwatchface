package uk.co.rossbeazley.wear.android.ui.config;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import uk.co.rossbeazley.wear.Announcer;

/**
 * Created by beazlr02 on 19/02/16.
 */
class ConfigService {

    private final Announcer<Listener> listenerAnnouncer;
    private String currentItemId;

    public List<String> selectedConfigOptions() {
        return persistence.stringsForKey(currentItemId);
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
                if( !(o instanceof KeyNotFound) ) return false;
                KeyNotFound keyNotFound = (KeyNotFound) o;
                boolean rtn = (keyNotFound.noneExistentKey)!=null ? keyNotFound.noneExistentKey.equals(noneExistentKey) : false;
                return rtn;
            }
        }
    }

    void addListener(Listener listener) {
        listenerAnnouncer.addListener(listener);
    }

    public void configure(String item) {
        if(persistence.hasKey(item)) {
            this.currentItemId = item;
            listenerAnnouncer.announce().configuring(item);
        } else {
            listenerAnnouncer.announce().error(new Listener.KeyNotFound(item));
        }

        //loads the current item into memory so the next method can have access to it
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
