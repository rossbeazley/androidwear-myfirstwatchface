package uk.co.rossbeazley.wear.android.ui.config;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.hamcrest.CustomTypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.android.ui.espressoMatchers.DepthFirstChildCount;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

//import static android.support.test.espresso.Espresso.*;

@RunWith(AndroidJUnit4.class)
public class ConfigOptionsListWearViewTest {
    private ConfigOptionsListWearView configOptionsListWearView;

//    @Rule public TestContext testContext = new TestContext();

    @Rule
    public ActivityTestRule<TestActivity> activityTestRule = new ActivityTestRule<>(TestActivity.class);

    @Rule
    public UiThreadTestRule uiThreadTest = new UiThreadTestRule();

    @Before
    public void startTestActivityWithUIUnderTestrrs() throws Throwable {
        Activity activity = activityTestRule.getActivity();
        createUI(activity);
    }

    @Test
    public void showsOneConfigOption() throws Throwable {

        configOptionsListWearView.showConfigItems(Collections.singletonList("ANY"));

        Espresso.onView(withId(R.id.view_under_test))
                .check(DepthFirstChildCount.hasNumberOfChildrenMatching(1, withText("ANY")))
        ;


    }

    @Test
    public void showsTwoConfigOption() throws Throwable {

        configOptionsListWearView.showConfigItems(Arrays.asList("ANY", "ANY"));

        Espresso.onView(withId(R.id.view_under_test))
                .check(DepthFirstChildCount.hasNumberOfChildrenMatching(2, withText("ANY")))
        ;


    }
    @Test
    public void showsTwoDifferentConfigOption() throws Throwable {

        configOptionsListWearView.showConfigItems(Arrays.asList("ANY", "OLD"));

        Espresso.onView(withId(R.id.view_under_test))
                .check(DepthFirstChildCount.hasNumberOfChildrenMatching(1, withText("ANY")))
                .check(DepthFirstChildCount.hasNumberOfChildrenMatching(1, withText("OLD")))
        ;


    }


    private void createUI(final Activity activity) throws Throwable {
        uiThreadTest.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                configOptionsListWearView = new ConfigOptionsListWearView(activity);
                configOptionsListWearView.setId(R.id.view_under_test);
                configOptionsListWearView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                activity.setContentView(configOptionsListWearView);
            }
        });
    }


    public static class ConfigOptionsListWearView extends FrameLayout implements ConfigListView {

        private WearableListView wearableListView;

        private void _ConfigOptionsListWearView() {
            LayoutInflater.from(getContext()).inflate(R.layout.config_options_list_wear_view, this);
            wearableListView = (WearableListView) findViewById(R.id.wearable_list);
        }


        public ConfigOptionsListWearView(Context context) {
            super(context);
            _ConfigOptionsListWearView();
        }

        public ConfigOptionsListWearView(Context context, AttributeSet attrs) {
            super(context, attrs);
            _ConfigOptionsListWearView();
        }

        public ConfigOptionsListWearView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            _ConfigOptionsListWearView();
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public ConfigOptionsListWearView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);

            _ConfigOptionsListWearView();
        }

        @Override
        public void showConfigItems(List<String> list) {
            wearableListView.setAdapter(new Adapter(list));
            wearableListView.invalidate();
        }

        public static final class Adapter extends WearableListView.Adapter {

            @Override
            public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView textview = new TextView(parent.getContext());
                textview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                textview.setGravity(Gravity.CENTER);
                return new WearableListView.ViewHolder(textview);
            }

            @Override
            public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
                TextView textView = (TextView) holder.itemView;
                textView.setText(list.get(position));
            }

            @Override
            public int getItemCount() {
                return list.size();
            }


            public Adapter(List<String> list) {
                this.list = list;
            }

            private final List<String> list;

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
