package uk.co.rossbeazley.wear.rotation;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.Core;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RotationTest {

    private float degreesRotation;
    private Core core;
    private CanBeObservedForChangesToRotation rotation;

    @Before
    public void setUp() throws Exception {
        core = new Core();
        rotation = core.canBeObservedForChangesToRotation;
        rotation.observe(new CanBeObservedForChangesToRotation.CanReceiveRotationUpdates() {
            @Override
            public void rotationUpdate(Rotation to) {
                degreesRotation = to.degrees();
            }
        });

    }

    @Test
    public void theOneWhereWeRotateRightToEast() {
        core.canBeRotated.right();
        assertThat(degreesRotation, is(90.0f));
    }

    @Test
    public void theOneWhereWeRotateRightToSouth() {
        core.canBeRotated.right();
        core.canBeRotated.right();
        assertThat(degreesRotation, is(180.0f));
    }


    @Test
    public void theOneWhereWeRotateRightToWest() {
        core.canBeRotated.right();
        core.canBeRotated.right();
        core.canBeRotated.right();
        assertThat(degreesRotation, is(270.0f));
    }


    @Test
    public void theOneWhereWeRotateRightToNorth() {
        core.canBeRotated.right();
        core.canBeRotated.right();
        core.canBeRotated.right();
        core.canBeRotated.right();
        assertThat(degreesRotation, is(0.0f));
    }


}
