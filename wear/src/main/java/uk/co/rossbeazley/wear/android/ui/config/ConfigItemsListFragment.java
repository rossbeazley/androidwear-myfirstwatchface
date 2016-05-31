package uk.co.rossbeazley.wear.android.ui.config;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import uk.co.rossbeazley.wear.config.ConfigService;

public class ConfigItemsListFragment {

    public static UIFactoryFragment createConfigItemsListFragment() {
        final UIFactoryFragment configItemsListFragment = UIFactoryFragment.createUIFactoryFragment(null);
        final Bundle args = new Bundle();
        args.putSerializable("factory",ConfigItemsListUIFactory.FACTORY);
        configItemsListFragment.setArguments(args);
        return configItemsListFragment;
    }

    public enum ConfigItemsListUIFactory implements UIFactory<ConfigOptionView> {
        FACTORY;

        @Override
        public View createView(ViewGroup container) {
            ConfigItemsListWearView configOptionsListWearView = new ConfigItemsListWearView(container.getContext());
            configOptionsListWearView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return configOptionsListWearView;
        }

        @Override
        public void createPresenters(ConfigService configService, ConfigOptionView view) {
            /*return */new ConfigItemsPresenter(configService, view);
        }
    }


}
