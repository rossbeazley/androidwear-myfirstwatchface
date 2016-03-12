package uk.co.rossbeazley.wear.android.ui.config.service;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uk.co.rossbeazley.wear.android.ui.config.CapturingConfigServiceListener;
import uk.co.rossbeazley.wear.android.ui.config.HashMapPersistence;
import uk.co.rossbeazley.wear.android.ui.config.TestConfigService;
;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

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

        configService.configure(firstConfigItem.itemId());
        configService.choose(optionOne);
        configService.configure(secondConfigItem.itemId());
        configService.choose(optionTwo);

        configService.initialiseDefaults(firstConfigItem,secondConfigItem);

        assertThat("configService.configItemsList()", configService.configItemsList(),hasItems(firstConfigItem.itemId(),secondConfigItem.itemId()));
        assertThat("configService.configItemsList().size()", configService.configItemsList().size(),is(2));
        assertThat("configService.currentOptionForItem(firstConfigItem.itemId())", configService.currentOptionForItem(firstConfigItem.itemId()),is(optionOne));
        assertThat("configService.currentOptionForItem(secondConfigItem.itemId())", configService.currentOptionForItem(secondConfigItem.itemId()),is(optionTwo));
    }

    @Test
    public void
    reInitServiceWithPreviousPersistantStore() {

        configService.configure(firstConfigItem.itemId());
        configService.choose(optionOne);
        configService.configure(secondConfigItem.itemId());
        configService.choose(optionTwo);

        configService = new ConfigService(testConfigService.hashMapPersistence);
        configService.initialiseDefaults(firstConfigItem,secondConfigItem);

        assertThat("configService.configItemsList()", configService.configItemsList(),hasItems(firstConfigItem.itemId(),secondConfigItem.itemId()));
        assertThat("configService.configItemsList().size()", configService.configItemsList().size(),is(2));
        assertThat("configService.currentOptionForItem(firstConfigItem.itemId())", configService.currentOptionForItem(firstConfigItem.itemId()),is(optionOne));
        assertThat("configService.currentOptionForItem(secondConfigItem.itemId())", configService.currentOptionForItem(secondConfigItem.itemId()),is(optionTwo));

    }

    @Before
    public void buildTestWorld() {

        optionOne = "optionOne";
        optionTwo = "2ndoptionTwo";

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
