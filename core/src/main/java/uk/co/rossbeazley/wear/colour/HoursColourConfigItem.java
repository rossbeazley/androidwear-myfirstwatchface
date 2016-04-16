package uk.co.rossbeazley.wear.colour;

import java.util.Arrays;
import java.util.List;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;

public class HoursColourConfigItem implements ConfigItem {
    private final Colours.Colour defaultColour;

    public HoursColourConfigItem(Colours.Colour colour) {
        defaultColour = colour;
    }

    @Override
    public String itemId() {
        return "Hours Colour";
    }

    @Override
    public List<String> options() {
        return Arrays.asList("Red","Blue");
    }

    @Override
    public String defaultOption() {
        return "Red";
    }

    public Colours.Colour defaultColour() {
        return defaultColour;
    }

    public String optionFor(Colours.Colour red) {
        String result = null;
        if(red== Colours.Colour.RED) {
            result = "Red";
        } else if(red == Colours.Colour.BLUE) {
            result = "Blue";
        }

        return result;
    }

    public Colours.Colour colourFor(String defaultOption) {
        Colours.Colour result = null;

        if(defaultOption.equals("Red")) {
            result = Colours.Colour.RED;
        } else if(defaultOption.equals("Blue")) {
            result = Colours.Colour.BLUE;
        }

        return result;
    }
}