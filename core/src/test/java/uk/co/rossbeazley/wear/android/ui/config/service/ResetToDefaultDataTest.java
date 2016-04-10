package uk.co.rossbeazley.wear.android.ui.config.service;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import uk.co.rossbeazley.wear.android.ui.config.TestWorld;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ResetToDefaultDataTest {

    private ConfigService configService;
    private TestWorld testWorld;

    @Test
    public void
    contractTestNotTheDefault() {

        String anyItemId = testWorld.anyItemID();
        assertThat(configService.currentOptionForItem(anyItemId),is(not(equalTo(testWorld.defaultOptionForItem(anyItemId)))));
    }

    @Test
    public void
    resetServiceBackToDefaults() {

        configService.resetDefaults();

        String anyItemId = testWorld.anyItemID();
        assertThat(configService.currentOptionForItem(anyItemId),is(equalTo(testWorld.defaultOptionForItem(anyItemId))));
    }

    @Before
    public void buildTestWorld() {

        testWorld = new TestWorld();
        configService = testWorld.build();

        // reconfigure
        List<String> strings = configService.configItemsList();
        for (String itemId : strings) {
            configService.configureItem(itemId);
            String currentOption = configService.currentOptionForItem(itemId);
            String newOption= testWorld.aDifferentOptionForItem(itemId, currentOption);
            configService.chooseOption(newOption);
        }

    }

}
