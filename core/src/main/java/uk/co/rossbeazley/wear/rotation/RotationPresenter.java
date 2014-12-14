package uk.co.rossbeazley.wear.rotation;

import uk.co.rossbeazley.wear.CanBeObserved;

public class RotationPresenter {
    public RotationPresenter(final RotationView view, CanBeObserved<CanReceiveRotationUpdates> canBeObservedForChangesToRotation) {
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
