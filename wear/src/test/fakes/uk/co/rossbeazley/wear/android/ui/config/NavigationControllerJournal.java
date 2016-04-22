package uk.co.rossbeazley.wear.android.ui.config;

import java.util.ArrayList;
import java.util.List;

import uk.co.rossbeazley.wear.android.ui.config.NavigationController;

public class NavigationControllerJournal implements NavigationController {
    public static final String CONFIG_ITEMS_LIST = "ConfigItemsList";
    public static final String CONFIG_OPTION = "ConfigOption";
    public static final String UNKNOWN = "UNKNOWN";
    public static final String CONFIG_OPTION_SELECTED = "ConfigOptionSelected";

    public String screen = UNKNOWN;
    public List<String> journal = new ArrayList<>();

    @Override
    public void defaultNavigation() {
        pushScreen("defaultNavigation");
    }

    @Override
    public void toConfigItemsList() {
        pushScreen(CONFIG_ITEMS_LIST);
    }

    @Override
    public void toConfigOption() {
        pushScreen(CONFIG_OPTION);
    }

    @Override
    public void toConfigOptionSelected() {
        pushScreen(CONFIG_OPTION_SELECTED);
    }

    public void pushScreen(String screen) {
        this.screen = screen;
        journal.add(screen);
    }
}
