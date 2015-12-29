package uk.co.rossbeazley.wear.android.ui;

import java.util.Calendar;

public interface WatchView {
    void toAmbient();

    void toActive();

    void toActiveOffset();

    void toInvisible();

    void registerInvalidator(RedrawOnInvalidate redrawOnInvalidate);

    void timeTick(Calendar instance);

    interface RedrawOnInvalidate {
        void postInvalidate();
    }
}
