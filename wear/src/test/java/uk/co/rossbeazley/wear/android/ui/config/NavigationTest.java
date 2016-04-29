package uk.co.rossbeazley.wear.android.ui.config;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.TestWorld;
import uk.co.rossbeazley.wear.config.ConfigService;
import uk.co.rossbeazley.wear.ui.config.UIEvents;
import uk.co.rossbeazley.wear.ui.config.UiNavigation;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class NavigationTest {

    private NavigationControllerJournal navigation;
    private ConfigService configService;
    private UIEvents uiNavigation;
    private TestWorld testWorld;

    @Before
    public void buildWorld() {

        navigation = new NavigationControllerJournal();
        testWorld = new TestWorld();
        configService = testWorld.build();
        uiNavigation = new UiNavigation(configService, navigation);
    }

    @Test
    public void firstScreenShowIsConfigOptionsList() {
        assertThat(navigation.screen, is(NavigationControllerJournal.CONFIG_ITEMS_LIST));
        assertThat(navigation.journal, hasItems(NavigationControllerJournal.CONFIG_ITEMS_LIST));
    }

    @Test
    public void anOptionIsConfiguredAndTheConfigScreenIsShown() {
        configService.configureItem(testWorld.anyItemID());
        assertThat(navigation.screen, is(NavigationControllerJournal.CONFIG_OPTION));
        assertThat(navigation.journal, hasItems(NavigationControllerJournal.CONFIG_ITEMS_LIST, NavigationControllerJournal.CONFIG_OPTION));
    }

    @Test
    public void anOptionIsSelectedAndATickIsShown() {
        final String item = testWorld.anyItemID();
        configService.configureItem(item);
        configService.chooseOption(testWorld.anyOptionForItem(item));
        assertThat(navigation.screen, is(NavigationControllerJournal.CONFIG_OPTION_SELECTED));
    }

    @Test
    public void afterTheTickIsShownWeGoToTheStart() {

        final String item = testWorld.anyItemID();
        configService.configureItem(item);
        configService.chooseOption(testWorld.anyOptionForItem(item));
        uiNavigation.optionSelectedFinished();

        assertThat(navigation.screen, is(NavigationControllerJournal.CONFIG_ITEMS_LIST));
    }

}
