package uk.co.rossbeazley.wear;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.android.ui.config.ConfigItemsListView;
import uk.co.rossbeazley.wear.android.ui.config.ConfigOptionView;
import uk.co.rossbeazley.wear.config.ConfigService;
import uk.co.rossbeazley.wear.config.ConfigServiceListener;

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

        mobileUINavigation = new MobileUINavigation(screen, configService);
    }

    @Test
    public void defaultNavigationShowsConfigItemsAsLeftHandPane() {
        assertThat(screen.leftShown(),is(true));
    }

    @Test
    public void defaultNavigationShowsNothingAsRightHandPane() {
        assertThat(screen.currentRight(),is(equalTo(screen.nothing())));
    }


    @Test
    public void showsConfigItemOptionsAtRightHandPane() {
        configService.configureItem(testWorld.anyItemID());
        final Class configOptionViewClass = ConfigOptionView.class;
        assertThat(screen.currentRight(),is(equalTo(configOptionViewClass)));
    }

    @Test
    public void showsNothingAtRightPaneAfterOptionChosen() {

        final String item = testWorld.anyItemID();
        configService.configureItem(item);
        configService.chooseOption(testWorld.anyOptionForItem(item));
        assertThat(screen.currentRight(),is(equalTo(screen.nothing())));
    }



    private static class CapturingScreen implements ScreenNavigationController {
        private boolean leftShown = false;
        private Class right = nothing();

        @Override
        public void showRight(Class uiPanel) {
            right=uiPanel;
        }

        @Override
        public void showLeft() {
            leftShown = true;
        }

        public boolean leftShown() {
            return leftShown;
        }

        public Class currentRight() {
            return right;
        }

        public static Class nothing() {
            return CapturingScreen.class;
        }

        @Override
        public void hideRight() {
            right = nothing();
        }
    }

    private class MobileUINavigation {
        public MobileUINavigation(final ScreenNavigationController screen, ConfigService configService) {
            screen.showLeft();

            configService.addListener(new ConfigServiceListener() {
                @Override
                public void configuring(String item) {
                    screen.showRight(ConfigOptionView.class);
                }

                @Override
                public void error(KeyNotFound keyNotFound) {

                }

                @Override
                public void chosenOption(String option) {
                    screen.hideRight();
                }
            });
        }
    }
}
