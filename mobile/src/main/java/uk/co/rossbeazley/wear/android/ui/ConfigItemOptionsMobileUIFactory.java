package uk.co.rossbeazley.wear.android.ui;

import android.view.View;
import android.view.ViewGroup;

import uk.co.rossbeazley.wear.android.ui.config.ConfigItemsPresenter;
import uk.co.rossbeazley.wear.android.ui.config.ConfigOptionsPresenter;
import uk.co.rossbeazley.wear.android.ui.config.SelectableItemListView;
import uk.co.rossbeazley.wear.android.ui.config.UIFactory;
import uk.co.rossbeazley.wear.config.ConfigService;

public enum ConfigItemOptionsMobileUIFactory implements UIFactory<SelectableItemListView> {
    FACTORY {
        @Override
        public View createView(ViewGroup container) {
            SelectableItemRecyclerView view = new SelectableItemRecyclerView(container.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public void createPresenters(ConfigService configService, SelectableItemListView view) {
            new ConfigOptionsPresenter(view,configService);
        }
    };


}
