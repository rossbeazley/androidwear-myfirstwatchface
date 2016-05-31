package uk.co.rossbeazley.wear.android.ui.config;

import uk.co.rossbeazley.wear.config.ConfigService;

public class ConfigItemsPresenter {
    public ConfigItemsPresenter(final ConfigService configService, SelectableItemListView configItemsListView) {
        configItemsListView.addListener(new SelectableItemListView.Listener() {
            @Override
            public void itemSelected(String two) {
                configService.configureItem(two);
            }
        });
        configItemsListView.showItems(configService.configItemsList());
    }
}
