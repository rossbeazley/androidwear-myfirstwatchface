package uk.co.rossbeazley.wear.android.ui.config;

import android.support.test.espresso.Espresso;
//import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.LayoutInflater;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.android.ui.espressoMatchers.ScrollToPositionViewAction;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static uk.co.rossbeazley.wear.android.ui.espressoMatchers.DepthFirstChildCount.hasNumberOfChildrenMatching;


@RunWith(AndroidJUnit4.class)
public class ConfigOptionsListWearViewTest {
    private ConfigItemsListWearView configOptionsListWearView;

    @Rule
    public ActivityTestRule<TestActivity> activityTestRule = new ActivityTestRule<>(TestActivity.class);

    @Rule
    public UiThreadTestRule uiThreadTest = new UiThreadTestRule();

    @Before
    public void startTestActivityWithUIUnderTestrrs() throws Throwable {
        TestActivity activity = activityTestRule.getActivity();
        createUI(activity);
    }

    @Test
    public void showsOneConfigOption() throws Throwable {
        configOptionsListWearView.showConfigItems(Collections.singletonList("ANY"));
        Espresso.onView(withId(R.id.view_under_test))
                .check(hasNumberOfChildrenMatching(1, withText("ANY")));
    }

    @Test
    public void showsTwoConfigOption() throws Throwable {
        configOptionsListWearView.showConfigItems(Arrays.asList("ANY", "ANY"));
        Espresso.onView(withId(R.id.view_under_test))
                .check(hasNumberOfChildrenMatching(2, withText("ANY")));
    }
    @Test
    public void showsTwoDifferentConfigOption() throws Throwable {
        configOptionsListWearView.showConfigItems(Arrays.asList("ANY", "OLD"));
        Espresso.onView(withId(R.id.view_under_test))
                .check(hasNumberOfChildrenMatching(1, withText("ANY")))
                .check(hasNumberOfChildrenMatching(1, withText("OLD")));
    }


    @Test
    public void showslOTSDifferentConfigOption() throws Throwable {
        List<String> list = new ArrayList<>(6);
        list.add("ANY1");
        list.add("ANY2");
        list.add("ANY3");
        list.add("ANY4");
        list.add("ANY5");
        list.add("ANY6");

        configOptionsListWearView.showConfigItems(list);
        Espresso.onView(withId(R.id.wearable_list))
                .perform(ScrollToPositionViewAction.scrollWearableListToPosition(4))
                .check(hasNumberOfChildrenMatching(1, withText("ANY4")));
    }


    @Test
    public void theOneWhereWeSelectAnItem() {
        configOptionsListWearView.showConfigItems(Arrays.asList("ANY1", "ANY2", "ANY3"));
        CapturingListener capturingListener = new CapturingListener();
        configOptionsListWearView.addListener(capturingListener);
        Espresso.onView(withText("ANY1"))
                .perform(ViewActions.click());

        assertThat(capturingListener.itemSelected, is("ANY1"));
    }

    @Test
    public void theOneWhereWeSelectTheSecondItem() {
        configOptionsListWearView.showConfigItems(Arrays.asList("ANY1", "ANY2", "ANY3"));


        CapturingListener capturingListener = new CapturingListener();
        configOptionsListWearView.addListener(capturingListener);

        Espresso.onView(withId(R.id.wearable_list))
                .perform(ScrollToPositionViewAction.scrollWearableListToPosition(1));

        Espresso.onView(withText("ANY2"))
                .perform(ViewActions.click());

        assertThat(capturingListener.itemSelected, is("ANY2"));
    }


    private void createUI(final TestActivity activity) throws Throwable {
        uiThreadTest.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ConfigItemsListFragment configItemsListFragment = new ConfigItemsListFragment();
                configOptionsListWearView = (ConfigItemsListWearView) configItemsListFragment.onCreateView(LayoutInflater.from(activity),activity.rootFrameLayout,null);
                configOptionsListWearView.setId(R.id.view_under_test);
                activity.setContentView(configOptionsListWearView);
            }
        });
    }


    private static class CapturingListener implements ConfigItemsListView.Listener {
        public String itemSelected;

        @Override
        public void itemSelected(String two) {
            itemSelected = two;
        }
    }

}
