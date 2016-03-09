package uk.co.rossbeazley.wear.android.ui.config;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;
import uk.co.rossbeazley.wear.android.ui.config.ui.ConfigOptionView;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class SelectedConfigItemTest {

    private TestConfigService testConfigService;
    private ConfigService configService;
    private String anyItem;

    @Before
    public void buildTestWorld() {

        testConfigService = new TestConfigService();
        configService = testConfigService.build();

        anyItem = testConfigService.anyItem();

        configService.configure(anyItem);
    }
    
    @Test
    public void
    theOneWeRetrieveTheSelectedConfigOption() {
        List<String> selectedConfigOptions = configService.selectedConfigOptions();
        String[] allTheOnes = testConfigService.expectedOptionsListForItem(anyItem).toArray(new String[]{});
        assertThat(selectedConfigOptions,hasItems(allTheOnes));
    }

    @Test
    public void
    theOneWhereWeDisplayTheSelectedConfigOption() {
        CapturingConfigOptionView capturingConfigOptionView = new CapturingConfigOptionView();
        new ConfigOptionPresenter(capturingConfigOptionView,configService);
        List<String> two = testConfigService.expectedOptionsListForItem(anyItem);
        String[] expectedList = two.toArray(new String[two.size()]);
        assertThat(capturingConfigOptionView.presentedList,hasItems(expectedList));
    }

    @Test
    public void
    theOneWhereWeChooseAConfigItemOption() {

        String expectedOption = testConfigService.expectedOptionListForItem(anyItem);
        configService.choose(expectedOption);

        String optionForItem = configService.optionForItem(anyItem);

        assertThat(optionForItem,is(expectedOption));
    }

    @Test
    public void
    theOneWhereWeChooseADifferentConfigItemOption() {

        String expectedOption = testConfigService.expectedOptionListForItem(anyItem);
        configService.choose(expectedOption);

        String optionForItem = configService.optionForItem(testConfigService.aDifferentItem(anyItem));

        assertThat(optionForItem,is(not(expectedOption)));
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

        public ConfigOptionPresenter(ConfigOptionView configOptionView, ConfigService configService) {
            configOptionView.showConfigOptions(configService.selectedConfigOptions());
        }
    }

    private class CapturingConfigOptionView implements ConfigOptionView {
        public List<String> presentedList;

        @Override
        public void showConfigOptions(List<String> configOptions) {
            presentedList = configOptions;
        }

        @Override
        public void addListener(Listener capturingListener) {

        }
    }
}
