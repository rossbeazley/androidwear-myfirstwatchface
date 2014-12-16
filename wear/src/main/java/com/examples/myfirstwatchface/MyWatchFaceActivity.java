package com.examples.myfirstwatchface;

import android.app.Activity;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;

import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.android.ui.DisplayManagerToWatchFaceAdapter;
import uk.co.rossbeazley.wear.android.ui.FragmentTransactionWatchFace;


public class MyWatchFaceActivity extends Activity {

    private FragmentTransactionWatchFace watchFace;
    private DisplayManagerToWatchFaceAdapter displayListener;
    private DisplayManager displayManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Set up the display manager and register a listener (this activity).
        displayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        displayListener = new DisplayManagerToWatchFaceAdapter(watchFace,displayManager);
        displayManager.registerDisplayListener(displayListener, null);
        watchFace = new FragmentTransactionWatchFace(getFragmentManager());

        setContentView(R.layout.activity_my_watch_face);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  Unregister the listener. If you don't, even after the watch face is gone,
        //  it will still accept your callbacks.
        displayManager.unregisterDisplayListener(displayListener);
    }


}
