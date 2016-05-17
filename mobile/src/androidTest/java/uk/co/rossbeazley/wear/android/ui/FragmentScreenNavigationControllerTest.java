package uk.co.rossbeazley.wear.android.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.ScreenNavigationController;
import uk.co.rossbeazley.wear.android.ui.config.ConfigItemsListView;
import uk.co.rossbeazley.wear.android.ui.config.ConfigOptionView;
import uk.co.rossbeazley.wear.android.ui.config.UIFactory;
import uk.co.rossbeazley.wear.android.ui.config.UIFactoryFragment;
import uk.co.rossbeazley.wear.config.ConfigService;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class FragmentScreenNavigationControllerTest {

    @Rule
    public ActivityTestRule<TestActivity> activityActivityTestRule = new ActivityTestRule<>(TestActivity.class);

    @Test
    public void showsConfigItemsAsLeftHandPane() throws Throwable {

        final TestActivity activity = activityActivityTestRule.getActivity();
        final FragmentManager fm = activity.getFragmentManager();
        final ScreenNavigationController fragmentScreenNavigationController = new FragmentScreenNavigationController(fm, R.id.test_left, R.id.test_right);

        fragmentScreenNavigationController.showLeft(ConfigItemsListView.class);

        //asserting on the UI thread pumps a message onto the that executes after the UI finishes its shiznit
        activityActivityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                UIFactoryFragment fragmentInLeftPane = (UIFactoryFragment) fm.findFragmentById(R.id.test_left);
                assertThat(fragmentInLeftPane, hasViewFactory(TestFactoryOne.FACTORY));
                View subViewInTestLeft = activity.findViewById(R.id.test_left).findViewById(R.id.view_under_test);
                assertThat("no view created with id view_under_test in parent view test_left",subViewInTestLeft,is(TestFactoryOne.FACTORY.createdView));
            }
        });

    }

    private Matcher<? super UIFactoryFragment> hasViewFactory(final UIFactory factory) {
        return new BaseMatcher<UIFactoryFragment>() {
            @Override
            public boolean matches(Object item) {
                if (!(item instanceof UIFactoryFragment)) return false;
                UIFactoryFragment fragment = (UIFactoryFragment) item;
                return factory == fragment.uiFactory();
            }

            @Override
            public void describeTo(Description description) {
            }
        };
    }


    public enum TestFactoryOne implements UIFactory<View> {
        FACTORY;

        public View createdView;

        @Override
        public View createView(ViewGroup container) {
            createdView = new View(container.getContext());
            createdView.setId(R.id.view_under_test);
            return createdView;
        }

        @Override
        public void createPresenters(ConfigService configService, View view) {
        }
    }


    @Test
    public void showsConfigItemOptionsAtRightHandPane() throws Throwable {
        final TestActivity activity = activityActivityTestRule.getActivity();
        final FragmentManager fm = activity.getFragmentManager();
        final ScreenNavigationController fragmentScreenNavigationController = new FragmentScreenNavigationController(fm, R.id.test_left, R.id.test_right);

        fragmentScreenNavigationController.showRight(ConfigOptionView.class);

        //asserting on the UI thread pumps a message onto the that executes after the UI finishes its shiznit
        activityActivityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                UIFactoryFragment fragmentInLeftPane = (UIFactoryFragment) fm.findFragmentById(R.id.test_right);
                assertThat(fragmentInLeftPane, hasViewFactory(TestFactoryTwo.FACTORY));
                View subViewInTestRight = activity.findViewById(R.id.test_right).findViewById(R.id.view_under_test_two);
                assertThat("no view created with id view_under_test_two in parent view test_right",subViewInTestRight,is(TestFactoryTwo.FACTORY.createdView));
            }
        });
    }



    public enum TestFactoryTwo implements UIFactory<View> {
        FACTORY;

        public View createdView;

        @Override
        public View createView(ViewGroup container) {
            createdView = new View(container.getContext());
            createdView.setId(R.id.view_under_test_two);
            return createdView;
        }

        @Override
        public void createPresenters(ConfigService configService, View view) {
        }
    }

    public void showsNothingAtRightPaneAfterShowingSomething() {
//        screen.showRight(ConfigOptionView.class);
        //screen.hideRight();
    }

    private static class FragmentScreenNavigationController implements ScreenNavigationController {

        private final FragmentManager fm;
        private final int leftID;

        public FragmentScreenNavigationController(FragmentManager fm, int test_activity_LEFT_view_id, int test_activity_RIGHT_view_id) {

            this.fm = fm;
            leftID = test_activity_LEFT_view_id;
        }

        @Override
        public void showRight(Class uiPanel) {

        }

        @Override
        public void showLeft(Class uiPanel) {
            final TestFactoryOne factory = TestFactoryOne.FACTORY;

            Fragment fragment;
            fragment = UIFactoryFragment.createUIFactoryFragment(factory);
            fm.beginTransaction()
                    .replace(leftID, fragment)
                    .commit();

        }

        @Override
        public void hideRight() {

        }
    }
}
