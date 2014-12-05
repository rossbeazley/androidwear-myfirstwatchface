package uk.co.rossbeazley.wear.rotation;

import uk.co.rossbeazley.wear.Announcer;

public class Rotation implements CanBeRotated, CanBeObservedForChangesToRotation {

    Orientation orientation = Orientation.north();
    Announcer<CanReceiveRotationUpdates> rotationUpdates;

    public Rotation() {
        rotationUpdates = Announcer.to(CanReceiveRotationUpdates.class);
    }

    @Override
    public void observe(CanReceiveRotationUpdates canReceiveRotationUpdates) {
        rotationUpdates.addListener(canReceiveRotationUpdates);
    }

    @Override
    public void right() {
        orientation = orientation.right();
        rotationUpdates.announce().rotationUpdate(orientation);
    }
}
