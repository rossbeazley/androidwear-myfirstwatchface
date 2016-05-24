package uk.co.rossbeazley.wear.android.ui;

import android.app.FragmentManager;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.BaseMatcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.ScreenNavigationController;
import uk.co.rossbeazley.wear.android.ui.config.ConfigItemsListView;
import uk.co.rossbeazley.wear.android.ui.config.ConfigOptionView;
import uk.co.rossbeazley.wear.android.ui.config.UIFactory;
import uk.co.rossbeazley.wear.android.ui.config.UIFactoryFragment;
import uk.co.rossbeazley.wear.config.ConfigService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

public class FragmentScreenNavigationControllerTest {

    @Rule
    public ActivityTestRule<TestActivity> activityActivityTestRule = new ActivityTestRule<>(TestActivity.class);
    private TestActivity activity;
    private FragmentManager fm;
    private ScreenNavigationController fragmentScreenNavigationController;
    private MyUIFactoryTransaction uiFactoryTransaction;

    @Before
    public void setUp() throws Exception {
        activity = activityActivityTestRule.getActivity();
        fm = activity.getFragmentManager();
        uiFactoryTransaction = new MyUIFactoryTransaction();
        fragmentScreenNavigationController = new FragmentScreenNavigationController(R.id.test_left, R.id.test_right, UIFactory.FACTORY, TestFactoryOne.FACTORY, uiFactoryTransaction);
    }

    @Test
    @UiThreadTest
    public void showsConfigItemsAsLeftHandPane() throws Throwable {

        fragmentScreenNavigationController.showLeft(ConfigItemsListView.class);
        Serializable factoryAtId = uiFactoryTransaction.factoryAtId(R.id.test_left);
        assertThat( factoryAtId, CoreMatchers.<Serializable>is(TestFactoryOne.FACTORY));
    }


    @Test
    @UiThreadTest
    public void showsConfigItemOptionsAtRightHandPane() throws Throwable {

        fragmentScreenNavigationController.showRight(ConfigOptionView.class);
        Serializable factoryAtId = uiFactoryTransaction.factoryAtId(R.id.test_right);
        assertThat( factoryAtId, CoreMatchers.<Serializable>is(UIFactory.FACTORY));
    }


    @Test
    @UiThreadTest
    public void showsNothingAtRightPaneAfterShowingSomething() throws Throwable {
        showsConfigItemOptionsAtRightHandPane();

        fragmentScreenNavigationController.hideRight();

        Serializable factoryAtId = uiFactoryTransaction.factoryAtId(R.id.test_right);
        assertThat(factoryAtId, is(nullValue()));
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

    private enum UIFactory implements uk.co.rossbeazley.wear.android.ui.config.UIFactory<View> {
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
    private static class MyUIFactoryTransaction implements UIFactoryTransaction {
        public Map<Integer, Object> factories = new HashMap<>();

        @Override
        public <FragmentUIFactory extends Serializable & uk.co.rossbeazley.wear.android.ui.config.UIFactory> void add(FragmentUIFactory fragmentUIFactory, int id) {
            this.factories.put(id,fragmentUIFactory);
        }

        @Override
        public void remove(int id) {
            factories.remove(id);
        }

        public <FragmentUIFactory extends Serializable & uk.co.rossbeazley.wear.android.ui.config.UIFactory> FragmentUIFactory factoryAtId(int test_left) {
            return (FragmentUIFactory) factories.get(test_left);
        }
    }
}
