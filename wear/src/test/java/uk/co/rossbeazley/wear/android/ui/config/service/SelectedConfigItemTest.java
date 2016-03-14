package uk.co.rossbeazley.wear.android.ui.config.service;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import uk.co.rossbeazley.wear.android.ui.config.TestConfigService;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class SelectedConfigItemTest {

    private TestConfigService testConfigService;
    private ConfigService configService;
    private String anyItem;

    @Before
    public void buildTestWorld() {

        testConfigService = new TestConfigService();
        configService = testConfigService.build();

        anyItem = testConfigService.anyItemID();

        configService.configure(anyItem);
    }
    
    @Test
    public void
    theOneWeRetrieveTheSelectedConfigOption() {
        List<String> selectedConfigOptions = configService.selectedConfigOptions();
        String[] allTheOnes = testConfigService.expectedOptionsListForItem(anyItem).toArray(new String[]{});
        assertThat(selectedConfigOptions,hasItems(allTheOnes));
    }


    @Test
    public void
    theOneWhereWeChooseAConfigItemOption() {

        String expectedOption = testConfigService.anyExpectedOptionListForItem(anyItem);
        configService.choose(expectedOption);

        String optionForItem = configService.currentOptionForItem(anyItem);

        assertThat(optionForItem,is(expectedOption));
    }

    @Test
    public void
    theOneWhereWeChooseADifferentConfigItemOption() {

        String expectedOption = testConfigService.anyExpectedOptionListForItem(anyItem);
        configService.choose(expectedOption);

        String optionForItem = configService.currentOptionForItem(testConfigService.aDifferentItem(anyItem));

        assertThat(optionForItem,is(not(expectedOption)));
    }

}
