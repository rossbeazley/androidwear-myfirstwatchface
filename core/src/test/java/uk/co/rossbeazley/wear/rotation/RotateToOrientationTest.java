package uk.co.rossbeazley.wear.rotation;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.Core;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RotateToOrientationTest {

    private float degreesRotation;
    private Core core;
    private CanBeObservedForChangesToRotation rotation;

    @Before
    public void setUp() throws Exception {
        core = new Core();
        rotation = core.canBeObservedForChangesToRotation;
        rotation.observe(new CanReceiveRotationUpdates() {
            @Override
            public void rotationUpdate(Orientation to) {
                degreesRotation = to.degrees();
            }
        });

    }

    @Test
    public void theOneWhereWeRotateToNorth() {
        core.canBeRotated.to(Orientation.north());
        assertThat(degreesRotation, is(0.0f));
    }

    @Test
    public void theOneWhereWeRotateToEast() {
        core.canBeRotated.to(Orientation.east());
        assertThat(degreesRotation, is(90.0f));
    }

    @Test
    public void theOneWhereWeRotateToSouth() {
        core.canBeRotated.to(Orientation.south());
        assertThat(degreesRotation, is(180.0f));
    }

    @Test
    public void theOneWhereWeRotateToWest() {
        core.canBeRotated.to(Orientation.west());
        assertThat(degreesRotation, is(270.0f));
    }


    @Test
    public void theOneWhereWeRotateRightToEast() {
        core.canBeRotated.to(Orientation.north());
        core.canBeRotated.right();
        assertThat(degreesRotation, is(90.0f));
    }

    @Test
    public void theOneWhereWeRotateRightToSouth() {
        core.canBeRotated.to(Orientation.east());
        core.canBeRotated.right();
        assertThat(degreesRotation, is(180.0f));
    }

    @Test
    public void theOneWhereWeRotateRightToWest() {
        core.canBeRotated.to(Orientation.south());
        core.canBeRotated.right();
        assertThat(degreesRotation, is(270.0f));
    }

    @Test
    public void theOneWhereWeRotateRightToNorth() {
        core.canBeRotated.to(Orientation.west());
        core.canBeRotated.right();
        assertThat(degreesRotation, is(0.0f));
    }


}
