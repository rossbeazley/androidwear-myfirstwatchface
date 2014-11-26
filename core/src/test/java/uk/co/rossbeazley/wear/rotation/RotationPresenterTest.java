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

        canBeObservedForChangesToRotation.canReceiveRotationUpdates.rotationUpdate(new Rotation(0.0f));

        assertThat(view.degreesRotation, is(0.0f));
    }

    interface CanBeObservedForChangesToRotation {
        void observe(CanReceiveRotationUpdates canReceiveRotationUpdates);

        interface CanReceiveRotationUpdates {
            void rotationUpdate(Rotation to);
        }
    }

    interface RotationView {
        void rotateToDegrees(float degreesRotation);
    }

    private static class Rotation {

        private float degrees;

        private Rotation(float degrees) {
            this.degrees = degrees;
        }

        public float degrees() {
            return degrees;
        }
    }

    private static class RotationChanges implements CanBeObservedForChangesToRotation {
        private CanReceiveRotationUpdates canReceiveRotationUpdates;

        @Override
        public void observe(CanReceiveRotationUpdates canReceiveRotationUpdates) {
            this.canReceiveRotationUpdates = canReceiveRotationUpdates;
        }
    }

    class FakeRotationView implements RotationView {
        private float degreesRotation;

        @Override
        public void rotateToDegrees(float degreesRotation) {
            this.degreesRotation = degreesRotation;
        }
    }



    private class RotationPresenter {
        public RotationPresenter(final FakeRotationView view, CanBeObservedForChangesToRotation canBeObservedForChangesToRotation) {
            canBeObservedForChangesToRotation.observe(new CanBeObservedForChangesToRotation.CanReceiveRotationUpdates() {
                @Override
                public void rotationUpdate(Rotation to) {
                    view.rotateToDegrees(to.degrees());
                }
            });
        }
    }
}
