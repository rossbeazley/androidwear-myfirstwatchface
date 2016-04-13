package uk.co.rossbeazley.wear.rotation;

import java.util.List;

import uk.co.rossbeazley.wear.BiMap;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;

public class RotationConfigItem implements ConfigItem {

    private BiMap<String,Orientation> bimap;
    private String defaultOptionString;

    public RotationConfigItem() {
        defaultOptionString = "North";
        bimap = new BiMap<>();
        bimap.put(defaultOptionString, Orientation.north());
        bimap.put("South", Orientation.south());
        bimap.put("West", Orientation.west());
        bimap.put("East", Orientation.east());

    }

    public String itemId() {
        return "Rotation";
    }

    public String defaultOption() {
        return defaultOptionString;
    }


    @Override
    public List<String> options() {
        return bimap.keyList();
    }


    public String optionFor(Orientation orientation) {
        return bimap.keyForValue(orientation);
    }

    public Orientation orientationFor(String rotation) {
        return bimap.valueForKey(rotation);
    }

}
