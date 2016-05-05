package uk.co.rossbeazley.wear.android.ui.config;

import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.UiThreadTestRule;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Rule;
import org.junit.Test;

import uk.co.rossbeazley.wear.config.ConfigService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UIFactoryFragmentTest {
    @Rule
    public ActivityTestRule<TestActivity> activityTestRule = new ActivityTestRule<>(TestActivity.class);

    @Rule
    public UiThreadTestRule uiThreadTest = new UiThreadTestRule();

    @Test
    public void createsPresentersWithView() throws Throwable {
        final TestActivity activity = activityTestRule.getActivity();

        UIFactoryFragment uiFragment = UIFactoryFragment.createUIFactoryFragment(CAPTURING_IMPLEMENTATION.Factory);

        activity.getFragmentManager()
                .beginTransaction()
                .add(TestActivity.TEST_ACTIVITY_ROOT_VIEW_ID, uiFragment)
                .commit();

        View capturedView = CAPTURING_IMPLEMENTATION.Factory.captured;
        View expectedView = CAPTURING_IMPLEMENTATION.Factory.created;
        assertThat(capturedView, is(expectedView));

    }

    public enum CAPTURING_IMPLEMENTATION implements UIFactory<View> {

        Factory;

        private View created;
        private View captured;

        @Override
        public View createView(ViewGroup container) {
            created = new View(container.getContext());
            return created;
        }

        @Override
        public void createPresenters(ConfigService configService, View view) {
            this.captured = view;
        }
    }


}