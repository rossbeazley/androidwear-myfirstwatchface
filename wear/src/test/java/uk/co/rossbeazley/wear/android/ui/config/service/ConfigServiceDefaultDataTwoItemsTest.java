package uk.co.rossbeazley.wear.android.ui.config.service;


import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.android.ui.config.TestConfigService;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ConfigServiceDefaultDataTwoItemsTest {

    private CapturingConfigServiceListener capturingConfigServiceListener;
    private ConfigService configService;
    private ConfigItem secondConfigItem;
    private ConfigItem firstConfigItem;
    private TestConfigService testConfigService;

    @Test
    public void
    theOneWhereWeInitialiseWithTwoConfigItem2AndGetTheListOfItems() {
        assertThat(configService.configItemsList(), is(equalTo(testConfigService.expectedListOfConfigItems())));
    }

    @Test
    public void
    theOneWhereWeConfigureWithTwoConfigItems() {
        String itemId = firstConfigItem.itemId();
        configService.configureItem(itemId);
        assertThat(capturingConfigServiceListener.configuredItem, is(itemId));
    }

    @Test
    public void
    theOneWhereWeGetTheOptionsWithTwoConfigItems() {
        configService.configureItem(secondConfigItem.itemId());
        assertThat(configService.selectedConfigOptions(),is(equalTo(secondConfigItem.options())));
    }


    @Test
    public void
    storesDefaultOptionsWithTwoConfigItems() {
        assertThat(configService.currentOptionForItem(firstConfigItem.itemId()), is(equalTo(firstConfigItem.defaultOption())));
        assertThat(configService.currentOptionForItem(secondConfigItem.itemId()), is(equalTo(secondConfigItem.defaultOption())));
    }

    @Before
    public void buildTestWorld() {

        firstConfigItem = new ConfigItem("itemId");
        firstConfigItem.addOption("optionOne");
        firstConfigItem.addOption("optionTwo");
        firstConfigItem.defaultOption("optionTwo");

        secondConfigItem = new ConfigItem("itemId2");
        secondConfigItem.addOption("2ndoptionOne");
        secondConfigItem.addOption("2ndoptionTwo");
        secondConfigItem.defaultOption("2ndoptionOne");
        testConfigService = new TestConfigService();
        configService = testConfigService.build(firstConfigItem, secondConfigItem);

        capturingConfigServiceListener = configService.addListener(new CapturingConfigServiceListener());
    }

}
