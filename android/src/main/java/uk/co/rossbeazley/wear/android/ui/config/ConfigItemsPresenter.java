package uk.co.rossbeazley.wear.android.ui.config;

import uk.co.rossbeazley.wear.config.ConfigService;

public class ConfigItemsPresenter {
    public ConfigItemsPresenter(final ConfigService configService, ConfigOptionView configItemsListView) {
        configItemsListView.addListener(new ConfigOptionView.Listener() {
            @Override
            public void itemSelected(String two) {
                configService.configureItem(two);
            }
        });
        configItemsListView.showConfigOptions(configService.configItemsList());
    }
}
