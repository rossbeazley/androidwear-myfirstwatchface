package uk.co.rossbeazley.wear.android.ui.config;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4.class)
public class ConfigOptionsListWearViewTest {

    @Rule public TestContext testContext = new TestContext();


    @Test
    public void showsAListOfConfigOptions() {

        assertThat("testContext in unit test", testContext.testContext,is(notNullValue()));
        ConfigOptionsListWearView configOptionsListWearView = new ConfigOptionsListWearView(testContext.testContext);
        configOptionsListWearView.measure(400,400);
        configOptionsListWearView.layout(0,0,400,400);

        configOptionsListWearView.showConfigItems(Collections.singletonList("ANY"));

        ArrayList<View> outViews = new ArrayList<>();
        configOptionsListWearView.findViewsWithText(outViews, "ANY", ViewGroup.FIND_VIEWS_WITH_TEXT);

        int numberOfViewsWithText = outViews.size();
        assertThat(numberOfViewsWithText, is(1));
    }


    public static class ConfigOptionsListWearView extends FrameLayout implements ConfigListView {

        public ConfigOptionsListWearView(Context context) {
            super(context);
        }

        public ConfigOptionsListWearView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public ConfigOptionsListWearView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public ConfigOptionsListWearView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void showConfigItems(List<String> list) {

            TextView textView = new TextView(getContext());
            textView.setText("ANY");
            addView(textView);
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
