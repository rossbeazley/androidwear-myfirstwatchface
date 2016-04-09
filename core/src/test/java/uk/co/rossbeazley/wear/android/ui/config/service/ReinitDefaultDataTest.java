package uk.co.rossbeazley.wear.android.ui.config.service;


import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.android.ui.config.TestConfigService;
import uk.co.rossbeazley.wear.rotation.Orientation;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class ReinitDefaultDataTest {

    private CapturingConfigServiceListener capturingConfigServiceListener;
    private ConfigService configService;
    private TestConfigService testConfigService;
    private ConfigItem[] defaultOptions;

    private Map<String,String> reconfiguredOptionsForItemIDs = new HashMap<>();

    @Test
    public void
    theOneWhereWePersistChoiceAndThenReinitialiseWithoutLoosingThatChoice() {
        reconfigureOptions();
        configService.initialiseDefaults(defaultOptions);
        assertReconfiguredOptionsStillPersisted();
    }


    @Test
    public void
    reInitServiceWithPreviousPersistentStore() {
        reconfigureOptions();
        configService = new Core(Orientation.north(), testConfigService.hashMapPersistence, defaultOptions).configService;
        assertReconfiguredOptionsStillPersisted();
    }

    private void assertReconfiguredOptionsStillPersisted() {
        Set<String> itemIDs = reconfiguredOptionsForItemIDs.keySet();
        String[] items={};
        items = itemIDs.toArray(items);
        assertThat("configService.configItemsList()", configService.configItemsList(),hasItems(items));
        assertThat("configService.configItemsList().size()", configService.configItemsList().size(),is(itemIDs.size()));

        for (Map.Entry<String, String> itemOption : reconfiguredOptionsForItemIDs.entrySet()) {
            String itemID = itemOption.getKey();
            String currentOptionForItem = configService.currentOptionForItem(itemID);
            assertThat("Whilst checking configured item options, expected item:"+itemID+" was reconfigured",currentOptionForItem,is(itemOption.getValue()));
        }

    }

    private void reconfigureOptions() {
        for(ConfigItem item : defaultOptions) {
            String itemID = item.itemId();
            configService.configureItem(itemID);
            String currentOptionForItem = configService.currentOptionForItem(itemID);
            String differentOptionForItem = testConfigService.aDifferentOptionForItem(itemID, currentOptionForItem);
            configService.chooseOption(differentOptionForItem);

            reconfiguredOptionsForItemIDs.put(itemID,differentOptionForItem);
        }
    }

    @Before
    public void buildTestWorld() {

        testConfigService = new TestConfigService();
        configService = testConfigService.build();
        capturingConfigServiceListener = configService.addListener(new CapturingConfigServiceListener());

        defaultOptions = testConfigService.defaultOptions;
    }


}