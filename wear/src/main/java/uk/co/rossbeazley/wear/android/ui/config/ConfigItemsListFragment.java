package uk.co.rossbeazley.wear.android.ui.config;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.rossbeazley.wear.config.ConfigService;

public class ConfigItemsListFragment extends UIFactoryFragment {

    public ConfigItemsListFragment() {
    }

    public static UIFactoryFragment createConfigItemsListFragment() {
        final UIFactoryFragment configItemsListFragment = new UIFactoryFragment();
        final Bundle args = new Bundle();
        args.putSerializable("factory",ConfigItemsListUIFactory.FACTORY);
        configItemsListFragment.setArguments(args);
        return configItemsListFragment;
    }

    public interface UIFactory<ViewType> {
        View createView(ViewGroup container);
        void createPresenters(ConfigService configService, ViewType view);
    }

    public enum ConfigItemsListUIFactory implements UIFactory<ConfigItemsListView> {
        FACTORY;

        @Override
        public View createView(ViewGroup container) {
            ConfigItemsListWearView configOptionsListWearView = new ConfigItemsListWearView(container.getContext());
            configOptionsListWearView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return configOptionsListWearView;
        }

        @Override
        public void createPresenters(ConfigService configService, ConfigItemsListView view) {
            /*return */new ConfigItemsPresenter(configService, view);
        }
    }


    public static String tag() {
        return "CONFIG_ITEMS";
    }
}
