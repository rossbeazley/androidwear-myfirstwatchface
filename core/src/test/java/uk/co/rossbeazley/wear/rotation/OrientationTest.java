package uk.co.rossbeazley.wear.rotation;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.Core;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OrientationTest {

    private float degreesRotation;
    private Core core;
    private CanBeObservedForChangesToRotation<CanReceiveRotationUpdates> rotation;

    @Before
    public void setUp() throws Exception {
        core = new Core();
        rotation = core.canBeObservedForChangesToRotation;
        rotation.addListener(new CanReceiveRotationUpdates() {
            @Override
            public void rotationUpdate(Orientation to) {
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
