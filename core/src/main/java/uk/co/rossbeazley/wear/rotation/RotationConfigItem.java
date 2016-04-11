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
}
