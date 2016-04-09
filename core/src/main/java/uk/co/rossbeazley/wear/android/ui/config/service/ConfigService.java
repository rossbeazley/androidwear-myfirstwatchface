package uk.co.rossbeazley.wear.android.ui.config.service;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.wear.Announcer;

import static java.util.Arrays.asList;

public class ConfigService {

    public static ConfigService setupConfig(StringPersistence persistence, ConfigItem[] options) {
        ConfigService configService =  new ConfigService(persistence);
        configService.initialiseDefaults(options);
        return configService;
    }


    private final Announcer<ConfigServiceListener> listenerAnnouncer;
    private String currentItemId;

    public ConfigItem[] defaultConfigItems;

    public List<String> selectedConfigOptions() {
        return persistence.stringsForKey(currentItemId);
    }

    public void chooseOption(String expectedOption) {

        persistence.storeStringsForKey(currentItemId + "Choice", asList(expectedOption));
        listenerAnnouncer.announce().chosenOption(expectedOption);
    }

    public String currentOptionForItem(String id) {
        return persistence.stringsForKey(id + "Choice").get(0);
    }

    public void initialiseDefaults(ConfigItem... configItems) {
        defaultConfigItems = configItems;

        if (alreadyHasDataStored()) return;

        storeDefaults(configItems);
    }

    private void storeDefaults(ConfigItem[] configItems) {
        List<String> IDs = new ArrayList<>(configItems.length);
        for (ConfigItem configItem : configItems) {
            String id = configItem.itemId();
            IDs.add(id);
            persistence.storeStringsForKey(id, configItem.options());
            String defaultOption = configItem.defaultOption();
            persistence.storeStringsForKey(id + "Choice", asList(defaultOption));
        }
        persistence.storeStringsForKey("configItems", IDs);
    }

    private boolean alreadyHasDataStored() {
        List<String> currentItems = persistence.stringsForKey("configItems");
        return currentItems != null && currentItems.size() > 0;
    }

    public void resetDefaults() {
        storeDefaults(defaultConfigItems);
    }

    public void persistItemChoice(String itemId, String option) {
        if (persistence.hasKey(itemId)) {
            persistence.storeStringsForKey(itemId + "Choice", asList(option));
        }
    }

    public <T extends ConfigServiceListener> T addListener(T listener) {
        listenerAnnouncer.addListener(listener);
        return listener;
    }

    public void configureItem(String item) {
        if (persistence.hasKey(item)) {
            this.currentItemId = item;
            listenerAnnouncer.announce().configuring(item);
        } else {
            listenerAnnouncer.announce().error(new ConfigServiceListener.KeyNotFound(item));
        }

        //loads the current item into memory so the next method can have access to it :S
    }

    public List<String> configItemsList() {
        return persistence.stringsForKey("configItems");
    }

    private StringPersistence persistence;

    public ConfigService(StringPersistence persistence) {
        this.persistence = persistence;
        listenerAnnouncer = Announcer.to(ConfigServiceListener.class);
    }

}
