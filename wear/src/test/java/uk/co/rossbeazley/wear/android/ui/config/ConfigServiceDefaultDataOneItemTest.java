package uk.co.rossbeazley.wear.android.ui.config;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ConfigServiceDefaultDataOneItemTest {

    private CapturingConfigServiceListener capturingConfigServiceListener;
    private ConfigService configService;
    private String expectedItemID;

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
        assertThat(configService.selectedConfigOptions(),hasItem("optionOne"));
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
        configService.initialiseDefaults(configItem);


    }

}
