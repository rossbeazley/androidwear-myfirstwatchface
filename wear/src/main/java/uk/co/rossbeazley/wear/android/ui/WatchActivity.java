package uk.co.rossbeazley.wear.android.ui;

import android.app.Activity;
import android.os.Bundle;

import uk.co.rossbeazley.wear.*;

public class WatchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_watch_face);
        //createFragmentNavigationFramework();
    }

    private void createFragmentNavigationFramework() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.layout.activity_my_watch_face, new WatchFaceViewActive());
    }
}
