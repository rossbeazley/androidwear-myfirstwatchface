package uk.co.rossbeazley.wear.android.ui;

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
import uk.co.rossbeazley.wear.android.ui.config.UIFactoryFragment;
import uk.co.rossbeazley.wear.config.ConfigService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class UIFragmentTransactionTest {

    @Rule
    public ActivityTestRule<TestActivity> activityActivityTestRule = new ActivityTestRule<>(TestActivity.class);
    private TestActivity activity;
    private FragmentManager fm;
    private UIFactoryFragmentTransaction uiFactoryTransaction;

    @Before
    public void setUp() throws Exception {
        activity = activityActivityTestRule.getActivity();
        fm = activity.getFragmentManager();
        uiFactoryTransaction = new UIFactoryFragmentTransaction(fm);
    }

    @Test
    @UiThreadTest
    public void showsAUIFactoryFragment() throws Throwable {

        uiFactoryTransaction.add(TestFactoryOne.FACTORY,R.id.test_left);

        fm.executePendingTransactions();

        UIFactoryFragment fragmentInLeftPane = (UIFactoryFragment) fm.findFragmentById(R.id.test_left);
        assertThat(fragmentInLeftPane, hasViewFactory(TestFactoryOne.FACTORY));
        View subViewInTestLeft = activity.findViewById(R.id.test_left).findViewById(R.id.view_under_test);
        assertThat("no view created with id view_under_test in parent view test_left", subViewInTestLeft, is(TestFactoryOne.FACTORY.createdView));
    }


    @Test
    @UiThreadTest
    public void removesFragment() throws Throwable {
        showsAUIFactoryFragment();

        uiFactoryTransaction.remove(R.id.test_left);

        fm.executePendingTransactions();
        UIFactoryFragment fragmentInRightPane = (UIFactoryFragment) fm.findFragmentById(R.id.test_left);
        assertThat(fragmentInRightPane, is(nullValue()));
        View subViewInTestRight = activity.findViewById(R.id.test_right).findViewById(R.id.view_under_test_two);
        assertThat(subViewInTestRight, is(nullValue()));
    }


    private enum TestFactoryOne implements uk.co.rossbeazley.wear.android.ui.config.UIFactory<View> {
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

    private Matcher<? super UIFactoryFragment> hasViewFactory(final uk.co.rossbeazley.wear.android.ui.config.UIFactory factory) {
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
