package com.examples.myfirstwatchface;

import android.app.Activity;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.view.Display;

public class MyWatchFaceActivity extends Activity {

    private final MyWatchFace watchFace = new MyWatchFace();
    private final MyDisplayListener displayListener = new MyDisplayListener(watchFace);
    private DisplayManager displayManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Set up the display manager and register a listener (this activity).
        displayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        displayManager.registerDisplayListener(displayListener, null);

        setContentView(R.layout.activity_my_watch_face);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  Unregister the listener. If you don't, even after the watch face is gone,
        //  it will still accept your callbacks.
        displayManager.unregisterDisplayListener(displayListener);
    }


    private class MyDisplayListener implements DisplayManager.DisplayListener {
        private final WatchFace watchFace;

        public MyDisplayListener(WatchFace watchFace) {
            this.watchFace = watchFace;
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
            switch(displayManager.getDisplay(displayId).getState()){
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

    private class MyWatchFace implements WatchFace {
        @Override
        public void screenDim() {

        }

        @Override
        public void screenAwake() {

        }

        /**
         * Used to detect when a watch face is being removed (switched).<br/>
         * You can either do what you need here, or simply override {@code onDestroy()}.
         */
        @Override
        public void removed(){}

        /**
         * When the screen is OFF due to "Always-On" being disabled.
         */
        @Override
        public void screenOff(){}
    }
}
