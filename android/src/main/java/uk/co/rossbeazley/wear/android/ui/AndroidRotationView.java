package uk.co.rossbeazley.wear.android.ui;

import android.view.View;



import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.android.R;
import uk.co.rossbeazley.wear.rotation.RotationPresenter;
import uk.co.rossbeazley.wear.ui.Disposable;

class AndroidRotationView implements RotationPresenter.RotationView {
    private final View inflatedViews;

    public AndroidRotationView(View inflatedViews) {
        this.inflatedViews = inflatedViews;
    }

    @Override
    public void rotateToDegrees(final float degreesRotation) {
        final View view = inflatedViews.findViewById(R.id.watch_face_view);
        view.post(new Runnable() {
            @Override
            public void run() {
                view.animate().rotation(degreesRotation);
            }
        });
    }

    public static Disposable createRotationView(Core core, View inflatedViews) {
        AndroidRotationView view = new AndroidRotationView(inflatedViews);
        return new RotationPresenter(view, core.canBeObservedForChangesToRotation);
    }
}
