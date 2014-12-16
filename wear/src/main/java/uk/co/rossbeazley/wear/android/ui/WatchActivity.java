package uk.co.rossbeazley.wear.android.ui;

import android.app.Activity;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Debug;

import uk.co.rossbeazley.wear.*;

public class WatchActivity extends Activity {

    public DisplayManagerToWatchFaceAdapter displayListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransactionWatchFaceUINavigation watchFace = new FragmentTransactionWatchFaceUINavigation(getFragmentManager());

        DisplayManager displayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        displayListener = new DisplayManagerToWatchFaceAdapter(watchFace, displayManager);

        watchFace.screenAwake();
    }

    @Override
    protected void onStop() {
        super.onStop();
        displayListener.tearDown();
        displayListener = null;
    }
}
