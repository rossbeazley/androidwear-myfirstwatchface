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

    public Orientation orientationFor(String rotation) {
        Orientation result = null;
        if(rotation.equals("North")) {
            result = Orientation.north();
        } else if(rotation.equals("East")) {
            result = Orientation.east();
        } else if(rotation.equals("South")) {
            result = Orientation.south();
        } else if(rotation.equals("West")) {
            result = Orientation.west();
        }

        return result;
    }
}
