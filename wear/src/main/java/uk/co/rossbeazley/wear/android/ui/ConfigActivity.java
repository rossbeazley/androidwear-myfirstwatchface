package uk.co.rossbeazley.wear.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Debug;

import uk.co.rossbeazley.wear.R;

public class ConfigActivity extends Activity {


    private NavigationController navigationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Debug.waitForDebugger();

        finishTheActivityWhenFeagmentBackstackIsEmpty();

        setContentView(R.layout.config_activity);
        FragmentManager fragmentManager = getFragmentManager();
        navigationController = new NavigationController(fragmentManager, R.id.config_root_view);
        // if not restoring state
        if(savedInstanceState==null) {
            navigationController.defaultNavigation();
        }
    }

    private void finishTheActivityWhenFeagmentBackstackIsEmpty() {
        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(getFragmentManager().getBackStackEntryCount()<1) {
                    finish();
                }
            }
        });
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if(fragment instanceof NeedsNavigationController) {
            ((NeedsNavigationController)fragment).attachNavigationController(navigationController);
        }
    }
}
