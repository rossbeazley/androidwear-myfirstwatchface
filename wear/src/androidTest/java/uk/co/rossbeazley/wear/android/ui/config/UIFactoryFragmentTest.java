package uk.co.rossbeazley.wear.android.ui.config;

import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.UiThreadTestRule;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Before;
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

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void createsPresentersWithView() throws Throwable {
        final TestActivity activity = activityTestRule.getActivity();
        uiThreadTest.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                UIFactoryFragment uiFragment = new UIFactoryFragment();
                final Bundle args = new Bundle();
                args.putSerializable("factory",FACTORY.STUB);
                uiFragment.setArguments(args);

                activity.getFragmentManager()
                        .beginTransaction()
                        .add(TestActivity.TEST_ACTIVITY_ROOT_VIEW_ID,uiFragment)
                        .commit();

            }
        });


        View capturedView= FACTORY.STUB.capturedView;
        View expectedView = FACTORY.STUB.expectedView;
        assertThat(capturedView, is(expectedView));

    }

    public enum FACTORY implements UIFactory<View>{

        STUB;

        private View expectedView;
        private View capturedView;

        @Override
        public View createView(ViewGroup container) {
            expectedView = new View(container.getContext());
            return expectedView;
        }

        @Override
        public void createPresenters(ConfigService configService, View view) {
            this.capturedView = view;
        }
    }


}