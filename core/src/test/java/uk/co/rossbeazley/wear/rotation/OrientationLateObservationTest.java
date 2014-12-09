package uk.co.rossbeazley.wear.rotation;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.Core;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OrientationLateObservationTest {

    private float degreesRotation;

    @Test
    public void theOneWhereWeAreToldOrientationIsSouth() {
        Core core = new Core(Orientation.south());
        CanBeObservedForChangesToRotation rotation = core.canBeObservedForChangesToRotation;
        rotation.observe(new CanBeObservedForChangesToRotation.CanReceiveRotationUpdates() {
            @Override
            public void rotationUpdate(Orientation to) {
                degreesRotation = to.degrees();
            }
        });

        assertThat(degreesRotation, is(180.0f));
    }

}
