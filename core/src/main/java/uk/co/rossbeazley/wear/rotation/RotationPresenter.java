package uk.co.rossbeazley.wear.rotation;

public class RotationPresenter {
    public RotationPresenter(final RotationView view, CanBeObservedForChangesToRotation canBeObservedForChangesToRotation) {
        CanBeObservedForChangesToRotation.CanReceiveRotationUpdates updateView;
        updateView = new CanBeObservedForChangesToRotation.CanReceiveRotationUpdates() {
            @Override
            public void rotationUpdate(Orientation to) {
                view.rotateToDegrees(to.degrees());
            }
        };
        canBeObservedForChangesToRotation.observe(updateView);
    }

    public static interface RotationView {
        void rotateToDegrees(float degreesRotation);
    }
}
