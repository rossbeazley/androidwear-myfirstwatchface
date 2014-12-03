package uk.co.rossbeazley.wear;

import android.app.Activity;
import android.os.Bundle;

import com.examples.myfirstwatchface.R;

public class WatchActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_watch_face);
    }
}
