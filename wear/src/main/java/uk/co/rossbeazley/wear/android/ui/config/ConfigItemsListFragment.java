package uk.co.rossbeazley.wear.android.ui.config;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

import uk.co.rossbeazley.wear.config.ConfigService;

public class ConfigItemsListFragment extends Fragment implements NeedsConfigService {

    private ConfigService configService;

    public ConfigItemsListFragment() {
    }

    public static ConfigItemsListFragment createConfigItemsListFragment() {
        final ConfigItemsListFragment configItemsListFragment = new ConfigItemsListFragment();
        final Bundle args = new Bundle();
        args.putSerializable("factory",ConfigItemsListUIFactory.FACTORY);
        configItemsListFragment.setArguments(args);
        return configItemsListFragment;
    }

    public interface UIFactory {
        View createView(ViewGroup container);
        void createPresenters(ConfigService configService, ConfigItemsListView view);
    }

    public enum ConfigItemsListUIFactory implements UIFactory {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return uiFactory().createView(container);
    }

    private UIFactory uiFactory() {
        return (UIFactory) getArguments().getSerializable("factory");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // make presenters, but need to cast :S
        buildPresenters((ConfigItemsListView)view, savedInstanceState);
    }

    public void buildPresenters(ConfigItemsListView view, Bundle savedInstanceState) {
        uiFactory().createPresenters(configService, view);
    }

    @Override
    public void attachConfigService(ConfigService configService) {
        this.configService = configService;
    }

    public static String tag() {
        return "CONFIG_ITEMS";
    }
}
