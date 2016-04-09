package uk.co.rossbeazley.wear.rotation;

import uk.co.rossbeazley.wear.Announcer;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

public class Rotation implements CanBeRotated, uk.co.rossbeazley.wear.CanBeObserved<CanReceiveRotationUpdates> {

    /**
     * new ConfigItem("Rotation")
     .addOptions("North", "East", "South", "West")
     .defaultOption("North")};
     */

    Orientation orientation;
    Announcer<CanReceiveRotationUpdates> rotationUpdates;
    private final ConfigService configService;
    private CanReceiveRotationUpdates announce;


    public Rotation(final Orientation orientation, Announcer<CanReceiveRotationUpdates> to, ConfigService configService) {
        //this.orientation = orientation;
        String rotation = configService.currentOptionForItem("Rotation");
        if(rotation.equals("North")) {
            this.orientation = Orientation.north();
        } else if(rotation.equals("East")) {
            this.orientation = Orientation.east();
        } else if(rotation.equals("South")) {
            this.orientation = Orientation.south();
        } else if(rotation.equals("West")) {
            this.orientation = Orientation.west();
        }
        rotationUpdates = to;
        this.configService = configService;
        rotationUpdates.registerProducer(new Announcer.Producer<CanReceiveRotationUpdates>() {
            @Override
            public void observed(CanReceiveRotationUpdates observer) {
                observer.rotationUpdate(Rotation.this.orientation);
            }
        });

        announce = rotationUpdates.announce();
    }

    @Override
    public void addListener(CanReceiveRotationUpdates canReceiveRotationUpdates) {
        rotationUpdates.addListener(canReceiveRotationUpdates);
    }

    @Override
    public void removeListener(CanReceiveRotationUpdates canReceiveRotationUpdates) {

    }

    @Override
    public void right() {
        to(orientation.right());
    }

    @Override
    public void to(Orientation newOrientation) {
        orientation = newOrientation;
        announce.rotationUpdate(orientation);
        if(orientation==Orientation.east()) {
            configService.persistItemChoice("Rotation","East");
        } else if(orientation==Orientation.south()) {
            configService.persistItemChoice("Rotation","South");
        } else if(orientation==Orientation.west()) {
            configService.persistItemChoice("Rotation","West");
        } else if(orientation==Orientation.north()) {
            configService.persistItemChoice("Rotation","North");
        }
    }
}
