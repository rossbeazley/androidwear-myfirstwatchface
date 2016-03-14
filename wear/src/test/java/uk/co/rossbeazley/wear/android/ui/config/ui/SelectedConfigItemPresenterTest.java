package uk.co.rossbeazley.wear.android.ui.config.ui;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import uk.co.rossbeazley.wear.android.ui.config.TestConfigService;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SelectedConfigItemPresenterTest {

    private TestConfigService testConfigService;
    private ConfigService configService;
    private String anyItem;
    private CapturingConfigOptionView capturingConfigOptionView;

    @Before
    public void buildTestWorld() {

        testConfigService = new TestConfigService();
        configService = testConfigService.build();

        anyItem = testConfigService.anyItemID();

        configService.configureItem(anyItem);

        capturingConfigOptionView = new CapturingConfigOptionView();
        new ConfigOptionPresenter(capturingConfigOptionView,configService);

    }

    @Test
    public void
    theOneWhereWeDisplayTheSelectedConfigItemOptions() {
        List<String> expectedOptions = testConfigService.expectedOptionsListForItem(anyItem);
        assertThat(capturingConfigOptionView.presentedList,is(equalTo(expectedOptions)));
    }

    @Test
    public void
    theOneWhereWeChooseAConfigItemOption() {

        String expectedOption = testConfigService.anyExpectedOptionForItem(anyItem);

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
