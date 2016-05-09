package uk.co.rossbeazley.wear;

import org.junit.Test;

import uk.co.rossbeazley.wear.android.ui.config.ConfigItemsListView;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class MobileUINavigationTest {

    private static final Class CONFIG_ITEMS = ConfigItemsListView.class;

    @Test
    public void defaultNavigationShowsConfigItemsAsLeftHandPane() {
        CapturingScreen screen = new CapturingScreen();
        new MobileUINavigation(screen);

        assertThat(screen.currentLeft(),is(equalTo(CONFIG_ITEMS)));
    }




    private class CapturingScreen {
        private Class left;

        public void showLeft(Class uiPanel) {
            left=uiPanel;
        }

        public Class currentLeft() {
            return left;
        }
    }

    private class MobileUINavigation {
        public MobileUINavigation(CapturingScreen screen) {
            screen.showLeft(ConfigItemsListView.class);
        }
    }
}
