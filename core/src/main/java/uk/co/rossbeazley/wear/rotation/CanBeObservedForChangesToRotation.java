package uk.co.rossbeazley.wear.rotation;

/**
* Created by beazlr02 on 27/11/2014.
*/
public interface CanBeObservedForChangesToRotation {
    void addListener(CanReceiveRotationUpdates canReceiveRotationUpdates);
    void removeListener(CanReceiveRotationUpdates canReceiveRotationUpdates);

}
