package uk.co.rossbeazley.wear.rotation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;

public class RotationConfigItem extends ConfigItem {

    public RotationConfigItem() {
        this("Rotation");
    }

    Map<String, Orientation> pairs;

    public RotationConfigItem(String id) {
        super(id);

        pairs = new HashMap<>();
        pairs.put("North", Orientation.north());
        pairs.put("South", Orientation.south());
        pairs.put("West", Orientation.west());
        pairs.put("East", Orientation.east());

        addOptions(pairs.keySet().toArray(new String[pairs.size()]));
        defaultOption(optionFor(Orientation.north()));
    }

    public String optionFor(Orientation orientation) {

        Set<Map.Entry<String, Orientation>> entries = pairs.entrySet();
        String result = null;
        for (Map.Entry<String, Orientation> el : entries) {
            if (el.getValue() == orientation) {
                result = el.getKey();
                return result;
            }
        }
        return result;
    }

    public Orientation orientationFor(String rotation) {
        return pairs.get(rotation);
    }
}
