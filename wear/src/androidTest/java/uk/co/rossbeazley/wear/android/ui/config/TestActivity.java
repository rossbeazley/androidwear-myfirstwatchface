package uk.co.rossbeazley.wear.android.ui.config;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import uk.co.rossbeazley.wear.R;

public class TestActivity extends Activity {

    public FrameLayout rootFrameLayout;
    public Fragment fragment;
    public DependencyInjectionFramework dependencyInjectionFramework;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootFrameLayout = new FrameLayout(this);
        rootFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rootFrameLayout.setId(R.id.test_activity_root_view_id);
        setContentView(rootFrameLayout);


        dependencyInjectionFramework = new DependencyInjectionFramework();

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        this.fragment = fragment;
        dependencyInjectionFramework.inject(fragment);
    }

}
