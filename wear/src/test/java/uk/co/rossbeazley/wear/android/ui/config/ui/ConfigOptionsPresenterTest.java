package uk.co.rossbeazley.wear.android.ui.config.ui;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import uk.co.rossbeazley.wear.android.ui.config.TestWorld;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConfigOptionsPresenterTest {

    private TestWorld testWorld;
    private ConfigService configService;
    private String anyItem;
    private CapturingConfigOptionView capturingConfigOptionView;

    @Before
    public void buildTestWorld() {

        testWorld = new TestWorld();
        configService = testWorld.build();

        anyItem = testWorld.anyItemID();

        configService.configureItem(anyItem);

        capturingConfigOptionView = new CapturingConfigOptionView();

        ConfigItemOptionsListFragment configItemsListFragment = new ConfigItemOptionsListFragment();
        configItemsListFragment.attachConfigService(configService);

        configItemsListFragment.buildPresenters(capturingConfigOptionView,null);

    }

    @Test
    public void
    theOneWhereWeDisplayTheSelectedConfigItemOptions() {
        List<String> expectedOptions = testWorld.optionsListForItem(anyItem);
        assertThat(capturingConfigOptionView.presentedList,is(equalTo(expectedOptions)));
    }

    @Test
    public void
    theOneWhereWeChooseAConfigItemOption() {

        String expectedOption = testWorld.anyOptionForItem(anyItem);

        capturingConfigOptionView.capturingListener.itemSelected(expectedOption);

        String optionForItem = configService.currentOptionForItem(anyItem);

        assertThat(optionForItem,is(expectedOption));
    }

    private class CapturingConfigOptionView implements ConfigOptionView {
        public List<String> presentedList;
        public Listener capturingListener;

        @Override
        public void showConfigOptions(List<String> configOptions) {
            presentedList = configOptions;
        }

        @Override
        public void addListener(Listener capturingListener) {

            this.capturingListener = capturingListener;
        }
    }
}
