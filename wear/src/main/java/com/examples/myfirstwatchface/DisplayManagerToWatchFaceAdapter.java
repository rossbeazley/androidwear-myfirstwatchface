package com.examples.myfirstwatchface;

import android.hardware.display.DisplayManager;
import android.view.Display;

import uk.co.rossbeazley.wear.android.ui.WatchFace;

class DisplayManagerToWatchFaceAdapter implements DisplayManager.DisplayListener {
    private final WatchFace watchFace;
    private final DisplayManager displayManager;

    public DisplayManagerToWatchFaceAdapter(WatchFace watchFace, DisplayManager displayManager) {
        this.watchFace = watchFace;
        this.displayManager = displayManager;
    }

    @Override
    public void onDisplayRemoved(int displayId) {
        this.watchFace.removed();
    }

    @Override
    public void onDisplayAdded(int displayId) {
        //  In testing, this was never called, so the callback for this was removed.
    }

    @Override
    public void onDisplayChanged(int displayId) {
        switch(this.displayManager.getDisplay(displayId).getState()){
            case Display.STATE_DOZING:
                this.watchFace.screenDim();
                break;
            case Display.STATE_OFF:
                this.watchFace.screenOff();
                break;
            default:
                //  Not really sure what to so about Display.STATE_UNKNOWN, so
                //  we'll treat it as if the screen is normal.
                this.watchFace.screenAwake();
                break;
        }
    }
}
