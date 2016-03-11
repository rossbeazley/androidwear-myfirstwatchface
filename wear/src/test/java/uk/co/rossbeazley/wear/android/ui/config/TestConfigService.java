package uk.co.rossbeazley.wear.android.ui.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

public class TestConfigService {

    public HashMapPersistence hashMapPersistence;
    public ConfigService configService;
    private LinkedHashMap<String, ConfigItem> configItems;

    private Random random;

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

        configItems = new LinkedHashMap<String, ConfigItem>(){{
            put(option1.itemId(),option1);
            put(option2.itemId(),option2);
            put(option3.itemId(),option3);
        }};


        hashMapPersistence = new HashMapPersistence();
        configService = new ConfigService(hashMapPersistence);

        configService.initialiseDefaults(option1, option2, option3);

        random = new Random();

        return configService;
    }

    public String anyItemID() {

        Collection<ConfigItem> values = configItems.values();
        return new ArrayList<>(values).get(random.nextInt(values.size())).itemId();
    }

    public List<String> expectedListOfConfigItems() {

        List<String> expectedList = new ArrayList<>();
        for(ConfigItem item : configItems.values()) {
            expectedList.add(item.itemId());
        }
        return expectedList;
    }

    public List<String> expectedOptionsListForItem(String forItem) {
        ConfigItem configItem = configItems.get(forItem);
        return configItem.options();
    }

    public String anyExpectedOptionListForItem(String anyItem) {
        List<String> strings = expectedOptionsListForItem(anyItem);
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

    public String expectedDefaultOptionForItem(String itemID) {
        return configItems.get(itemID).defaultOption();
    }
}
