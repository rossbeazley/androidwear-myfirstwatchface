package uk.co.rossbeazley.wear.android.ui.config.ui;

import android.app.FragmentManager;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.co.rossbeazley.wear.android.ui.config.TestActivity;
import uk.co.rossbeazley.wear.android.ui.config.TestWorld;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class FragmentNavigationControllerTest {

    @Rule
    public ActivityTestRule<TestActivity> activityTestRule = new ActivityTestRule<>(TestActivity.class);
    private TestActivity testActivity;
    private FragmentNavigationController fragmentNavigationController;
    private TestWorld testWorld;

    @Before
    public void createTestWorld() {
        testActivity = activityTestRule.getActivity();

        fragmentNavigationController = new FragmentNavigationController(new FragmentNavigationController.FragmentManagerProvider() {
            @Override
            public FragmentManager getFragmentManager() {
                return testActivity.getFragmentManager();
            }
        }, testActivity.rootFrameLayout.getId());

        testActivity.dependencyInjectionFramework.register(new NavigationControllerJournal(), NeedsNavigationController.class);
        testWorld = new TestWorld();
        testActivity.dependencyInjectionFramework.register(testWorld.build(), NeedsConfigService.class);
    }

    @Test
    public void navigateToConfigItemsList() throws Exception {
        fragmentNavigationController.toConfigItemsList();
        SystemClock.sleep(1000);
        assertThat(testActivity.fragment, is(instanceOf(ConfigItemsListFragment.class)));
    }

    @Test
    public void navigateToConfigOption() throws Exception {
        testWorld.configService.configureItem(testWorld.anyItemID());
        fragmentNavigationController.toConfigOption();
        SystemClock.sleep(1000);
        assertThat(testActivity.fragment, is(instanceOf(ConfigItemOptionsListFragment.class)));
    }

    @Test
    public void navigateToConfigOptionSelected() throws Exception {
        fragmentNavigationController.toConfigOptionSelected();
        SystemClock.sleep(1000);
        assertThat(testActivity.fragment, is(instanceOf(ConfigOptionSelectedFragment.class)));
    }

}