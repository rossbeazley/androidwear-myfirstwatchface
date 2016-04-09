package uk.co.rossbeazley.wear.android.ui.config.ui;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigServiceListener;

public class UiNavigation {
    public UiNavigation(ConfigService configService, final NavigationController navigation) {
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
                navigation.toConfigItemsList();
            }
        });

        navigation.toConfigItemsList();
    }
}
