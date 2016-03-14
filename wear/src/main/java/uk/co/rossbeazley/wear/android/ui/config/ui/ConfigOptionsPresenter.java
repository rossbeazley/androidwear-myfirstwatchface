package uk.co.rossbeazley.wear.android.ui.config.ui;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

public class ConfigOptionsPresenter {
    public ConfigOptionsPresenter(final ConfigService configService, ConfigItemsListView configItemsListView) {
        configItemsListView.addListener(new ConfigItemsListView.Listener() {
            @Override
            public void itemSelected(String two) {
                configService.configureItem(two);
            }
        });
        configItemsListView.showConfigItems(configService.configItemsList());
    }
}
