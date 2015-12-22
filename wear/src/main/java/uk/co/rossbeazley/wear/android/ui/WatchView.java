package uk.co.rossbeazley.wear.android.ui;

public interface WatchView {
    void toAmbient();

    void toActive();

    void toOffsetView();

    void toInvisible();

    void registerInvalidator(RedrawOnInvalidate redrawOnInvalidate);

    interface RedrawOnInvalidate {
        void postInvalidate();
    }
}
