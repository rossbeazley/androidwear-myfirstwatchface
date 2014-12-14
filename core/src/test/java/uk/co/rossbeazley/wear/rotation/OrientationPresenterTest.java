package uk.co.rossbeazley.wear.rotation;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OrientationPresenterTest {


    @Test
    public void theOneWhereWeRotateNorth() {

        FakeRotationView view = new FakeRotationView();
        RotationChanges canBeObservedForChangesToRotation = new RotationChanges();
        new RotationPresenter(view, canBeObservedForChangesToRotation);

        canBeObservedForChangesToRotation.canReceiveRotationUpdates.rotationUpdate(Orientation.north());

        assertThat(view.degreesRotation, is(0.0f));
    }

    private static class RotationChanges implements CanBeObservedForChangesToRotation<CanReceiveRotationUpdates> {
        private CanReceiveRotationUpdates canReceiveRotationUpdates;

        @Override
        public void addListener(CanReceiveRotationUpdates canReceiveRotationUpdates) {
            this.canReceiveRotationUpdates = canReceiveRotationUpdates;
        }

        @Override
        public void removeListener(CanReceiveRotationUpdates canReceiveRotationUpdates) {

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
