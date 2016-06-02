package uk.co.rossbeazley.wear;

import uk.co.rossbeazley.wear.android.ui.config.SelectableItemListView;
import uk.co.rossbeazley.wear.config.ConfigService;
import uk.co.rossbeazley.wear.config.ConfigServiceListener;

public class MobileUINavigation {
    public MobileUINavigation(final ScreenNavigationController screen, ConfigService configService) {
        screen.showLeft();
        screen.showRight(SelectAnItemView.class);

        configService.addListener(new ConfigServiceListener() {
            @Override
            public void configuring(String item) {
                screen.showRight(SelectableItemListView.class); //TODO change to show Presenter class, not view class
            }

            @Override
            public void error(KeyNotFound keyNotFound) {

            }

            @Override
            public void chosenOption(String option) {
                // show OK, then timer, then... (maybe timer goes in presenter....)
                screen.showRight(ChosenOptionView.class);

            }
        });
    }
}
