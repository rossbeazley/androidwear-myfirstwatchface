package uk.co.rossbeazley.wear.android.ui.config;

import android.app.Fragment;
import android.app.FragmentManager;

import uk.co.rossbeazley.wear.ui.config.NavigationController;

public class FragmentNavigationController implements NavigationController {
    private FragmentManagerProvider fragmentManagerProvider;
    private int config_root_view;

    public FragmentNavigationController(FragmentManagerProvider fragmentManagerProvider, int root_view) {
        this.fragmentManagerProvider = fragmentManagerProvider;
        config_root_view = root_view;
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
        UIFactoryFragment fragment = ConfigItemsListFragment.createConfigItemsListFragment();
        pushFragment(fragment, ConfigItemsListFragment.tag());
    }

    @Override
    public void toConfigOption() {
        ConfigItemOptionsListFragment fragment = ConfigItemOptionsListFragment.createConfigItemOptionsListFragment();
        pushFragment(fragment, fragment.tag());
    }

    @Override
    public void toConfigOptionSelected() {
        ConfigOptionSelectedFragment fragment = new ConfigOptionSelectedFragment();
        pushFragment(fragment,fragment.tag());
    }

    private FragmentManager fragmentManager() {
        return fragmentManagerProvider.getFragmentManager();
    }

    public static interface FragmentManagerProvider {
        FragmentManager getFragmentManager();
    }
}
