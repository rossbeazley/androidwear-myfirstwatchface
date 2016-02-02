package uk.co.rossbeazley.wear.android.ui.config;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.android.ui.config.FragmentNavigationController;
import uk.co.rossbeazley.wear.android.ui.config.NavigationController;
import uk.co.rossbeazley.wear.android.ui.config.NeedsNavigationController;

public class ConfigActivity extends Activity implements FragmentNavigationController.FragmentManagerProvider {


    private NavigationController navigationController = new FragmentNavigationController(this, R.id.config_root_view);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_activity);

        new FinishedActivity(this);

//        Debug.waitForDebugger();

        // if not restoring state, do default navigation action
        if(savedInstanceState==null) {
            navigationController.defaultNavigation();
        }
    }


    @Override
    public void onAttachFragment(Fragment fragment) {
        if(fragment instanceof NeedsNavigationController) {
            ((NeedsNavigationController)fragment).attachNavigationController(navigationController);
        }
    }

    private class FinishedActivity {

        private final Activity activity;
        private final FragmentManager fragmentManager;

        public FinishedActivity(Activity activity) {

            this.activity = activity;
            this.fragmentManager = activity.getFragmentManager();

            finishTheActivityWhenFragmentBackstackIsEmpty();
        }

        private void finishTheActivityWhenFragmentBackstackIsEmpty() {

            fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    if(fragmentManager.getBackStackEntryCount() < 1) {
                        activity.finish();
                        fragmentManager.removeOnBackStackChangedListener(this);
                    }
                }
            });
        }
    }
}
