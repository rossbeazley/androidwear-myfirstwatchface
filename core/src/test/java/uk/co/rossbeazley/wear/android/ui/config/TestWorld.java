package uk.co.rossbeazley.wear.android.ui.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

public class TestWorld {

    public HashMapPersistence hashMapPersistence;
    public ConfigService configService;
    private LinkedHashMap<String, ConfigItem> configItems;

    private Random random;
    public Core core;
    public Core.DefaultOptions defaultOptions;

    public ConfigService build() {

        hashMapPersistence = new HashMapPersistence();
        core = new Core(hashMapPersistence);
        configService = core.configService;

        random = new Random();

        this.defaultOptions = core.defaultOptions();

        configItems = new LinkedHashMap<>();
        for (ConfigItem option : defaultOptions.array()) {
            configItems.put(option.itemId(), option);
        }
        return configService;
    }

    public String anyItemID() {

        Collection<ConfigItem> values = configItems.values();
        return new ArrayList<>(values).get(random.nextInt(values.size())).itemId();
    }

    public List<String> listOfConfigItems() {

        List<String> expectedList = new ArrayList<>();
        for(ConfigItem item : configItems.values()) {
            expectedList.add(item.itemId());
        }
        return expectedList;
    }

    public List<String> optionsListForItem(String forItem) {
        ConfigItem configItem = configItems.get(forItem);
        return configItem.options();
    }

    public String anyOptionForItem(String anyItem) {
        List<String> strings = optionsListForItem(anyItem);
        return strings.get(random.nextInt(strings.size()));
    }

    public String aDifferentItem(String anyItem) {
        assertMoreThanOneItem();

        String item;
        do {
            item = anyItemID();
        }
        while(anyItem.equals(item));
        return item;
    }

    private void assertMoreThanOneItem() {
        Set<String> itemIDS = configItems.keySet();
        assert itemIDS.size()>1 : "MALFORMED TESTDATA, ASSUMES MORE THAN ONE ITEM " + itemIDS;
    }

    public String defaultOptionForItem(String itemID) {
        return configItems.get(itemID).defaultOption();
    }

    public String aDifferentOptionForItem(String itemId, String currentOption) {
        assertMoreThanOneOptionForItem(itemId);

        String option;
        do {
            option = anyOptionForItem(itemId);
        }while (currentOption.equals(option));
        return option;
    }

    private void assertMoreThanOneOptionForItem(String itemId) {
        List<String> options = configItems.get(itemId).options();
        assert options.size()>1 : "MALFORMED TESTDATA, ASSUMES ITEM " + itemId + " HAS MORE THAN ON OPTION\n" + options;

    }
}
