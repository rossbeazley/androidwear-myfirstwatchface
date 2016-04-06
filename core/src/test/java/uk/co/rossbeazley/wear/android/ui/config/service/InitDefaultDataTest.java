package uk.co.rossbeazley.wear.android.ui.config.service;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.android.ui.config.TestConfigService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InitDefaultDataTest {

    private CapturingConfigServiceListener capturingConfigServiceListener;
    private ConfigService configService;
    private TestConfigService testConfigService;

    @Test
    public void
    theOneWhereWeGetTheListOfItems() {
        assertThat(configService.configItemsList(),is(equalTo(testConfigService.listOfConfigItems())));
    }

    @Test
    public void
    theOneWhereWeConfigure() {

        String itemID =testConfigService.anyItemID();
        configService.configureItem(itemID);
        assertThat(capturingConfigServiceListener.configuredItem,is(itemID));
    }

    @Test
    public void
    theOneWhereWeGetTheOptions() {

        String itemID =testConfigService.anyItemID();
        configService.configureItem(itemID);
        assertThat(configService.selectedConfigOptions(),is(equalTo(testConfigService.optionsListForItem(itemID))));
    }


    @Test
    public void
    storesDefaultOptions() {

        String itemID = testConfigService.anyItemID();
        configService.configureItem(itemID);
        assertThat(configService.currentOptionForItem(itemID),is(testConfigService.defaultOptionForItem(itemID)));
    }

    @Before
    public void buildTestWorld() {

        testConfigService = new TestConfigService();
        configService = testConfigService.build();
        capturingConfigServiceListener = configService.addListener(new CapturingConfigServiceListener());
    }

}
