package uk.co.rossbeazley.wear.android.ui.config;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.config.ConfigService;
import uk.co.rossbeazley.wear.ui.config.NavigationController;
import uk.co.rossbeazley.wear.ui.config.UiNavigation;

public class ConfigActivity extends Activity implements FragmentNavigationController.FragmentManagerProvider {


    private NavigationController navigationController = new FragmentNavigationController(this, R.id.config_root_view);
    private DependencyInjectionFramework dependencyInjectionFramework;// = new DependencyInjectionFramework();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_activity);
//        Debug.waitForDebugger();
        ConfigService configService = Core.instance().configService;
        UiNavigation uiNavigation = new UiNavigation(configService, navigationController);
        dependencyInjectionFramework = new DependencyInjectionFrameworkBuilder().withDefaults(navigationController, uiNavigation,configService);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        dependencyInjectionFramework.inject(fragment);
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
                    if (fragmentManager.getBackStackEntryCount() < 1) {
                        activity.finish();
                        fragmentManager.removeOnBackStackChangedListener(this);
                    }
                }
            });
        }
    }

}
