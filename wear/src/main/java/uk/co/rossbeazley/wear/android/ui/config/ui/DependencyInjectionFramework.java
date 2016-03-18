package uk.co.rossbeazley.wear.android.ui.config.ui;

import java.util.HashMap;
import java.util.Map;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

public class DependencyInjectionFramework {
        private Map<Class, Object> map = new HashMap<>();

    public void register(Object navigationController, Class needsNavigationControllerClass) {

        map.put(needsNavigationControllerClass,navigationController);
    }

    public void inject(Object fragment) {
        if(fragment instanceof NeedsNavigationController) {
            NeedsNavigationController.class.cast(fragment).attachNavigationController((NavigationController) map.get(NeedsNavigationController.class));
        } else if(fragment instanceof NeedsConfigService) {
            NeedsConfigService.class.cast(fragment).attachConfigService((ConfigService) map.get(NeedsConfigService.class));
        }
    }
}
