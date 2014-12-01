package uk.co.rossbeazley.wear.rotation;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RotationPresenterTest {


    @Test
    public void theOneWhereWeRotateNorth() {

        FakeRotationView view = new FakeRotationView();
        RotationChanges canBeObservedForChangesToRotation = new RotationChanges();
        new RotationPresenter(view, canBeObservedForChangesToRotation);

        canBeObservedForChangesToRotation.canReceiveRotationUpdates.rotationUpdate(Rotation.north());

        assertThat(view.degreesRotation, is(0.0f));
    }

    private static class RotationChanges implements CanBeObservedForChangesToRotation {
        private CanReceiveRotationUpdates canReceiveRotationUpdates;

        @Override
        public void observe(CanReceiveRotationUpdates canReceiveRotationUpdates) {
            this.canReceiveRotationUpdates = canReceiveRotationUpdates;
        }
    }

    class FakeRotationView implements RotationPresenter.RotationView {
        private float degreesRotation;

        @Override
        public void rotateToDegrees(float degreesRotation) {
            this.degreesRotation = degreesRotation;
        }
    }


}
