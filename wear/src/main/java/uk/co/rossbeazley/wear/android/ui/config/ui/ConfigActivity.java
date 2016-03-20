package uk.co.rossbeazley.wear.android.ui.config.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.android.ui.config.HashMapPersistence;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;
import uk.co.rossbeazley.wear.android.ui.config.service.StringPersistence;

public class ConfigActivity extends Activity implements FragmentNavigationController.FragmentManagerProvider {


    private NavigationController navigationController = new FragmentNavigationController(this, R.id.config_root_view);
    private DependencyInjectionFramework dependencyInjectionFramework = new DependencyInjectionFramework();

    private UiNavigation uiNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_activity);

//        Debug.waitForDebugger();

        ConfigService configService = buildConfigService(new HashMapPersistence());
        dependencyInjectionFramework.register(configService, NeedsConfigService.class);

        dependencyInjectionFramework.register(navigationController, NeedsNavigationController.class);
        uiNavigation = new UiNavigation(configService, navigationController);

    }

    @NonNull
    private static ConfigService buildConfigService(StringPersistence persistence) {
        ConfigService configService = new ConfigService(persistence);

        ConfigItem[] options = {
                new ConfigItem("Background")
                        .addOptions("Black", "White")
                        .defaultOption("White"),

                new ConfigItem("Rotation")
                        .addOptions("North", "East", "South", "West")
                        .defaultOption("North"),

                new ConfigItem("12/24 Hour")
                        .addOptions("Twelve", "Twenty Four", "Twelve Padded")
                        .defaultOption("Twelve Padded")
        };
        configService.initialiseDefaults(options);

        return configService;
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
