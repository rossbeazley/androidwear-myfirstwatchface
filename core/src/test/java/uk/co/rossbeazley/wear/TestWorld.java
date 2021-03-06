package uk.co.rossbeazley.wear;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import uk.co.rossbeazley.wear.config.*;
import uk.co.rossbeazley.wear.colour.BackgroundColourConfigItem;
import uk.co.rossbeazley.wear.colour.Colours;
import uk.co.rossbeazley.wear.colour.HoursColourConfigItem;
import uk.co.rossbeazley.wear.hours.HoursModeConfigItem;
import uk.co.rossbeazley.wear.rotation.RotationConfigItem;

public class TestWorld {

    public uk.co.rossbeazley.wear.config.HashMapPersistence hashMapPersistence;
    public ConfigService configService;
    private LinkedHashMap<String, ConfigItem> configItems;

    private Random random;
    public Core core;
    public Core.DefaultOptions defaultOptions;
    private HoursColourConfigItem hoursColourConfigItem;
    private BackgroundColourConfigItem backgroundColourConfigItem;
    private RotationConfigItem rotationConfigItem;
    public HoursModeConfigItem hoursModeConfigItem;

    public TestWorld() {
        hoursModeConfigItem = Core.defaultOptions().defaultHoursModeConfigItem;
        hoursColourConfigItem = new HoursColourConfigItem(Colours.Colour.RED);
        backgroundColourConfigItem = Core.defaultOptions().defaultBackgroundColourConfigItem;
        rotationConfigItem = Core.defaultOptions().defaultRotationConfigItem;
        hashMapPersistence = new uk.co.rossbeazley.wear.config.HashMapPersistence();
    }

    public ConfigService build() {

        core = new Core(hashMapPersistence, backgroundColourConfigItem, rotationConfigItem, hoursColourConfigItem, hoursModeConfigItem);
        configService = core.configService;

        random = new Random();

        this.defaultOptions = core.defaultOptions();

        configItems = new LinkedHashMap<>();
        for (ConfigItem option : new ConfigItem[]{backgroundColourConfigItem, rotationConfigItem, hoursColourConfigItem, hoursModeConfigItem}) {
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
        for (ConfigItem item : configItems.values()) {
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
        while (anyItem.equals(item));
        return item;
    }

    private void assertMoreThanOneItem() {
        Set<String> itemIDS = configItems.keySet();
        assert itemIDS.size() > 1 : "MALFORMED TESTDATA, ASSUMES MORE THAN ONE ITEM " + itemIDS;
    }

    public String defaultOptionForItem(String itemID) {
        return configItems.get(itemID).defaultOption();
    }

    public String aDifferentOptionForItem(String itemId, String currentOption) {
        assertMoreThanOneOptionForItem(itemId);

        String option;
        do {
            option = anyOptionForItem(itemId);
        } while (currentOption.equals(option));
        return option;
    }

    private void assertMoreThanOneOptionForItem(String itemId) {
        List<String> options = configItems.get(itemId).options();
        assert options.size() > 1 : "MALFORMED TESTDATA, ASSUMES ITEM " + itemId + " HAS MORE THAN ON OPTION\n" + options;

    }

    public String aDifferentOptionForItem(String aDifferentItem) {

        return aDifferentOptionForItem(aDifferentItem, configService.currentOptionForItem(aDifferentItem));
    }

    public TestWorld with(HoursColourConfigItem hoursColourConfigItem) {
        this.hoursColourConfigItem = hoursColourConfigItem;
        return this;
    }

    public TestWorld with(uk.co.rossbeazley.wear.config.HashMapPersistence persistence) {
        this.hashMapPersistence = persistence;

        return this;
    }

    public TestWorld with(HoursModeConfigItem hoursModeConfigItem) {

        this.hoursModeConfigItem = hoursModeConfigItem;
        return this;
    }
}
