package uk.co.rossbeazley.wear.android.ui.config;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ConfigServiceDefaultDataTest {

    private CapturingConfigServiceListener capturingConfigServiceListener;
    private ConfigService configService;

    @Test
    public void
    theOneWhereWeInitialiseWithOneConfigItemAndGetTheListOfItems() {

        String expectedItem = "itemId";
        ConfigItem configItem = new ConfigItem(expectedItem);
        configService.initialiseDefaults(configItem);
        assertThat(configService.configItemsList(),hasItem(expectedItem));
    }

    @Test
    public void
    theOneWhereWeConfigureWithOneConfigItem() {

        String expectedItem = "itemId";
        configService.initialiseDefaults(new ConfigItem(expectedItem));
        configService.configure(expectedItem);
        assertThat(capturingConfigServiceListener.configuredItem,is(expectedItem));
    }

    @Test
    public void
    theOneWhereWeGetTheOptionsWithOnConfigItem() {

        String expectedItem = "itemId";
        ConfigItem configItem = new ConfigItem(expectedItem);
        configItem.addOption("optionOne");
        configService.initialiseDefaults(configItem);

        configService.configure("itemId");
        assertThat(configService.selectedConfigOptions(),hasItem("optionOne"));
    }

    @Before
    public void buildTestWorld() {
        capturingConfigServiceListener = new CapturingConfigServiceListener();

        HashMap<String, List<String>> emptyMap = new HashMap<>();
        HashMapPersistence hashMapPersistence = new HashMapPersistence(emptyMap);
        configService = new ConfigService(hashMapPersistence);
        configService.addListener(capturingConfigServiceListener);
    }

}
