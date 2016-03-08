package uk.co.rossbeazley.wear.android.ui.config;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ConfigServiceDefaultDataTest {

    @Test
    public void
    theOneWhereWeInitialiseWithOneConfigItemAndGetTheListOfItems() {
        HashMap<String, List<String>> emptyMap = new HashMap<>();
        HashMapPersistence hashMapPersistence = new HashMapPersistence(emptyMap);
        ConfigService configService = new ConfigService(hashMapPersistence);

        String expectedItem = "itemId";

        configService.initialiseDefaults(new ConfigItem(expectedItem));

        assertThat(configService.configItemsList(),hasItem(expectedItem));
    }

    @Test
    public void
    theOneWhereWeConfigureWithOneConfigItem() {

        CapturingConfigServiceListener capturingConfigServiceListener = new CapturingConfigServiceListener();

        HashMap<String, List<String>> emptyMap = new HashMap<>();
        HashMapPersistence hashMapPersistence = new HashMapPersistence(emptyMap);
        ConfigService configService = new ConfigService(hashMapPersistence);
        configService.addListener(capturingConfigServiceListener);

        String expectedItem = "itemId";

        configService.initialiseDefaults(new ConfigItem(expectedItem));

        configService.configure(expectedItem);

        assertThat(capturingConfigServiceListener.configuredItem,is(expectedItem));
    }

}
