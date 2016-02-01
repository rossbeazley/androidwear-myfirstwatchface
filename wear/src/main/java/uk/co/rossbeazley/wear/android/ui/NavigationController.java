package uk.co.rossbeazley.wear.android.ui;

import android.app.Fragment;
import android.app.FragmentManager;

class NavigationController {
    private FragmentManager fragmentManager;
    private int config_root_view;

    public NavigationController(FragmentManager fragmentManager, int root_view) {
        this.fragmentManager = fragmentManager;
        config_root_view = root_view;
    }

    public void defaultNavigation() {
        UIConfigFragment fragment = new UIConfigFragment();
        String tag = "DEFAULT";
        pushFragment(fragment, tag);
    }

    private void pushFragment(Fragment fragment, String tag) {
        fragmentManager
                .beginTransaction()
                .replace(config_root_view, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    public void toConfigOptionsList() {
        ConfigOptionsList fragment = new ConfigOptionsList();
        String tag = "CONFIG_OPTIONS";
        pushFragment(fragment, tag);
    }
}
