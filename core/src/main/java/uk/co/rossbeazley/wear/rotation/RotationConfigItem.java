package uk.co.rossbeazley.wear.rotation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;

public class RotationConfigItem extends ConfigItem {

    private BiMap bimap;

    public RotationConfigItem() {
        this("Rotation");
    }

    public RotationConfigItem(String id) {
        super(id);

        bimap = new RotationConfigItem.BiMap();
        bimap.put("North", Orientation.north());
        bimap.put("South", Orientation.south());
        bimap.put("West", Orientation.west());
        bimap.put("East", Orientation.east());


        addOptions(bimap.keyArray());
        defaultOption(optionFor(Orientation.north()));
    }

    public String optionFor(Orientation orientation) {
        return bimap.keyForValue(orientation);
    }

    public Orientation orientationFor(String rotation) {
        return bimap.valueForKey(rotation);
    }

    private class BiMap {

        private HashMap<String, Orientation> keyToValueMap = new HashMap<>();
        private HashMap<Orientation, String> valuetoKeyMap = new HashMap<>();

        public void put(String key, Orientation value) {
            keyToValueMap.put(key, value);
            valuetoKeyMap.put(value, key);
        }

        public String[] keyArray() {
            return valuetoKeyMap.values().toArray(new String[valuetoKeyMap.size()]);
        }

        public String keyForValue(Orientation orientation) {
            return valuetoKeyMap.get(orientation);
        }

        public Orientation valueForKey(String rotation) {
            return keyToValueMap.get(rotation);
        }
    }
}
