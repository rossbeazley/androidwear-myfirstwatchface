package uk.co.rossbeazley.wear.ui.config;

import uk.co.rossbeazley.wear.config.ConfigService;
import uk.co.rossbeazley.wear.config.ConfigServiceListener;

public class UiNavigation implements UIEvents {
    private final NavigationController navigation;

    public UiNavigation(ConfigService configService, final NavigationController navigation) {
        this.navigation = navigation;
        configService.addListener(new ConfigServiceListener() {
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
                //navigation.toConfigItemsList();
            }
        });

        navigation.toConfigItemsList();
    }

    @Override
    public void optionSelectedFinished() {
        navigation.toConfigItemsList();
    }
}
