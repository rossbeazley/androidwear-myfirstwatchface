package uk.co.rossbeazley.wear.colour;

import java.util.List;

import uk.co.rossbeazley.wear.BiMap;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;

public class BackgroundColourConfigItem implements ConfigItem {

    BiMap<String, Colours.Colour> bimap = new BiMap<>();
    private String defaultOptionString;

    public BackgroundColourConfigItem() {
        defaultOptionString = "White";
        bimap.put("Black", Colours.Colour.BLACK);
        bimap.put(defaultOptionString, Colours.Colour.WHITE);
    }
    public String itemId() {
        return "Background";
    }

    public String optionFor(Colours.Colour colour) {
        return bimap.keyForValue(colour);
    }

    public Colours.Colour colourFor(String colourString) {
        return bimap.valueForKey(colourString);
    }

    public String defaultOption() {
        return defaultOptionString;
    }


    @Override
    public List<String> options() {
        return bimap.keyList();
    }


}
