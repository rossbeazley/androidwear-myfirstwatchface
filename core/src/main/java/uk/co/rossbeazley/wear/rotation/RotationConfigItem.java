package uk.co.rossbeazley.wear.rotation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;

public class RotationConfigItem extends ConfigItem {

    private BiMap bimap;
    private String defaultOptionString;

    public RotationConfigItem() {
        this("Rotation");
    }

    public RotationConfigItem(String id) {
        super(id);

        defaultOptionString = "North";
        bimap = new RotationConfigItem.BiMap();
        bimap.put(defaultOptionString, Orientation.north());
        bimap.put("South", Orientation.south());
        bimap.put("West", Orientation.west());
        bimap.put("East", Orientation.east());

        defaultOption(defaultOptionString);
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

    private class BiMap {

        private HashMap<String, Orientation> keyToValueMap = new HashMap<>();
        private HashMap<Orientation, String> valuetoKeyMap = new HashMap<>();

        public void put(String key, Orientation value) {
            keyToValueMap.put(key, value);
            valuetoKeyMap.put(value, key);
        }

        public String keyForValue(Orientation orientation) {
            return valuetoKeyMap.get(orientation);
        }

        public Orientation valueForKey(String rotation) {
            return keyToValueMap.get(rotation);
        }

        public List<String> keyList() {
            return new ArrayList<>(valuetoKeyMap.values());
        }
    }
}
