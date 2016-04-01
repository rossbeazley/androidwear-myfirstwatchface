package uk.co.rossbeazley.wear.android.ui.config.service;


import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.android.ui.config.TestConfigService;
import uk.co.rossbeazley.wear.rotation.Orientation;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

;

public class ReinitDefaultDataTest {

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
    reInitServiceWithPreviousPersistentStore() {
        reconfigureOptions();
        configService = new Core(Orientation.north(), testConfigService.hashMapPersistence, firstConfigItem, secondConfigItem).configService;
        assertReconfiguredOptionsStillPersisted();
    }

    private void assertReconfiguredOptionsStillPersisted() {
        assertThat("configService.configItemsList()", configService.configItemsList(),hasItems(firstConfigItem.itemId(),secondConfigItem.itemId()));
        assertThat("configService.configItemsList().size()", configService.configItemsList().size(),is(2));
        assertThat("configService.currentOptionForItem(firstConfigItem.itemId())", configService.currentOptionForItem(firstConfigItem.itemId()),is(optionOne));
        assertThat("configService.currentOptionForItem(secondConfigItem.itemId())", configService.currentOptionForItem(secondConfigItem.itemId()),is(optionTwo));
    }

    private void reconfigureOptions() {
        configService.configureItem(firstConfigItem.itemId());
        configService.chooseOption(optionOne);
        configService.configureItem(secondConfigItem.itemId());
        configService.chooseOption(optionTwo);
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
