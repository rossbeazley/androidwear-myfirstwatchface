package uk.co.rossbeazley.wear.rotation;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.ui.Disposable;

public class RotationPresenter implements Disposable {
    private final CanBeObserved<CanReceiveRotationUpdates> canBeObservedForChangesToRotation;
    public final CanReceiveRotationUpdates updateView;

    public RotationPresenter(final RotationView view, CanBeObserved<CanReceiveRotationUpdates> canBeObservedForChangesToRotation) {
        this.canBeObservedForChangesToRotation = canBeObservedForChangesToRotation;
        CanReceiveRotationUpdates updateView;
        this.updateView = new CanReceiveRotationUpdates() {
            @Override
            public void rotationUpdate(Orientation to) {
                view.rotateToDegrees(to.degrees());
            }
        };
        updateView = this.updateView;
        canBeObservedForChangesToRotation.addListener(updateView);
    }

    @Override
    public void dispose() {
        canBeObservedForChangesToRotation.removeListener(updateView);
    }

    public static interface RotationView {
        void rotateToDegrees(float degreesRotation);
    }
}
