package uk.co.rossbeazley.wear.rotation;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;

public class RotationConfigItem extends ConfigItem {

    public RotationConfigItem() {
        this("Rotation");
    }


    public RotationConfigItem(String id) {
        super(id);
        addOptions("North", "East", "South", "West");
        defaultOption("North");
    }

    public String optionFor(Orientation orientation) {
        if (orientation == Orientation.north()) {
            return  "North";
        } else if (orientation == Orientation.south()) {
            return "South";
        } else if (orientation == Orientation.east()) {
            return "East";
        } else if (orientation == Orientation.west()) {
            return "West";
        } else {
            return "";
        }
    }
}
