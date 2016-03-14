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

    private class ConfigOptionPresenter {

        /**
         *
         * maybe it would be a good idea to totally dumb down views so they just speak in terms of
         * primitive data, the presenter is the thing that actually adapts the strings and things
         * into domain objects, maybe it can speak in terms of uiclick and show string and read string
         * it might make a number of view implementations more reusable, eg lists of items
         *
         *
         * the presenter would be responsible for mapping uiclick into the domain message
         * so there would be a generic uiclick listener, maybe viewActionListener
         *
         * In a way this is a more classical MVC arrangement with the view being composed
         * of the P+V from MVP... The M is still the same, but where is the controller?
         *
         * With MVP as I do it, there is something I call a "navigation controller"
         * this is responsible for bringing together the MVP triad.
         *
         * So here we have is Model-ViewPresenter-Controller.
         */

        public ConfigOptionPresenter(ConfigOptionView configOptionView, final ConfigService configService) {
            configOptionView.addListener(new ConfigOptionView.Listener() {
                @Override
                public void itemSelected(String option) {
                    configService.chooseOption(option);
                }
            });
            configOptionView.showConfigOptions(configService.selectedConfigOptions());
        }
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
