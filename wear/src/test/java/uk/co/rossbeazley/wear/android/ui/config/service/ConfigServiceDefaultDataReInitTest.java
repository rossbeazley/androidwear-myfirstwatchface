package uk.co.rossbeazley.wear.android.ui.config.service;


import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.android.ui.config.CapturingConfigServiceListener;
import uk.co.rossbeazley.wear.android.ui.config.TestConfigService;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

;

public class ConfigServiceDefaultDataReInitTest {

    private CapturingConfigServiceListener capturingConfigServiceListener;
    private ConfigService configService;
    private ConfigItem secondConfigItem;
    private ConfigItem firstConfigItem;
    private TestConfigService testConfigService;
    private String optionOne;
    private String optionTwo;

    @Test
    public void
    theOneWhereWePersistChoiceAndThenReinitialiseWithoutLoosingThatChoice() {
        reconfigureOptions();
        configService.initialiseDefaults(firstConfigItem,secondConfigItem);
        assertReconfiguredOptionsStillPersisted();
    }


    @Test
    public void
    reInitServiceWithPreviousPersistantStore() {
        reconfigureOptions();
        configService = new ConfigService(testConfigService.hashMapPersistence);
        configService.initialiseDefaults(firstConfigItem,secondConfigItem);
        assertReconfiguredOptionsStillPersisted();
    }

    private void assertReconfiguredOptionsStillPersisted() {
        assertThat("configService.configItemsList()", configService.configItemsList(),hasItems(firstConfigItem.itemId(),secondConfigItem.itemId()));
        assertThat("configService.configItemsList().size()", configService.configItemsList().size(),is(2));
        assertThat("configService.currentOptionForItem(firstConfigItem.itemId())", configService.currentOptionForItem(firstConfigItem.itemId()),is(optionOne));
        assertThat("configService.currentOptionForItem(secondConfigItem.itemId())", configService.currentOptionForItem(secondConfigItem.itemId()),is(optionTwo));
    }

    private void reconfigureOptions() {
        configService.configure(firstConfigItem.itemId());
        configService.choose(optionOne);
        configService.configure(secondConfigItem.itemId());
        configService.choose(optionTwo);
    }

    @Before
    public void buildTestWorld() {

        optionOne = "optionOne";
        optionTwo = "2ndoptionTwo";

        firstConfigItem = new ConfigItem("itemId");
        firstConfigItem.addOption(optionOne);
        firstConfigItem.addOption("optionTwo");
        firstConfigItem.defaultOption("optionTwo");

        secondConfigItem = new ConfigItem("itemId2");
        secondConfigItem.addOption("2ndoptionOne");
        secondConfigItem.addOption(optionTwo);
        secondConfigItem.defaultOption("2ndoptionOne");
        testConfigService = new TestConfigService();
        configService = testConfigService.build(firstConfigItem, secondConfigItem);

        capturingConfigServiceListener = configService.addListener(new CapturingConfigServiceListener());
    }


}
