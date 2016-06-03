package uk.co.rossbeazley.wear.android.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.rossbeazley.wear.ChosenOptionView;
import uk.co.rossbeazley.wear.android.ui.config.ConfigItemsPresenter;
import uk.co.rossbeazley.wear.android.ui.config.SelectableItemListView;
import uk.co.rossbeazley.wear.android.ui.config.UIFactory;
import uk.co.rossbeazley.wear.config.ConfigService;

public enum ChosenOptionMobileUIFactory implements UIFactory<TextView> {
    FACTORY {
        @Override
        public View createView(ViewGroup container) {
            TextView view = new TextView(container.getContext());
            view.setText("SAVED!");
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return view;
        }

        @Override
        public void createPresenters(ConfigService configService, TextView view) {

        }
    };


}
