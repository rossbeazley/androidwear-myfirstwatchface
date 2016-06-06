package uk.co.rossbeazley.wear.android.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.rossbeazley.wear.ChosenOptionView;
import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.android.ui.config.ConfigItemsPresenter;
import uk.co.rossbeazley.wear.android.ui.config.SelectableItemListView;
import uk.co.rossbeazley.wear.android.ui.config.UIFactory;
import uk.co.rossbeazley.wear.config.ConfigService;

import static android.view.LayoutInflater.from;

public enum ChosenOptionMobileUIFactory implements UIFactory<View> {
    FACTORY {
        @Override
        public View createView(ViewGroup container) {
            return from(container.getContext()).inflate(R.layout.success,container,false);
        }

        @Override
        public void createPresenters(ConfigService configService, View view) {

        }
    };


}
