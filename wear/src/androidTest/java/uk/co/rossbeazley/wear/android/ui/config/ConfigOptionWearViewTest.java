package uk.co.rossbeazley.wear.android.ui.config;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

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
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.rossbeazley.wear.android.ui.espressoMatchers.DepthFirstChildCount.hasNumberOfChildrenMatching;

@RunWith(AndroidJUnit4.class)
public class ConfigOptionWearViewTest {

    private ConfigOptionsWearView configOptionsWearView;

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
        configOptionsWearView.showConfigOptions(Collections.singletonList("ANY"));
        Espresso.onView(withId(R.id.view_under_test))
                .check(hasNumberOfChildrenMatching(1, withText("ANY")));
    }

    @Test
    public void showsTwoConfigOption() throws Throwable {
        configOptionsWearView.showConfigOptions(Arrays.asList("ANY", "ANY"));
        Espresso.onView(withId(R.id.view_under_test))
                .check(hasNumberOfChildrenMatching(2, withText("ANY")));
    }
    @Test
    public void showsTwoDifferentConfigOption() throws Throwable {
        configOptionsWearView.showConfigOptions(Arrays.asList("ANY", "OLD"));
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

        configOptionsWearView.showConfigOptions(list);
        Espresso.onView(withId(R.id.wearable_list))
                .perform(ScrollToPositionViewAction.scrollWearableListToPosition(4))
                .check(hasNumberOfChildrenMatching(1, withText("ANY4")));
    }


    @Test
    public void theOneWhereWeSelectAnItem() {
        configOptionsWearView.showConfigOptions(Arrays.asList("ANY1", "ANY2", "ANY3"));
        CapturingListener capturingListener = new CapturingListener();
        configOptionsWearView.addListener(capturingListener);
        Espresso.onView(withText("ANY1"))
                .perform(ViewActions.click());

        assertThat(capturingListener.itemSelected, is("ANY1"));
    }

    @Test
    public void theOneWhereWeSelectTheSecondItem() {
        configOptionsWearView.showConfigOptions(Arrays.asList("ANY1", "ANY2", "ANY3"));


        CapturingListener capturingListener = new CapturingListener();
        configOptionsWearView.addListener(capturingListener);

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
                ConfigOptionsWearView configOptionsWearView = new ConfigOptionsWearView(activity);
                configOptionsWearView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                ConfigOptionWearViewTest.this.configOptionsWearView = configOptionsWearView;
                ConfigOptionWearViewTest.this.configOptionsWearView.setId(R.id.view_under_test);
                activity.rootFrameLayout.addView(configOptionsWearView);
            }
        });
    }

    private class ConfigOptionsWearView extends FrameLayout implements ConfigOptionView {

        private WearableListView wearableListView;
        private CapturingListener listener;

        void _ConfigOptionsWearView()
        {
            LayoutInflater.from(getContext()).inflate(R.layout.config_options_list_wear_view, this);
            wearableListView = (WearableListView) findViewById(R.id.wearable_list);
            wearableListView.setClickListener(new WearableListView.ClickListener() {
                @Override
                public void onClick(WearableListView.ViewHolder viewHolder) {
                    String text = String.valueOf(((TextView) viewHolder.itemView).getText());
                    listener.itemSelected(text);
                }

                @Override
                public void onTopEmptyRegionClick() {

                }
            });

        }

        public ConfigOptionsWearView(Context context) {
            super(context);
            _ConfigOptionsWearView();
        }

        public ConfigOptionsWearView(Context context, AttributeSet attrs) {
            super(context, attrs);
            _ConfigOptionsWearView();
        }

        public ConfigOptionsWearView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            _ConfigOptionsWearView();
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public ConfigOptionsWearView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            _ConfigOptionsWearView();
        }

        @Override
        public void showConfigOptions(List<String> configOptions) {
            wearableListView.setAdapter(new ConfigOptionsListWearView.Adapter(configOptions));
        }

        public void addListener(CapturingListener capturingListener) {

            listener = capturingListener;
        }
    }

    private class CapturingListener {
        public String itemSelected;

        public void itemSelected(String text) {
            this.itemSelected = text;
        }
    }
}
