package uk.co.rossbeazley.wear.android.ui.config.service;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import uk.co.rossbeazley.wear.android.ui.config.TestConfigService;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ResetToDefaultDataTest {

    private ConfigService configService;
    private TestConfigService testConfigService;

    @Test
    public void
    contractTestNotTheDefault() {

        String anyItemId = testConfigService.anyItemID();
        assertThat(configService.currentOptionForItem(anyItemId),is(not(equalTo(testConfigService.defaultOptionForItem(anyItemId)))));
    }

    @Test
    public void
    resetServiceBackToDefaults() {

        configService.resetDefaults();

        String anyItemId = testConfigService.anyItemID();
        assertThat(configService.currentOptionForItem(anyItemId),is(equalTo(testConfigService.defaultOptionForItem(anyItemId))));
    }

    @Before
    public void buildTestWorld() {

        testConfigService = new TestConfigService();
        configService = testConfigService.build();

        // reconfigure
        List<String> strings = configService.configItemsList();
        for (String itemId : strings) {
            configService.configureItem(itemId);
            String currentOption = configService.currentOptionForItem(itemId);
            String newOption= testConfigService.aDifferentOptionForItem(itemId, currentOption);
            configService.chooseOption(newOption);
        }

    }

}
