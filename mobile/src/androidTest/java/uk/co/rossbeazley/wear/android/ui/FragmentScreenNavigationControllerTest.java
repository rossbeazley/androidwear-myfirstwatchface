package uk.co.rossbeazley.wear.android.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
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
    private TestActivity activity;
    private FragmentManager fm;
    private ScreenNavigationController fragmentScreenNavigationController;

    @Before
    public void setUp() throws Exception {
        activity = activityActivityTestRule.getActivity();
        fm = activity.getFragmentManager();
        fragmentScreenNavigationController = new FragmentScreenNavigationController(fm, R.id.test_left, R.id.test_right);
    }

    @Test
    @UiThreadTest
    public void showsConfigItemsAsLeftHandPane() throws Throwable {

        fragmentScreenNavigationController.showLeft(ConfigItemsListView.class);

        fm.executePendingTransactions();

        UIFactoryFragment fragmentInLeftPane = (UIFactoryFragment) fm.findFragmentById(R.id.test_left);
        assertThat(fragmentInLeftPane, hasViewFactory(TestFactoryOne.FACTORY));
        View subViewInTestLeft = activity.findViewById(R.id.test_left).findViewById(R.id.view_under_test);
        assertThat("no view created with id view_under_test in parent view test_left", subViewInTestLeft, is(TestFactoryOne.FACTORY.createdView));
    }


    @Test
    @UiThreadTest
    public void showsConfigItemOptionsAtRightHandPane() throws Throwable {

        fragmentScreenNavigationController.showRight(ConfigOptionView.class);

        fm.executePendingTransactions();
        UIFactoryFragment fragmentInRightPane = (UIFactoryFragment) fm.findFragmentById(R.id.test_right);
        assertThat(fragmentInRightPane, hasViewFactory(TestFactoryTwo.FACTORY));
        View subViewInTestRight = activity.findViewById(R.id.test_right).findViewById(R.id.view_under_test_two);
        assertThat("no view created with id view_under_test_two in parent view test_right", subViewInTestRight, is(TestFactoryTwo.FACTORY.createdView));
    }



    @Test
    @UiThreadTest
    public void showsNothingAtRightPaneAfterShowingSomething() throws Throwable {
        showsConfigItemOptionsAtRightHandPane();

        fm.executePendingTransactions();

        fragmentScreenNavigationController.hideRight();

        fm.executePendingTransactions();
        UIFactoryFragment fragmentInRightPane = (UIFactoryFragment) fm.findFragmentById(R.id.test_right);
        assertThat(fragmentInRightPane, is(nullValue()));
        View subViewInTestRight = activity.findViewById(R.id.test_right).findViewById(R.id.view_under_test_two);
        assertThat(subViewInTestRight, is(nullValue()));
    }

    private static class FragmentScreenNavigationController implements ScreenNavigationController {

        private final FragmentManager fm;
        private final int leftID;
        private final int rightID;
        private Fragment rightFragment;

        public FragmentScreenNavigationController(FragmentManager fm, int test_activity_LEFT_view_id, int test_activity_RIGHT_view_id) {

            this.fm = fm;
            leftID = test_activity_LEFT_view_id;
            rightID = test_activity_RIGHT_view_id;
        }

        @Override
        public void showRight(Class uiPanel) {

            rightFragment = UIFactoryFragment.createUIFactoryFragment(TestFactoryTwo.FACTORY);
            fm.beginTransaction()
                    .replace(rightID, rightFragment)
                    .commit();

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
            final Fragment fragmentById = fm.findFragmentById(rightID);
            fm.beginTransaction()
                    .remove(fragmentById)
                    .commit();
        }
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

}
