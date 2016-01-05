package uk.co.rossbeazley.wear.rotation;

import uk.co.rossbeazley.wear.Announcer;

public class Rotation implements CanBeRotated, uk.co.rossbeazley.wear.CanBeObserved<CanReceiveRotationUpdates> {

    Orientation orientation;
    Announcer<CanReceiveRotationUpdates> rotationUpdates;
    private CanReceiveRotationUpdates announce;

    public Rotation(Announcer<CanReceiveRotationUpdates> to) {
        this(Orientation.north(), to);
    }

    public Rotation(final Orientation orientation, Announcer<CanReceiveRotationUpdates> to) {
        this.orientation = orientation;
        rotationUpdates = to;
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
    }
}
