package uk.co.rossbeazley.wear.android.ui.config;

/**
 * Created by beazlr02 on 19/02/16.
 */
class ConfigOptionsPresenter {
    public ConfigOptionsPresenter(final ConfigService configService, ConfigListView configListView) {
        configListView.addListener(new ConfigListView.Listener() {
            @Override
            public void itemSelected(String two) {
                configService.configure(two);
            }
        });
        configListView.showConfigItems(configService.configItemsList());
    }
}
