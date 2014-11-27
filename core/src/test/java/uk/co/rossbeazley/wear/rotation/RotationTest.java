package uk.co.rossbeazley.wear.rotation;

import org.junit.Ignore;
import org.junit.Test;

import uk.co.rossbeazley.wear.Main;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RotationTest {

    private float degreesRotation;

    @Test
    public void theOneWhereWeRotateRightToEast() {

        Main.Core core = new Main.Core();

        CanBeObservedForChangesToRotation rotation = core.canBeObservedForChangesToRotation;
        rotation.observe(new CanBeObservedForChangesToRotation.CanReceiveRotationUpdates() {
            @Override
            public void rotationUpdate(Rotation to) {
                degreesRotation = to.degrees();
            }
        });

        core.canBeRotated.right();

        assertThat(degreesRotation, is(90.0f));
    }

    @Test @Ignore("test list")
    public void theOneWhereWeRotateRightToSouth() {

    }


    @Test @Ignore("test list")
    public void theOneWhereWeRotateRightToWest() {

    }


    @Test @Ignore("test list")
    public void theOneWhereWeRotateRightToNorth() {

    }


}
