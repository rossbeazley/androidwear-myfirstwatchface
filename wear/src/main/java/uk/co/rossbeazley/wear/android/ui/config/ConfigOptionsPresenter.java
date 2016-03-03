package uk.co.rossbeazley.wear.android.ui.config;

/**
 * Created by beazlr02 on 19/02/16.
 */
class ConfigOptionsPresenter {
    public ConfigOptionsPresenter(final ConfigService configService, ConfigItemsListView configItemsListView) {
        configItemsListView.addListener(new ConfigItemsListView.Listener() {
            @Override
            public void itemSelected(String two) {
                configService.configure(two);
            }
        });
        configItemsListView.showConfigItems(configService.configItemsList());
    }
}
