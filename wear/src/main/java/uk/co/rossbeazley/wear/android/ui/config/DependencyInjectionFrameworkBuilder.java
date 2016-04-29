package uk.co.rossbeazley.wear.android.ui.config;

import uk.co.rossbeazley.wear.config.ConfigService;
import uk.co.rossbeazley.wear.ui.config.NavigationController;
import uk.co.rossbeazley.wear.ui.config.UIEvents;

public class DependencyInjectionFrameworkBuilder {

    public DependencyInjectionFramework withDefaults(NavigationController registeredNavController, UIEvents registeredUiEvents, ConfigService registeredConfigService) {
        DependencyInjectionFramework dependencyInjectionFramework = new DependencyInjectionFramework();
        dependencyInjectionFramework.register(registeredNavController, NeedsNavigationController.class);
        dependencyInjectionFramework.register(registeredUiEvents,RaisesUIEvents.class);
        dependencyInjectionFramework.register(registeredConfigService,NeedsConfigService.class);
        return dependencyInjectionFramework;
    }
}
