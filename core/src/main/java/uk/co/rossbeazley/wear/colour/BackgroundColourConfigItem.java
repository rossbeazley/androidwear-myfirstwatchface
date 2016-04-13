package uk.co.rossbeazley.wear.colour;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;

public class BackgroundColourConfigItem extends ConfigItem {

    public BackgroundColourConfigItem() {
        this("Background");
    }

    public BackgroundColourConfigItem(String id) {
        super(id);
        addOptions("Black", "White");
        defaultOption("Black");
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

    public Colours.Colour colourFor(String colourString) {
        Colours.Colour result = null;

        if(colourString.equals("Black")) {
            result = Colours.Colour.BLACK;
        } else if(colourString.equals("White")) {
            result = Colours.Colour.WHITE;
        }

        return result;
    }
}
