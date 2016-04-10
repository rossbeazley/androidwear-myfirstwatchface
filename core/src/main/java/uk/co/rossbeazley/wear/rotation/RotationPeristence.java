package uk.co.rossbeazley.wear.rotation;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigServiceListener;

public class RotationPeristence implements CanReceiveRotationUpdates {


    /**
     * new ConfigItem("Rotation")
     .addOptions("North", "East", "South", "West")
     .defaultOption("North")};
     */
    private final ConfigService configService;

    public RotationPeristence(ConfigService configService, final Rotation rotation) {
        this.configService = configService;
        rotation.to(reHydrateOrientation());

        configService.addListener(new ConfigServiceListener() {
            private String item;

            @Override
            public void configuring(String item) {

                this.item = item;
            }

            @Override
            public void error(KeyNotFound keyNotFound) {

            }

            @Override
            public void chosenOption(String option) {
                if(item.equals("Rotation")) {
                    rotation.to(configServiceStringToRotation(option));
                }
            }
        });

    }

    public Orientation reHydrateOrientation() {
        Orientation result = null;

        String rotation = configService.currentOptionForItem("Rotation");
        result = configServiceStringToRotation(rotation);

        return result;
    }

    private Orientation configServiceStringToRotation(String rotation) {
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
