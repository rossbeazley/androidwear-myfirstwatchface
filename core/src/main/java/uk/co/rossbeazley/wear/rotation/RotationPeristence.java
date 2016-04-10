package uk.co.rossbeazley.wear.rotation;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

public class RotationPeristence implements CanReceiveRotationUpdates {


    /**
     * new ConfigItem("Rotation")
     .addOptions("North", "East", "South", "West")
     .defaultOption("North")};
     */
    private final ConfigService configService;

    public RotationPeristence(ConfigService configService) {
        this.configService = configService;
    }

    public Orientation reHydrateOrientation() {
        Orientation result = null;

        String rotation = configService.currentOptionForItem("Rotation");
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

    public void persistOrientation(Orientation orientation) {
        if(orientation == Orientation.east()) {
            configService.persistItemChoice("Rotation","East");
        } else if(orientation ==Orientation.south()) {
            configService.persistItemChoice("Rotation","South");
        } else if(orientation ==Orientation.west()) {
            configService.persistItemChoice("Rotation","West");
        } else if(orientation ==Orientation.north()) {
            configService.persistItemChoice("Rotation","North");
        }
    }

    @Override
    public void rotationUpdate(Orientation to) {
        persistOrientation(to);
    }
}
