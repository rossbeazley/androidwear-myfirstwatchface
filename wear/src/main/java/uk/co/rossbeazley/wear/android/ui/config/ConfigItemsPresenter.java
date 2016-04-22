package uk.co.rossbeazley.wear.android.ui.config;

import uk.co.rossbeazley.wear.config.ConfigService;

public class ConfigItemsPresenter {
    public ConfigItemsPresenter(final ConfigService configService, ConfigItemsListView configItemsListView) {
        configItemsListView.addListener(new ConfigItemsListView.Listener() {
            @Override
            public void itemSelected(String two) {
                configService.configureItem(two);
            }
        });
        configItemsListView.showConfigItems(configService.configItemsList());
    }
}
