package uk.co.rossbeazley.wear.android.ui.config;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
//import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.wear.R;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static uk.co.rossbeazley.wear.android.ui.espressoMatchers.DepthFirstChildCount.hasNumberOfChildrenMatching;


@RunWith(AndroidJUnit4.class)
public class ConfigOptionsListWearViewTest {
    private ConfigOptionsListWearView configOptionsListWearView;

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
                .check(hasNumberOfChildrenMatching(1, withText("ANY")))
        ;

    }

    @Test
    public void showsTwoConfigOption() throws Throwable {

        configOptionsListWearView.showConfigItems(Arrays.asList("ANY", "ANY"));

        Espresso.onView(withId(R.id.view_under_test))
                .check(hasNumberOfChildrenMatching(2, withText("ANY")))
        ;


    }
    @Test
    public void showsTwoDifferentConfigOption() throws Throwable {

        configOptionsListWearView.showConfigItems(Arrays.asList("ANY", "OLD"));

        Espresso.onView(withId(R.id.view_under_test))
                .check(hasNumberOfChildrenMatching(1, withText("ANY")))
                .check(hasNumberOfChildrenMatching(1, withText("OLD")))
        ;

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
                .perform(scrollWearableListToPosition(4))
                .check(hasNumberOfChildrenMatching(1, withText("ANY4")))
        ;

    }


    @Test
    public void theOneWhereWeSelectAnItem() {
        configOptionsListWearView.showConfigItems(Arrays.asList("ANY1", "ANY2", "ANY3"));


        CapturingListener capturingListener = new CapturingListener();
        configOptionsListWearView.addListener(capturingListener);

        Espresso.onView(withText("ANY1"))
                .perform(ViewActions.click())
                ;

        assertThat(capturingListener.itemSelected, is("ANY1"));

    }

    @Test
    public void theOneWhereWeSelectTheSecondItem() {
        configOptionsListWearView.showConfigItems(Arrays.asList("ANY1", "ANY2", "ANY3"));


        CapturingListener capturingListener = new CapturingListener();
        configOptionsListWearView.addListener(capturingListener);

        Espresso.onView(withId(R.id.wearable_list))
                .perform(scrollWearableListToPosition(1));

        Espresso.onView(withText("ANY2"))
                .perform(ViewActions.click())
                ;

        assertThat(capturingListener.itemSelected, is("ANY2"));

    }


    static private ViewAction scrollWearableListToPosition(int i) {
        return new ScrollToPositionViewAction(i);
    }

    private static final class ScrollToPositionViewAction implements ViewAction {
        private final int position;

        private ScrollToPositionViewAction(int position) {
            this.position = position;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Matcher<View> getConstraints() {
            return allOf(isAssignableFrom(WearableListView.class), isDisplayed());
        }

        @Override
        public String getDescription() {
            return "scroll WearableListView to position: " + position;
        }

        @Override
        public void perform(UiController uiController, View view) {
            WearableListView wearableListView = (WearableListView) view;
            MyOnScrollListener listener = new MyOnScrollListener();
            wearableListView.addOnScrollListener(listener);
            wearableListView.smoothScrollToPosition(position);
            while(listener.notFinishedScrolling())
            {
                uiController.loopMainThreadForAtLeast(520);
            }
            uiController.loopMainThreadUntilIdle();

            wearableListView.removeOnScrollListener(listener);
        }

        private static class MyOnScrollListener implements WearableListView.OnScrollListener {
            private int lastScrollState = -1;
            private long MAX_WAIT = SECONDS.toMillis(1);
            private long startTime = SystemClock.currentThreadTimeMillis();

            @Override public void onScroll(int i) { }

            @Override public void onAbsoluteScrollChange(int i) { }

            @Override public void onScrollStateChanged(int i) {
                lastScrollState = i;
            }

            @Override public void onCentralPositionChanged(int i) {  }

            boolean notFinishedScrolling() {
                boolean scrollState = lastScrollState != RecyclerView.SCROLL_STATE_IDLE;
                boolean timedOut = (SystemClock.currentThreadTimeMillis() - startTime) > MAX_WAIT;
                return !timedOut && scrollState;
            }
        }
    }



    private void createUI(final TestActivity activity) throws Throwable {
        uiThreadTest.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ConfigOptionsListFragment configOptionsListFragment = new ConfigOptionsListFragment();
                configOptionsListWearView = (ConfigOptionsListWearView) configOptionsListFragment.onCreateView(LayoutInflater.from(activity),activity.rootFrameLayout,null);
                configOptionsListWearView.setId(R.id.view_under_test);
                activity.setContentView(configOptionsListWearView);
            }
        });
    }


    private static class CapturingListener implements ConfigListView.Listener {
        public String itemSelected;

        @Override
        public void itemSelected(String two) {
            itemSelected = two;
        }
    }


    private class TestContext implements TestRule {

        public Context testContext;

        @Override
        public Statement apply(final Statement base, Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    testContext = InstrumentationRegistry.getTargetContext();

                    try {
                        base.evaluate();
                    } finally {
                        testContext = null;
                    }
                }
            };
        }
    }
}
