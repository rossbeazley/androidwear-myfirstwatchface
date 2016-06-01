package uk.co.rossbeazley.wear.android.ui;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.ViewGroup;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.android.ui.config.SelectableItemListView;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.rossbeazley.wear.android.ui.DepthFirstChildCount.hasNumberOfChildrenMatching;

@RunWith(AndroidJUnit4.class)
public class SelectableItemRecyclerViewTest {

    private SelectableItemListView configOptionsWearView;

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
        configOptionsWearView.showItems(Collections.singletonList("ANY"));
        Espresso.onView(withId(R.id.view_under_test))
                .check(hasNumberOfChildrenMatching(1, withText("ANY")));
    }

    @Test
    public void showsTwoConfigOption() throws Throwable {
        configOptionsWearView.showItems(Arrays.asList("ANY", "ANY"));
        Espresso.onView(withId(R.id.view_under_test))
                .check(hasNumberOfChildrenMatching(2, withText("ANY")));
    }

    @Test
    public void showsTwoDifferentConfigOption() throws Throwable {
        configOptionsWearView.showItems(Arrays.asList("ANY", "OLD"));
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

        configOptionsWearView.showItems(list);
        Espresso.onView(withId(R.id.list))
                .perform(RecyclerViewActions.scrollTo(withText("ANY4")));
    }


    @Test
    public void theOneWhereWeSelectAnItem() {
        configOptionsWearView.showItems(Arrays.asList("ANY1", "ANY2", "ANY3"));
        CapturingListener capturingListener = new CapturingListener();
        configOptionsWearView.addListener(capturingListener);
        Espresso.onView(withText("ANY1"))
                .perform(ViewActions.click());

        assertThat(capturingListener.itemSelected, is("ANY1"));
    }

    @Test
    public void theOneWhereWeSelectTheSecondItem() {
        configOptionsWearView.showItems(Arrays.asList("ANY1", "ANY2", "ANY3"));


        CapturingListener capturingListener = new CapturingListener();
        configOptionsWearView.addListener(capturingListener);

        Espresso.onView(withId(R.id.list))
                .perform(RecyclerViewActions.scrollTo(withText("ANY2")));
        Espresso.onView(withText("ANY2"))
                .perform(ViewActions.click());

        assertThat(capturingListener.itemSelected, is("ANY2"));
    }


    private void createUI(final TestActivity activity) throws Throwable {

        uiThreadTest.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SelectableItemRecyclerView view = new SelectableItemRecyclerView(activity.rootView.getContext());
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                view.setId(R.id.view_under_test);
                activity.rootView.addView(view);
                configOptionsWearView = (SelectableItemListView) view;
            }
        });
    }

    private class CapturingListener implements SelectableItemListView.Listener {
        public String itemSelected;

        public void itemSelected(String text) {
            this.itemSelected = text;
        }
    }


}
