package uk.co.rossbeazley.wear.android.ui.config.ui;

import android.app.Fragment;
import android.app.FragmentManager;

public class FragmentNavigationController implements NavigationController {
    private FragmentManagerProvider fragmentManagerProvider;
    private int config_root_view;

    public FragmentNavigationController(FragmentManagerProvider fragmentManagerProvider, int root_view) {
        this.fragmentManagerProvider = fragmentManagerProvider;
        config_root_view = root_view;
    }

    @Override
    public void defaultNavigation() {
        UIConfigFragment fragment = new UIConfigFragment();
        String tag = "DEFAULT";
        pushFragment(fragment, tag);
    }

    private void pushFragment(Fragment fragment, String tag) {
        fragmentManager()
                .beginTransaction()
                .replace(config_root_view, fragment, tag)
//                .addToBackStack(tag)
//                  .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void toConfigItemsList() {
        ConfigItemsListFragment fragment = new ConfigItemsListFragment();
        pushFragment(fragment, fragment.tag());
    }

    @Override
    public void toConfigOption() {
        ConfigItemOptionsListFragment fragment = new ConfigItemOptionsListFragment();
        pushFragment(fragment, fragment.tag());
    }

    @Override
    public void toConfigOptionSelected() {
        ConfigOptionSelectedFragment fragment = new ConfigOptionSelectedFragment();
        pushFragment(fragment,fragment.tag());
//        fragmentManager().popBackStack(ConfigItemsListFragment.tag(), 0);
    }

    private FragmentManager fragmentManager() {
        return fragmentManagerProvider.getFragmentManager();
    }

    public static interface FragmentManagerProvider {
        FragmentManager getFragmentManager();
    }
}
