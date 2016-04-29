package uk.co.rossbeazley.wear.android.ui.config;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.config.ConfigService;
import uk.co.rossbeazley.wear.config.HashMapPersistence;
import uk.co.rossbeazley.wear.ui.config.NavigationController;
import uk.co.rossbeazley.wear.ui.config.UIEvents;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DependencyInjectionFrameworkTest implements NeedsNavigationController, RaisesUIEvents, NeedsConfigService{

    private NavigationController navigationController;
    private UIEvents uiEvents;
    private DependencyInjectionFramework dependencyInjectionFramework;
    private ConfigService configService;
    private NavigationController registeredNavController;
    private UIEvents registeredUiEvents;
    private ConfigService registeredConfigService;

    private DependencyInjectionFrameworkBuilder dependencyInjectionFrameworkBuilder = new DependencyInjectionFrameworkBuilder();

    @Before
    public void setUp() throws Exception {
        registeredNavController = new NavigationControllerJournal();
        registeredUiEvents = new StubUIEvents();
        registeredConfigService = new ConfigService(new HashMapPersistence());

        dependencyInjectionFrameworkBuilder.withDefaults(registeredNavController, registeredUiEvents, registeredConfigService);
    }

    @Test
    public void injectsNavController() throws Exception {
        dependencyInjectionFramework.inject(this);
        assertThat(navigationController,is(registeredNavController));
    }

    @Test
    public void injectsUIEvents() {
        dependencyInjectionFramework.inject(this);
        assertThat(uiEvents, is(registeredUiEvents));
    }

    @Test
    public void injectsConfigService() {
        dependencyInjectionFramework.inject(this);
        assertThat(this.configService, is(registeredConfigService));
    }

    @Override
    public void attachNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

    @Override
    public void injectUIEventsDispatcher(UIEvents uiEvents) {
        this.uiEvents = uiEvents;
    }

    @Override
    public void attachConfigService(ConfigService configService) {
        this.configService = configService;
    }

}