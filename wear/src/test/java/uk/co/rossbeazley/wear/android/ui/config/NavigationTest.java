package uk.co.rossbeazley.wear.android.ui.config;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

        StubStringPersistence stubStringPersistence = new StubStringPersistence(configItems);
        configService = new ConfigService(stubStringPersistence);
        new UiNavigation(configService, navigation);
    }

    @Test
    public void firstScreenShowIsConfigOptionsList() {
        assertThat(navigation.screen, is(NavigationControllerJournal.CONFIG_OPTIONS_LIST));
        assertThat(navigation.journal, hasItems(NavigationControllerJournal.CONFIG_OPTIONS_LIST));
    }

    @Test
    public void anOptionIsConfiguredAndTheConfigScreenIsShown() {
        configService.configure("two");
        assertThat(navigation.screen, is(NavigationControllerJournal.CONFIG_OPTION));
        assertThat(navigation.journal, hasItems(NavigationControllerJournal.CONFIG_OPTIONS_LIST, NavigationControllerJournal.CONFIG_OPTION));
    }

    private static class NavigationControllerJournal implements NavigationController {
        public static final String CONFIG_OPTIONS_LIST = "ConfigOptionsList";
        public static final String CONFIG_OPTION = "ConfigOption";
        public static final String UNKNOWN = "UNKNOWN";

        private String screen = UNKNOWN;
        public List<String> journal = new ArrayList<>();

        @Override
        public void defaultNavigation() {
            pushScreen("defaultNavigation");
        }

        @Override
        public void toConfigOptionsList() {
            pushScreen(CONFIG_OPTIONS_LIST);
        }

        @Override
        public void toConfigOption() {
            pushScreen(CONFIG_OPTION);
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
            });

            navigation.toConfigOptionsList();
        }
    }
}
