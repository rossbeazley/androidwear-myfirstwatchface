package uk.co.rossbeazley.wear.android.ui.config;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class NavigationTest {

    @Test
    public void anOptionIsConfiguredAndTheConfigScreenIsShown() {

        NavigationControllerJournal navigation = new NavigationControllerJournal();

        HashMap<String, List<String>> configItems = new HashMap<String, List<String>>() {{
            put("configItems", Arrays.asList("one", "two", "three"));
            put("two", Arrays.asList("twoOne", "twoTwo", "twoThree", "twoFour"));
        }};

        StubStringPersistence stubStringPersistence = new StubStringPersistence(configItems);
        ConfigService configService = new ConfigService(stubStringPersistence);
        new UiNavigation(configService, navigation);

        configService.configure("two");

        assertThat(navigation.screen, is("ConfigOptionsList"));

        assertThat(navigation.journal, hasItems("defaultNavigation","ConfigOptionsList"));
    }

    private static class NavigationControllerJournal implements NavigationController {
        private String screen = "UNKNOWN";
        public List<String> journal = new ArrayList<>();

        @Override
        public void defaultNavigation() {
            pushScreen("defaultNavigation");
        }

        @Override
        public void toConfigOptionsList() {
            pushScreen("ConfigOptionsList");
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
                    navigation.toConfigOptionsList();
                }

                @Override
                public void error(KeyNotFound keyNotFound) {

                }
            });

            navigation.defaultNavigation();
        }
    }
}
