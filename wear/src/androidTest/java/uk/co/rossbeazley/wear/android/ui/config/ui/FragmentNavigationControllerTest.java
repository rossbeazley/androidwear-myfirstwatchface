package uk.co.rossbeazley.wear.android.ui.config.ui;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.co.rossbeazley.wear.android.ui.config.ConfigItemOptionsListFragment;
import uk.co.rossbeazley.wear.android.ui.config.ConfigItemsListFragment;
import uk.co.rossbeazley.wear.android.ui.config.ConfigOptionSelectedFragment;
import uk.co.rossbeazley.wear.android.ui.config.FragmentNavigationController;
import uk.co.rossbeazley.wear.android.ui.config.NavigationControllerJournal;
import uk.co.rossbeazley.wear.android.ui.config.NeedsConfigService;
import uk.co.rossbeazley.wear.android.ui.config.NeedsNavigationController;
import uk.co.rossbeazley.wear.android.ui.config.RaisesUIEvents;
import uk.co.rossbeazley.wear.android.ui.config.TestActivity;
import uk.co.rossbeazley.wear.TestWorld;
import uk.co.rossbeazley.wear.android.ui.config.UIFactory;
import uk.co.rossbeazley.wear.ui.config.UIEvents;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class FragmentNavigationControllerTest {

    @Rule
    public ActivityTestRule<TestActivity> activityTestRule = new ActivityTestRule<>(TestActivity.class);
    private TestActivity testActivity;
    private FragmentNavigationController fragmentNavigationController;
    private TestWorld testWorld;
    private CapturingUIEvents capturingUIEvents;

    @Before
    public void createTestWorld() {
        testActivity = activityTestRule.getActivity();

        fragmentNavigationController = new FragmentNavigationController(new FragmentNavigationController.FragmentManagerProvider() {
            @Override
            public FragmentManager getFragmentManager() {
                return testActivity.getFragmentManager();
            }
        }, testActivity.rootFrameLayout.getId());

        capturingUIEvents = new CapturingUIEvents();
        testActivity.dependencyInjectionFramework.register(capturingUIEvents, RaisesUIEvents.class);
        testActivity.dependencyInjectionFramework.register(new NavigationControllerJournal(), NeedsNavigationController.class);
        testWorld = new TestWorld();
        testActivity.dependencyInjectionFramework.register(testWorld.build(), NeedsConfigService.class);
    }

    @Test
    public void navigateToConfigItemsList() throws Exception {
        fragmentNavigationController.toConfigItemsList();
        SystemClock.sleep(1000);
        assertThat(testActivity.fragment.getArguments(), contains("factory", ConfigItemsListFragment.ConfigItemsListUIFactory.FACTORY));
    }

    private Matcher<? super Bundle> contains(final String key, final UIFactory uiFactory) {
        return new BaseMatcher<Bundle>() {
            @Override
            public boolean matches(Object item) {
                Bundle toSearch = (Bundle) item;
                if (!toSearch.containsKey(key)) {
                    return false;
                }
                return toSearch.getSerializable(key).equals(uiFactory);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Bundle containing " + uiFactory + " at key " + key);
            }
        };
    }

    @Test
    public void navigateToConfigOption() throws Exception {
        testWorld.configService.configureItem(testWorld.anyItemID());
        fragmentNavigationController.toConfigOption();
        SystemClock.sleep(1000);
        assertThat(testActivity.fragment.getArguments(), contains("factory", ConfigItemOptionsListFragment.ConfigItemsOptionsListUIFactory.FACTORY));
    }

    @Test
    public void navigateToConfigOptionSelected() throws Exception {
        fragmentNavigationController.toConfigOptionSelected();
        SystemClock.sleep(1000);
        assertThat(testActivity.fragment, is(instanceOf(ConfigOptionSelectedFragment.class)));
    }

    @Test
    public void configOptionSelectedCompletes() throws Exception {
        fragmentNavigationController.toConfigOptionSelected();
        SystemClock.sleep(2000);
        assertThat(capturingUIEvents.optionSelectedFinished, is(true));
    }

    public static class CapturingUIEvents implements UIEvents {
        private boolean optionSelectedFinished;

        @Override
        public void optionSelectedFinished() {
            this.optionSelectedFinished = true;
        }
    }
}