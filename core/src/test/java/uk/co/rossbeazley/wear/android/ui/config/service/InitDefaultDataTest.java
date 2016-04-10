package uk.co.rossbeazley.wear.android.ui.config.service;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.android.ui.config.TestWorld;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InitDefaultDataTest {

    private CapturingConfigServiceListener capturingConfigServiceListener;
    private ConfigService configService;
    private TestWorld testWorld;

    @Test
    public void
    theOneWhereWeGetTheListOfItems() {
        assertThat(configService.configItemsList(),is(equalTo(testWorld.listOfConfigItems())));
    }

    @Test
    public void
    theOneWhereWeConfigure() {

        String itemID = testWorld.anyItemID();
        configService.configureItem(itemID);
        assertThat(capturingConfigServiceListener.configuredItem,is(itemID));
    }

    @Test
    public void
    theOneWhereWeGetTheOptions() {

        String itemID = testWorld.anyItemID();
        configService.configureItem(itemID);
        assertThat(configService.selectedConfigOptions(),is(equalTo(testWorld.optionsListForItem(itemID))));
    }


    @Test
    public void
    storesDefaultOptions() {

        String itemID = testWorld.anyItemID();
        configService.configureItem(itemID);
        assertThat(configService.currentOptionForItem(itemID),is(testWorld.defaultOptionForItem(itemID)));
    }

    @Before
    public void buildTestWorld() {

        testWorld = new TestWorld();
        configService = testWorld.build();
        capturingConfigServiceListener = configService.addListener(new CapturingConfigServiceListener());
    }

}
