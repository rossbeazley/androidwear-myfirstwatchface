package uk.co.rossbeazley.wear.android.ui.config.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.android.ui.config.HashMapPersistence;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

public class ConfigActivity extends Activity implements FragmentNavigationController.FragmentManagerProvider {


    private NavigationController navigationController = new FragmentNavigationController(this, R.id.config_root_view);
    private DependencyInjectionFramework dependencyInjectionFramework = new DependencyInjectionFramework();

    private UiNavigation uiNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_activity);

//        new FinishedActivity(this);

//        Debug.waitForDebugger();

        // if not restoring state, do default navigation action
//        if(savedInstanceState==null) {
//            navigationController.defaultNavigation();
//        }



        {
            dependencyInjectionFramework.register(navigationController, NeedsNavigationController.class);
            ConfigService configService = new ConfigService(new HashMapPersistence());

            final ConfigItem option1 = new ConfigItem("one");
            option1.addOptions("oneOne", "oneone", "oneThree", "oneFour");
            option1.defaultOption("oneOne");

            final ConfigItem option2 = new ConfigItem("two");
            option2.addOptions("twoOne", "twoTwo", "twoThree", "twoFour");
            option2.defaultOption("twoOne");

            final ConfigItem option3 = new ConfigItem("three");
            option3.addOptions("threeOne", "threethree", "threeThree", "threeFour");
            option3.defaultOption("threeOne");
            configService.initialiseDefaults(option1,option2,option3);
            dependencyInjectionFramework.register(configService, NeedsConfigService.class);


            uiNavigation = new UiNavigation(configService, navigationController);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Exception("TRACER BULLET").printStackTrace();
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
                    if(fragmentManager.getBackStackEntryCount() < 1) {
                        activity.finish();
                        fragmentManager.removeOnBackStackChangedListener(this);
                    }
                }
            });
        }
    }

}
