package uk.co.rossbeazley.wear.android.ui;

import android.view.View;



import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.android.R;
import uk.co.rossbeazley.wear.rotation.RotationPresenter;

class AndroidRotationView implements RotationPresenter.RotationView {
    private final View inflatedViews;

    public AndroidRotationView(View inflatedViews) {
        this.inflatedViews = inflatedViews;
    }

    @Override
    public void rotateToDegrees(final float degreesRotation) {
        final View view = inflatedViews.findViewById(R.id.watch_face_view);
//                ObjectAnimator.ofFloat(view,"rotation",view.getRotation(),degreesRotation)
//                        .setDuration(1000)
//                        .start();
        view.post(new Runnable() {
            @Override
            public void run() {
                view.setRotation(degreesRotation);
            }
        });
    }

    public static void createRotationView(Core core, View inflatedViews) {
        AndroidRotationView view = new AndroidRotationView(inflatedViews);
        new RotationPresenter(view, core.canBeObservedForChangesToRotation);
    }
}
