package uk.co.rossbeazley.wear.android.ui.config.ui;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import uk.co.rossbeazley.wear.android.ui.config.HashMapPersistence;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class NavigationTest {

    private NavigationControllerJournal navigation;
    private ConfigService configService;

    @Before
    public void buildWorld() {

        navigation = new NavigationControllerJournal();

        HashMap<String, List<String>> configItems = new HashMap<String, List<String>>() {{
            put("configItems", Arrays.asList("one", "two", "three"));
            put("two", Arrays.asList("twoOne", "twoTwo", "twoThree", "twoFour"));
        }};

        HashMapPersistence hashMapPersistence = new HashMapPersistence(configItems);
        configService = new ConfigService(hashMapPersistence);
        new UiNavigation(configService, navigation);
    }

    @Test
    public void firstScreenShowIsConfigOptionsList() {
        assertThat(navigation.screen, is(NavigationControllerJournal.CONFIG_ITEMS_LIST));
        assertThat(navigation.journal, hasItems(NavigationControllerJournal.CONFIG_ITEMS_LIST));
    }

    @Test
    public void anOptionIsConfiguredAndTheConfigScreenIsShown() {
        configService.configureItem("two");
        assertThat(navigation.screen, is(NavigationControllerJournal.CONFIG_OPTION));
        assertThat(navigation.journal, hasItems(NavigationControllerJournal.CONFIG_ITEMS_LIST, NavigationControllerJournal.CONFIG_OPTION));
    }

    @Test
    public void anOptionIsSelectedAndATickIsShown() {
        configService.configureItem("two");

        configService.chooseOption("twoThree");

        assertThat(navigation.screen, is(NavigationControllerJournal.CONFIG_OPTION_SELECTED));
    }

    private static class NavigationControllerJournal implements NavigationController {
        public static final String CONFIG_ITEMS_LIST = "ConfigItemsList";
        public static final String CONFIG_OPTION = "ConfigOption";
        public static final String UNKNOWN = "UNKNOWN";
        public static final String CONFIG_OPTION_SELECTED = "ConfigOptionSelected";

        private String screen = UNKNOWN;
        public List<String> journal = new ArrayList<>();

        @Override
        public void defaultNavigation() {
            pushScreen("defaultNavigation");
        }

        @Override
        public void toConfigItemsList() {
            pushScreen(CONFIG_ITEMS_LIST);
        }

        @Override
        public void toConfigOption() {
            pushScreen(CONFIG_OPTION);
        }

        @Override
        public void toConfigOptionSelected() {
            pushScreen(CONFIG_OPTION_SELECTED);
        }

        public void pushScreen(String screen) {
            this.screen = screen;
            journal.add(screen);
        }
    }

    private class UiNavigation {
        public UiNavigation(ConfigService configService, final NavigationController navigation) {
            configService.addListener(new ConfigService.Listener() {
                @Override
                public void configuring(String item) {
                    navigation.toConfigOption();
                }

                @Override
                public void error(KeyNotFound keyNotFound) {

                }

                @Override
                public void chosenOption(String option) {
                    navigation.toConfigOptionSelected();
                }
            });

            navigation.toConfigItemsList();
        }
    }
}
