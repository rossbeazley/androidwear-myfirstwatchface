package uk.co.rossbeazley.wear.rotation;

import uk.co.rossbeazley.wear.Announcer;

public class Rotation implements CanBeRotated, CanBeObservedForChangesToRotation {

    Orientation orientation;
    Announcer<CanReceiveRotationUpdates> rotationUpdates;

    public Rotation() {
        this(Orientation.north());
    }

    public Rotation(Orientation orientation) {
        this.orientation = orientation;
        rotationUpdates = Announcer.to(CanReceiveRotationUpdates.class);
    }

    @Override
    public void observe(CanReceiveRotationUpdates canReceiveRotationUpdates) {
        rotationUpdates.addListener(canReceiveRotationUpdates);
    }

    @Override
    public void right() {
        to(orientation.right());
    }

    @Override
    public void to(Orientation newOrientation) {
        orientation = newOrientation;
        rotationUpdates.announce().rotationUpdate(orientation);
    }
}
