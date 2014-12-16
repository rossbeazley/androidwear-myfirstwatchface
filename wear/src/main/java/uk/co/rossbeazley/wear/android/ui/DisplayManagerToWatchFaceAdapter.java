package uk.co.rossbeazley.wear.android.ui;

import android.hardware.display.DisplayManager;
import android.view.Display;

public class DisplayManagerToWatchFaceAdapter implements DisplayManager.DisplayListener {
    private final WatchFaceUINavigation watchFaceUINavigation;
    private final DisplayManager displayManager;

    public DisplayManagerToWatchFaceAdapter(WatchFaceUINavigation watchFaceUINavigation, DisplayManager displayManager) {
        this.watchFaceUINavigation = watchFaceUINavigation;
        this.displayManager = displayManager;
        this.displayManager.registerDisplayListener(this, null);
    }

    @Override
    public void onDisplayRemoved(int displayId) {
        this.watchFaceUINavigation.removed();
    }

    @Override
    public void onDisplayAdded(int displayId) {
        //  In testing, this was never called, so the callback for this was removed.
    }

    @Override
    public void onDisplayChanged(int displayId) {
        switch(this.displayManager.getDisplay(displayId).getState()){
            case Display.STATE_DOZING:
                this.watchFaceUINavigation.screenDim();
                break;
            case Display.STATE_OFF:
                this.watchFaceUINavigation.screenOff();
                break;
            default:
                //  Not really sure what to so about Display.STATE_UNKNOWN, so
                //  we'll treat it as if the screen is normal.
                this.watchFaceUINavigation.screenAwake();
                break;
        }
    }

    public void tearDown() {
        this.displayManager.unregisterDisplayListener(this);
    }
}
