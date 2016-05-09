package uk.co.rossbeazley.wear;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.android.ui.config.ConfigItemsListView;
import uk.co.rossbeazley.wear.android.ui.config.ConfigOptionView;
import uk.co.rossbeazley.wear.config.ConfigService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class MobileUINavigationTest {

    private CapturingScreen screen ;
    private MobileUINavigation mobileUINavigation;
    private TestWorld testWorld;
    private ConfigService configService;

    @Before
    public void setUp() throws Exception {
        screen = new CapturingScreen();
        testWorld = new TestWorld();
        configService = testWorld.build();

        mobileUINavigation = new MobileUINavigation(screen);
    }

    @Test
    public void defaultNavigationShowsConfigItemsAsLeftHandPane() {
        final Class configItemsListViewClass = ConfigItemsListView.class;
        assertThat(screen.currentLeft(),is(equalTo(configItemsListViewClass)));
    }


    @Test
    public void showsConfigItemsAtRightHandPane() {
        configService.configureItem(testWorld.anyItemID());
        final Class configOptionViewClass = ConfigOptionView.class;
        assertThat(screen.currentRight(),is(equalTo(configOptionViewClass)));
    }




    private class CapturingScreen {
        private Class left;
        private Class right;

        public void showRight(Class uiPanel) {
            right=uiPanel;
        }

        public void showLeft(Class uiPanel) {
            left=uiPanel;
        }

        public Class currentLeft() {
            return left;
        }

        public Class currentRight() {
            return right;
        }
    }

    private class MobileUINavigation {
        public MobileUINavigation(CapturingScreen screen) {
            screen.showLeft(ConfigItemsListView.class);

            screen.showRight(ConfigOptionView.class);
        }
    }
}
