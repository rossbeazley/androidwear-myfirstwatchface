package uk.co.rossbeazley.wear.android.ui.config.service;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.android.ui.config.TestConfigService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConfigServiceDefaultDataOneItemTest {

    private CapturingConfigServiceListener capturingConfigServiceListener;
    private ConfigService configService;
    private String expectedDefaultOption;
    private TestConfigService testConfigService;
    private ConfigItem configItem;

    @Test
    public void
    theOneWhereWeInitialiseWithOneConfigItemAndGetTheListOfItems() {
        assertThat(configService.configItemsList(),hasItem(configItem.itemId()));
    }

    @Test
    public void
    theOneWhereWeConfigureWithOneConfigItem() {
        String itemId = configItem.itemId();
        configService.configureItem(itemId);
        assertThat(capturingConfigServiceListener.configuredItem,is(itemId));
    }

    @Test
    public void
    theOneWhereWeGetTheOptionsWithOnConfigItem() {
        configService.configureItem(configItem.itemId());
        assertThat(configService.selectedConfigOptions(),is(equalTo(configItem.options())));
    }

    @Test
    public void
    theOneWhereWeGetTheDefaultOptionForOneItem() {
        String option = configService.currentOptionForItem(configItem.itemId());
        assertThat(option,is(expectedDefaultOption));
    }


    @Before
    public void buildTestWorld() {
        configItem = new ConfigItem("optionTwo");
        configItem.addOption("optionOne");
        configItem.addOption(expectedDefaultOption);
        configItem.defaultOption(expectedDefaultOption);

        testConfigService = new TestConfigService();
        configService = testConfigService.build(configItem);

        capturingConfigServiceListener = new CapturingConfigServiceListener();
        configService.addListener(capturingConfigServiceListener);

    }

}
