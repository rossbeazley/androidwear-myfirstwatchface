package uk.co.rossbeazley.wear.android.ui;

import android.app.Activity;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Debug;
import android.view.ViewGroup;

import uk.co.rossbeazley.wear.*;

public class WatchActivity extends Activity {

    public DisplayManagerToWatchFaceAdapter displayListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_watch_face);
        //FragmentTransactionWatchFaceUINavigation watchFace = new FragmentTransactionWatchFaceUINavigation(getFragmentManager());
        //ViewGroupReplaceWatchFaceUINavigation watchFace = new ViewGroupReplaceWatchFaceUINavigation((ViewGroup) findViewById(R.id.watch_container));

        //DisplayManager displayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        //displayListener = new DisplayManagerToWatchFaceAdapter(watchFace, displayManager);

        //watchFace.defaultState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //displayListener.tearDown();
        //displayListener = null;
    }
}
