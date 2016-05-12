package uk.co.rossbeazley.wear.android.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.co.rossbeazley.wear.R;
import uk.co.rossbeazley.wear.android.ui.config.ConfigItemsListView;
import uk.co.rossbeazley.wear.android.ui.config.UIFactory;
import uk.co.rossbeazley.wear.android.ui.config.UIFactoryFragment;
import uk.co.rossbeazley.wear.config.ConfigService;

import static org.junit.Assert.assertThat;

public class FragmentScreenNavigationControllerTest {

    @Rule
    public ActivityTestRule<TestActivity> activityActivityTestRule = new ActivityTestRule<>(TestActivity.class);

    @Test
    public void showsConfigItemsAsLeftHandPane() {

        //screen.showLeft(ConfigItemsListView.class);

        FragmentManager fm = activityActivityTestRule.getActivity().getFragmentManager();

        UIFactoryFragment fragmentInLeftPane = (UIFactoryFragment) fm.findFragmentById(R.id.test_activity_LEFT_view_id);
        assertThat(fragmentInLeftPane,hasViewFactory(ConfigItemsListUIFactory.FACTORY));
    }

    private Matcher<? super UIFactoryFragment> hasViewFactory(final ConfigItemsListUIFactory factory) {
        return new BaseMatcher<UIFactoryFragment>() {
            @Override
            public boolean matches(Object item) {
                if ( ! (item instanceof UIFactoryFragment) ) return false;

                UIFactoryFragment fragment = (UIFactoryFragment) item;

                return factory == fragment.uiFactory();
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }


    public enum ConfigItemsListUIFactory implements UIFactory<ConfigItemsListView> {
        FACTORY;

        @Override
        public View createView(ViewGroup container) {
            return null;
        }

        @Override
        public void createPresenters(ConfigService configService, ConfigItemsListView view) {
        }
    }



    @Test
    public void showsConfigItemOptionsAtRightHandPane() {
//        screen.showRight(ConfigOptionView.class);
    }

    @Test
    public void showsNothingAtRightPaneAfterShowingSomething() {
//        screen.showRight(ConfigOptionView.class);
        //screen.hideRight();
    }
}
