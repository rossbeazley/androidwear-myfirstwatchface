package uk.co.rossbeazley.wear.android.ui.config.service;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import uk.co.rossbeazley.wear.android.ui.config.CapturingConfigServiceListener;
import uk.co.rossbeazley.wear.android.ui.config.HashMapPersistence;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConfigServiceDefaultDataTwoItemsTest {

    private CapturingConfigServiceListener capturingConfigServiceListener;
    private ConfigService configService;
    private String expectedItemID;
    private ConfigItem secondConfigItem;
    private String secondItemID;
    private String secondDefaultOption;
    private String defaultOption;

    @Test
    public void
    theOneWhereWeInitialiseWithTwoConfigItem2AndGetTheListOfItems() {
        assertThat(configService.configItemsList(),hasItems(secondItemID,expectedItemID));
    }

    @Test
    public void
    theOneWhereWeConfigureWithTwoConfigItems() {
        configService.configure(expectedItemID);
        assertThat(capturingConfigServiceListener.configuredItem,is(expectedItemID));
    }

    @Test
    public void
    theOneWhereWeGetTheOptionsWithTwoConfigItems() {
        configService.configure(secondItemID);
        assertThat(configService.selectedConfigOptions(),hasItems("2ndoptionOne","2ndoptionTwo"));
    }


    @Test
    public void
    storesDefaultOptionsWithTwoConfigItems() {
        assertThat(configService.optionForItem(expectedItemID),is(defaultOption));
        assertThat(configService.optionForItem(secondItemID),is(secondDefaultOption));
    }

    @Before
    public void buildTestWorld() {
        capturingConfigServiceListener = new CapturingConfigServiceListener();

        HashMap<String, List<String>> emptyMap = new HashMap<>();
        HashMapPersistence hashMapPersistence = new HashMapPersistence(emptyMap);
        configService = new ConfigService(hashMapPersistence);
        configService.addListener(capturingConfigServiceListener);


        expectedItemID = "itemId";
        ConfigItem configItem = new ConfigItem(expectedItemID);
        configItem.addOption("optionOne");
        defaultOption = "optionTwo";
        configItem.addOption(defaultOption);
        configItem.defaultOption(defaultOption);

        secondItemID = "itemId2";
        secondConfigItem = new ConfigItem(secondItemID);
        secondDefaultOption = "2ndoptionOne";
        secondConfigItem.addOption(secondDefaultOption);
        secondConfigItem.addOption("2ndoptionTwo");
        secondConfigItem.defaultOption(secondDefaultOption);
        configService.initialiseDefaults(configItem, secondConfigItem);


    }

}
