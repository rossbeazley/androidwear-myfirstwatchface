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

    public String optionFor(Colours.Colour colour) {
        String result = null;
        if (colour == Colours.Colour.WHITE) {
            result = "White";
        } else if (colour == Colours.Colour.BLACK) {
            result = "Black";
        }
        return result;
    }
}
