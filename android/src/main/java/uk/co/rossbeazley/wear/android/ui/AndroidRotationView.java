package uk.co.rossbeazley.wear.android.ui;

import android.view.View;



import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.android.R;
import uk.co.rossbeazley.wear.rotation.RotationPresenter;
import uk.co.rossbeazley.wear.ui.Disposable;

class AndroidRotationView implements RotationPresenter.RotationView {
    public final View view;

    public AndroidRotationView(View inflatedViews) {
        view = inflatedViews;
    }

    @Override
    public void rotateToDegrees(final float degreesRotation) {

//        view.post(new Runnable() {
//            @Override
//            public void run() {
//                view.animate().rotation(degreesRotation);
//            }
//        });
        view.setRotation(degreesRotation);
    }

    public static Disposable createRotationView(Core core, View inflatedViews) {
        AndroidRotationView view = new AndroidRotationView(inflatedViews);
        return new RotationPresenter(view, core.canBeObservedForChangesToRotation);
    }
}
