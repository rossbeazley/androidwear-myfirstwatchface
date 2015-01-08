package uk.co.rossbeazley.wear.android.ui;

import android.app.Activity;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Debug;
import android.view.ViewGroup;

import uk.co.rossbeazley.wear.*;

public class WatchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_watch_face);
    }
}
