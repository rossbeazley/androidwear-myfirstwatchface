package uk.co.rossbeazley.wear.config;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.wear.Announcer;

import static java.util.Arrays.asList;

public class ConfigService {

    public static ConfigService setupConfig(StringPersistence persistence, ConfigItem... options) {
        ConfigService configService =  new ConfigService(persistence);
        configService.initialiseDefaults(options);
        return configService;
    }

    private String currentItemId;
    private StringPersistence persistence;
    public ConfigItem[] defaultConfigItems;
    private interface ConfigItemIterator {
        void item(ConfigItem configItem);
    }

    public List<String> selectedConfigOptions() {
        return configItem(currentItemId).options();
    }

    private ConfigItem configItem(String currentItemId) {
        ConfigItem result = null;
        for (ConfigItem i : defaultConfigItems) {
            if(i.itemId().equals(currentItemId)) {
                result = i;
            }
        }
        return result;
    }

    public void chooseOption(String expectedOption) {
        persistItemChoice(currentItemId, expectedOption);
        listenerAnnouncer.announce().chosenOption(expectedOption);
    }

    public String currentOptionForItem(String id) {
        return persistence.stringsForKey(id).get(0);
    }

    private void forEachConfigItem(ConfigItemIterator it) {
        for (ConfigItem configItem : defaultConfigItems) {
            it.item(configItem);
        }
    }

    public void initialiseDefaults(ConfigItem... configItems) {
        defaultConfigItems = configItems;
        forEachConfigItem(new ConfigItemIterator() {
            @Override
            public void item(ConfigItem configItem) {
                if (!persistence.hasKey(configItem.itemId())) {
                    persistItemChoice(configItem.itemId(), configItem.defaultOption());
                }
            }
        });
    }

    public void resetDefaults() {
        forEachConfigItem(new ConfigItemIterator() {
            @Override
            public void item(ConfigItem configItem) {
                persistItemChoice(configItem.itemId(), configItem.defaultOption());
            }
        });
    }

    public void persistItemChoice(String itemId, String option) {
        if (itemExists(itemId)) {
            persistence.storeStringsForKey(itemId, asList(option));
        }
    }

    private boolean itemExists(String itemId) {
        return configItem(itemId)!=null;
    }


    public void configureItem(String item) {
        if (itemExists(item)) {
            this.currentItemId = item;
            listenerAnnouncer.announce().configuring(item);
        } else {
            listenerAnnouncer.announce().error(new ConfigServiceListener.KeyNotFound(item));
        }

        //loads the current item into memory so the next method can have access to it :S
    }

    public List<String> configItemsList() {
        final List<String> configItems = new ArrayList<>();
        forEachConfigItem(new ConfigItemIterator() {
            @Override
            public void item(ConfigItem configItem) {
                configItems.add(configItem.itemId());
            }
        });
        return configItems;
    }


    public ConfigService(StringPersistence persistence) {
        this.persistence = persistence;
        listenerAnnouncer = Announcer.to(ConfigServiceListener.class);
    }




    private final Announcer<ConfigServiceListener> listenerAnnouncer;

    public void removeListener(ConfigServiceListener listener) {
        listenerAnnouncer.removeListener(listener);
    }

    public <Listener extends ConfigServiceListener> Listener addListener(Listener listener) {
        listenerAnnouncer.addListener(listener);
        return listener;
    }

}
