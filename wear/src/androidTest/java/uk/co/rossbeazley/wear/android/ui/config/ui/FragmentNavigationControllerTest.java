package uk.co.rossbeazley.wear.android.ui.config.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.test.rule.ActivityTestRule;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import uk.co.rossbeazley.wear.android.ui.config.TestActivity;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class FragmentNavigationControllerTest {

    @Rule
    public ActivityTestRule<TestActivity> activityTestRule = new ActivityTestRule<>(TestActivity.class);
    private TestActivity testActivity;
    private FragmentNavigationController fragmentNavigationController;

    @Before
    public void createTestWorld() {
        testActivity = activityTestRule.getActivity();

        fragmentNavigationController = new FragmentNavigationController(new FragmentNavigationController.FragmentManagerProvider() {
            @Override
            public FragmentManager getFragmentManager() {
                return testActivity.getFragmentManager();
            }
        }, testActivity.rootFrameLayout.getId());
    }

    @Test
    public void navigateToConfigItemsList() throws Exception {
        fragmentNavigationController.toConfigItemsList();
        SystemClock.sleep(1000);
        assertThat(testActivity.fragment, is(instanceOf(ConfigItemsListFragment.class)));
    }

    @Test @Ignore
    public void navigateToConfigOption() throws Exception {

    }

    @Test @Ignore
    public void navigateToConfigOptionSelected() throws Exception {

    }
}