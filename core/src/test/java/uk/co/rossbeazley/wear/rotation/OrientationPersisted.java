package uk.co.rossbeazley.wear.rotation;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.android.ui.config.HashMapPersistence;
import uk.co.rossbeazley.wear.android.ui.config.service.StringPersistence;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OrientationPersisted {

    private HashMapPersistence hashMapPersistence;
    private Core core;
    private CapturingCanReceiveRotationUpdates canReceiveSecondsUpdates;

    @Before
    public void createTestWorld() {
        hashMapPersistence = new HashMapPersistence();
        reBuildCore(hashMapPersistence);
    }


    private void reBuildCore(StringPersistence persistence) {
        core = new Core(persistence);
        canReceiveSecondsUpdates = new CapturingCanReceiveRotationUpdates();
        core.canBeObservedForChangesToRotation.addListener(canReceiveSecondsUpdates);
    }

    @Test
    public void remembersNorth() {
        core.canBeRotated.to(Orientation.north());
        reBuildCore(hashMapPersistence);
        assertThat(canReceiveSecondsUpdates.capturedRotation,is(Orientation.north()));
    }

    @Test
    public void remembersNorthAfterDifferentOrientation() {
        core.canBeRotated.to(Orientation.south());
        core.canBeRotated.to(Orientation.north());
        reBuildCore(hashMapPersistence);
        assertThat(canReceiveSecondsUpdates.capturedRotation,is(Orientation.north()));
    }

    @Test
    public void remembersEast() {

        core.canBeRotated.to(Orientation.east());
        reBuildCore(hashMapPersistence);
        assertThat(canReceiveSecondsUpdates.capturedRotation,is(Orientation.east()));
    }

    @Test
    public void remembersSouth() {

        core.canBeRotated.to(Orientation.south());
        reBuildCore(hashMapPersistence);
        assertThat(canReceiveSecondsUpdates.capturedRotation,is(Orientation.south()));
    }

    @Test
    public void remembersWest() {

        core.canBeRotated.to(Orientation.west());
        reBuildCore(hashMapPersistence);
        assertThat(canReceiveSecondsUpdates.capturedRotation,is(Orientation.west()));
    }

    private static class CapturingCanReceiveRotationUpdates implements CanReceiveRotationUpdates {
        public Orientation capturedRotation;

        @Override
        public void rotationUpdate(Orientation to) {

            capturedRotation = to;
        }
    }
}
