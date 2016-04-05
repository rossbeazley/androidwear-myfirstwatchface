package uk.co.rossbeazley.wear.android.ui.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;
import uk.co.rossbeazley.wear.rotation.Orientation;

public class TestConfigService {

    public HashMapPersistence hashMapPersistence;
    public ConfigService configService;
    private LinkedHashMap<String, ConfigItem> configItems;

    private Random random;
    public Core core;
    public ConfigItem[] defaultOptions;

    public ConfigService build() {

        String oneChosen = "oneone";
        String twoChosen = "twotwo";
        String threeChosen = "threethree";

        final ConfigItem option1 = new ConfigItem("one");
        option1.addOptions("oneOne", "oneone", "oneThree", "oneFour");
        option1.defaultOption(oneChosen);

        final ConfigItem option2 = new ConfigItem("two");
        option2.addOptions("twoOne", "twoTwo", "twoThree", "twoFour");
        option2.defaultOption(twoChosen);

        final ConfigItem option3 = new ConfigItem("three");
        option3.addOptions("threeOne", "threethree", "threeThree", "threeFour");
        option3.defaultOption(threeChosen);

        return build(Core.defaultOptions());
    }

    public ConfigService build(final ConfigItem... defaultOptions) {

        //this.defaultOptions = defaultOptions;

        hashMapPersistence = new HashMapPersistence();

        configItems = new LinkedHashMap<>();
        for (ConfigItem option : defaultOptions) {
            configItems.put(option.itemId(), option);
        }
        core = new Core(Orientation.north(), hashMapPersistence, defaultOptions);
        configService = core.configService;

        random = new Random();

        this.defaultOptions = core.configService.defaultConfigItems;

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
        String item;
        do {
            item = anyItemID();
        }
        while(anyItem.equals(item));
        return item;
    }

    public String defaultOptionForItem(String itemID) {
        return configItems.get(itemID).defaultOption();
    }

    public String aDifferentOptionForItem(String itemId, String currentOption) {
        String option;
        do {
            option = anyOptionForItem(itemId);
        }while (currentOption.equals(option));
        return option;
    }
}
