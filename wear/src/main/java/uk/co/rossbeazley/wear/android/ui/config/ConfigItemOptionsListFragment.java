package uk.co.rossbeazley.wear.android.ui.config;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import uk.co.rossbeazley.wear.config.ConfigService;

public class ConfigItemOptionsListFragment {

    public static UIFactoryFragment createConfigItemOptionsListFragment() {
        final UIFactoryFragment configItemOptionsListFragment = UIFactoryFragment.createUIFactoryFragment(null);
        final Bundle args = new Bundle();
        args.putSerializable("factory",ConfigItemsOptionsListUIFactory.FACTORY);
        configItemOptionsListFragment.setArguments(args);
        return configItemOptionsListFragment;
    }

    public enum ConfigItemsOptionsListUIFactory implements UIFactory<SelectableItemListView> {

        FACTORY;

        @Override
        public View createView(ViewGroup container) {
            ConfigOptionsWearView configOptionsListWearView = new ConfigOptionsWearView(container.getContext());
            configOptionsListWearView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return configOptionsListWearView;
        }

        @Override
        public void createPresenters(ConfigService configService, SelectableItemListView view) {
            new ConfigOptionsPresenter(view, configService);
        }

    }

}
