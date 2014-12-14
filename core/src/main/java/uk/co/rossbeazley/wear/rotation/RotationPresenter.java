package uk.co.rossbeazley.wear.rotation;

public class RotationPresenter {
    public RotationPresenter(final RotationView view, CanBeObservedForChangesToRotation canBeObservedForChangesToRotation) {
        CanReceiveRotationUpdates updateView;
        updateView = new CanReceiveRotationUpdates() {
            @Override
            public void rotationUpdate(Orientation to) {
                view.rotateToDegrees(to.degrees());
            }
        };
        canBeObservedForChangesToRotation.addListener(updateView);
    }

    public static interface RotationView {
        void rotateToDegrees(float degreesRotation);
    }
}
