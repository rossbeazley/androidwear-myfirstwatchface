package uk.co.rossbeazley.wear.android.ui.config.service;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import uk.co.rossbeazley.wear.android.ui.config.CapturingConfigServiceListener;
import uk.co.rossbeazley.wear.android.ui.config.HashMapPersistence;
import uk.co.rossbeazley.wear.android.ui.config.TestConfigService;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ConfigServiceDefaultDataTest {

    private CapturingConfigServiceListener capturingConfigServiceListener;
    private ConfigService configService;
    private String expectedItemID;
    private ConfigItem secondConfigItem;
    private String secondItemID;
    private String secondDefaultOption;
    private String defaultOption;
    private TestConfigService testConfigService;

    @Test
    public void
    theOneWhereWeGetTheListOfItems() {
        assertThat(configService.configItemsList(),is(equalTo(testConfigService.expectedListOfConfigItems())));
    }

    @Test
    public void
    theOneWhereWeConfigure() {

        String itemID =testConfigService.anyItem();
        configService.configure(itemID);
        assertThat(capturingConfigServiceListener.configuredItem,is(itemID));
    }

    @Test
    public void
    theOneWhereWeGetTheOptions() {

        String itemID =testConfigService.anyItem();
        configService.configure(itemID);
        assertThat(configService.selectedConfigOptions(),is(equalTo(testConfigService.expectedOptionsListForItem(itemID))));
    }


    @Test
    public void
    storesDefaultOptions() {

        String itemID =testConfigService.anyItem();
        configService.configure(itemID);
        assertThat(configService.optionForItem(itemID),is(testConfigService.expectedDefaultOptionForItem(itemID)));
    }

    @Before
    public void buildTestWorld() {
        capturingConfigServiceListener = new CapturingConfigServiceListener();

        testConfigService = new TestConfigService();
        configService = testConfigService.build();

        configService.addListener(capturingConfigServiceListener);

    }

}
