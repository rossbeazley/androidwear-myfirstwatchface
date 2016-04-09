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

    RotationPeristence rotationPeristence;

    public Rotation(final Orientation orientation, Announcer<CanReceiveRotationUpdates> to, ConfigService configService) {
        rotationPeristence = new RotationPeristence(configService);
        //this.orientation = orientation;
        this.orientation = rotationPeristence.reHydrateOrientation();
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
        rotationPeristence.persistOrientation(orientation);
    }

}
