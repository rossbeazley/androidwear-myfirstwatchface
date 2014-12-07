package uk.co.rossbeazley.wear.rotation;

import org.junit.Ignore;
import org.junit.Test;

import uk.co.rossbeazley.wear.Core;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RotationRehydrationTest {

    private float degreesRotation;
    private Core core;
    private CanBeObservedForChangesToRotation rotation;

    public void createCoreAtRotation(float startRotation) {
        core = new Core();
        rotation = core.canBeObservedForChangesToRotation;
        rotation.observe(new CanBeObservedForChangesToRotation.CanReceiveRotationUpdates() {
            @Override
            public void rotationUpdate(Orientation to) {
                degreesRotation = to.degrees();
            }
        });

    }

    @Test @Ignore("test list")
    public void theOneWhereWeRotateRightToEast() {
        createCoreAtRotation(0.0f);
        core.canBeRotated.right();
        assertThat(degreesRotation, is(90.0f));
    }

    @Test @Ignore("test list")
    public void theOneWhereWeRotateRightToSouth() {
        createCoreAtRotation(90.0f);
        core.canBeRotated.right();
        assertThat(degreesRotation, is(180.0f));
    }


    @Test @Ignore("test list")
    public void theOneWhereWeRotateRightToWest() {
        createCoreAtRotation(180.0f);
        core.canBeRotated.right();
        assertThat(degreesRotation, is(270.0f));
    }


    @Test @Ignore("test list")
    public void theOneWhereWeRotateRightToNorth() {
        createCoreAtRotation(270.0f);
        core.canBeRotated.right();
        assertThat(degreesRotation, is(0.0f));
    }


}
