package uk.co.rossbeazley.wear.colour;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;

public class BackgroundColourConfigItem extends ConfigItem {

    public BackgroundColourConfigItem() {
        this("Background");
    }

    public BackgroundColourConfigItem(String id) {
        super(id);
        addOptions("Black", "White");
        defaultOption("White");
    }
}
