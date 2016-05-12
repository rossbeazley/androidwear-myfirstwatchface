package uk.co.rossbeazley.wear.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.android.ui.config.NeedsConfigService;
import uk.co.rossbeazley.wear.config.ConfigService;
import uk.co.rossbeazley.wear.config.HashMapPersistence;

public class TestActivity extends Activity {

    public Fragment fragment;
    private ConfigService registeredConfigService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        registeredConfigService = new ConfigService(new HashMapPersistence());
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        this.fragment = fragment;
        if(this.fragment instanceof NeedsConfigService) {
            ((NeedsConfigService) fragment).attachConfigService(registeredConfigService);
        }
    }

}
