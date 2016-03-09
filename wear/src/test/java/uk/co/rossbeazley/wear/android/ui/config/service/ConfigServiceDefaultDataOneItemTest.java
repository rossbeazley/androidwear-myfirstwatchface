package uk.co.rossbeazley.wear.android.ui.config.service;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import uk.co.rossbeazley.wear.android.ui.config.CapturingConfigServiceListener;
import uk.co.rossbeazley.wear.android.ui.config.HashMapPersistence;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ConfigServiceDefaultDataOneItemTest {

    private CapturingConfigServiceListener capturingConfigServiceListener;
    private ConfigService configService;
    private String expectedItemID;
    private String expectedDefaultOption;

    @Test
    public void
    theOneWhereWeInitialiseWithOneConfigItemAndGetTheListOfItems() {
        assertThat(configService.configItemsList(),hasItem(expectedItemID));
    }

    @Test
    public void
    theOneWhereWeConfigureWithOneConfigItem() {
        configService.configure(expectedItemID);
        assertThat(capturingConfigServiceListener.configuredItem,is(expectedItemID));
    }

    @Test
    public void
    theOneWhereWeGetTheOptionsWithOnConfigItem() {
        configService.configure(expectedItemID);
        assertThat(configService.selectedConfigOptions(),hasItems("optionOne","optionTwo"));
    }

    @Test
    public void
    theOneWhereWeGetTheDefaultOptionForOneItem() {
        String option = configService.optionForItem(expectedItemID);
        assertThat(option,is(expectedDefaultOption));
    }


    @Before
    public void buildTestWorld() {
        capturingConfigServiceListener = new CapturingConfigServiceListener();

        HashMap<String, List<String>> emptyMap = new HashMap<>();
        HashMapPersistence hashMapPersistence = new HashMapPersistence(emptyMap);
        configService = new ConfigService(hashMapPersistence);
        configService.addListener(capturingConfigServiceListener);


        expectedItemID = "itemId";
        expectedDefaultOption = "optionTwo";
        ConfigItem configItem = new ConfigItem(expectedItemID);
        configItem.addOption("optionOne");
        configItem.addOption(expectedDefaultOption);
        configItem.defaultOption(expectedDefaultOption);
        configService.initialiseDefaults(configItem);


    }

}
