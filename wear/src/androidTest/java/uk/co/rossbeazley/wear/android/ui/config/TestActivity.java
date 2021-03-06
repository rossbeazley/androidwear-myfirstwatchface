package uk.co.rossbeazley.wear.android.ui.config;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.config.ConfigService;
import uk.co.rossbeazley.wear.config.HashMapPersistence;

public class TestActivity extends Activity {

    public static final int TEST_ACTIVITY_ROOT_VIEW_ID = R.id.test_activity_root_view_id;
    public FrameLayout rootFrameLayout;
    public Fragment fragment;
    public DependencyInjectionFramework dependencyInjectionFramework;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootFrameLayout = new FrameLayout(this);
        rootFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rootFrameLayout.setId(TEST_ACTIVITY_ROOT_VIEW_ID);
        setContentView(rootFrameLayout);

        NavigationControllerJournal registeredNavController = new NavigationControllerJournal();
        StubUIEvents registeredUiEvents = new StubUIEvents();
        ConfigService registeredConfigService = new ConfigService(new HashMapPersistence());
        dependencyInjectionFramework = new DependencyInjectionFrameworkBuilder().withDefaults(registeredNavController, registeredUiEvents, registeredConfigService);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        this.fragment = fragment;
        dependencyInjectionFramework.inject(fragment);
    }

}
