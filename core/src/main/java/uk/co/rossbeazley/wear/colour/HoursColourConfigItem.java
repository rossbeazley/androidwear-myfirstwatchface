package uk.co.rossbeazley.wear.colour;

import java.util.List;

import uk.co.rossbeazley.wear.BiMap;
import uk.co.rossbeazley.wear.config.ConfigItem;

public class HoursColourConfigItem implements ConfigItem {
    private final Colours.Colour defaultColour;
    private final BiMap<String, Colours.Colour> stringColourBiMap;

    public HoursColourConfigItem(Colours.Colour colour) {
        defaultColour = colour;
        stringColourBiMap = new BiMap<>();
        stringColourBiMap.put("Red", Colours.Colour.RED);
        stringColourBiMap.put("Blue", Colours.Colour.BLUE);
        stringColourBiMap.put("Green", Colours.Colour.GREEN);
        stringColourBiMap.put("Yellow", Colours.Colour.YELLOW);
        stringColourBiMap.put("Cyan", Colours.Colour.CYAN);

    }

    @Override
    public String itemId() {
        return "Hours Colour";
    }

    @Override
    public List<String> options() {
        return stringColourBiMap.keyList();
    }

    @Override
    public String defaultOption() {
        return optionFor(defaultColour);
    }

    public Colours.Colour defaultColour() {
        return defaultColour;
    }

    public String optionFor(Colours.Colour red) {
        return stringColourBiMap.keyForValue(red);
    }

    public Colours.Colour colourFor(String defaultOption) {
        return stringColourBiMap.valueForKey(defaultOption);
    }
}
