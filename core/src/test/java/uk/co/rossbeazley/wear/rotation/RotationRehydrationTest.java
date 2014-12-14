package uk.co.rossbeazley.wear.rotation;

import org.junit.Test;

import uk.co.rossbeazley.wear.Core;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RotationRehydrationTest {

    private float degreesRotation;
    private Core core;
    private CanBeObservedForChangesToRotation rotation;

    public void createCoreAtRotation(Orientation orientation) {
        core = new Core(orientation);
        rotation = core.canBeObservedForChangesToRotation;
        rotation.observe(new CanReceiveRotationUpdates() {
            @Override
            public void rotationUpdate(Orientation to) {
                degreesRotation = to.degrees();
            }
        });

    }

    @Test
    public void theOneWhereWeRotateRightToEast() {
        createCoreAtRotation(Orientation.north());
        core.canBeRotated.right();
        assertThat(degreesRotation, is(90.0f));
    }

    @Test
    public void theOneWhereWeRotateRightToSouth() {
        createCoreAtRotation(Orientation.east());
        core.canBeRotated.right();
        assertThat(degreesRotation, is(180.0f));
    }


    @Test
    public void theOneWhereWeRotateRightToWest() {
        createCoreAtRotation(Orientation.south());
        core.canBeRotated.right();
        assertThat(degreesRotation, is(270.0f));
    }


    @Test
    public void theOneWhereWeRotateRightToNorth() {
        createCoreAtRotation(Orientation.west());
        core.canBeRotated.right();
        assertThat(degreesRotation, is(0.0f));
    }


}
