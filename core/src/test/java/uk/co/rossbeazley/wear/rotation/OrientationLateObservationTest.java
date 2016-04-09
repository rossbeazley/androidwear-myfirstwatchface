package uk.co.rossbeazley.wear.rotation;

import org.junit.Test;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.Core;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OrientationLateObservationTest {

    private float degreesRotation;

    @Test
    public void theOneWhereWeAreToldOrientationIsSouth() {
        Core core = new Core(Orientation.south());
        core.canBeRotated.to(Orientation.south());
        CanBeObserved<CanReceiveRotationUpdates> rotation = core.canBeObservedForChangesToRotation;
        rotation.addListener(new CanReceiveRotationUpdates() {
            @Override
            public void rotationUpdate(Orientation to) {
                degreesRotation = to.degrees();
            }
        });

        assertThat(degreesRotation, is(180.0f));
    }

    @Test
    public void weObserveAfterARotation() {
        Core core = new Core(Orientation.south());
        core.canBeRotated.to(Orientation.south());
        CanBeObserved<CanReceiveRotationUpdates> rotation = core.canBeObservedForChangesToRotation;
        core.canBeRotated.right();
        rotation.addListener(new CanReceiveRotationUpdates() {
            @Override
            public void rotationUpdate(Orientation to) {
                degreesRotation = to.degrees();
            }
        });

        assertThat(degreesRotation, is(270.0f));
    }

}
