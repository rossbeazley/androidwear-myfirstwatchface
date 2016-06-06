package uk.co.rossbeazley.wear.android.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.rossbeazley.wear.android.ui.config.UIFactory;
import uk.co.rossbeazley.wear.config.ConfigService;

import static android.view.LayoutInflater.from;
import  uk.co.rossbeazley.wear.R;

public enum SelectAnItemMobileUIFactory implements UIFactory<View> {
    FACTORY {
        @Override
        public View createView(ViewGroup container) {
            return from(container.getContext()).inflate(R.layout.choose_item,container,false);
        }

        @Override
        public void createPresenters(ConfigService configService, View view) {

        }
    };


}
