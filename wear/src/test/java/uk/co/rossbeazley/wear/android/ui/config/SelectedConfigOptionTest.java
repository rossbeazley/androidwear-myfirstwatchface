package uk.co.rossbeazley.wear.android.ui.config;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

public class SelectedConfigOptionTest {

    @Test
    public void
    theOneWeRetrieveTheSelectedConfigOption() {

        final String[] configItems = {"one", "two", "three"};
        final String[] oneOptions = {"oneOne", "oneone", "oneThree", "oneFour"};
        final String[] twoOptions = {"twoOne", "twoTwo", "twoThree", "twoFour"};
        final String[] threeOptions = {"threeOne", "threethree", "threeThree", "threeFour"};
        HashMap<String, List<String>> config = new HashMap<String, List<String>>() {{
            put("configItems", Arrays.asList(configItems));
            put("one", Arrays.asList(oneOptions));
            put("two", Arrays.asList(twoOptions));
            put("three", Arrays.asList(threeOptions));
        }};

        StubStringPersistence stubStringPersistence = new StubStringPersistence(config);
        ConfigService configService = new ConfigService(stubStringPersistence);

        configService.configure("one");

        List<String> selectedConfigOptions = configService.selectedConfigOptions();
        assertThat(selectedConfigOptions,hasItems(oneOptions));
    }

    @Test
    public void
    theOneWhereWeDisplayTheSelectedConfigOption() {

        final String[] configItems = {"one", "two", "three"};
        final String[] expectedList = {"twoOne", "twoTwo", "twoThree", "twoFour"};
        HashMap<String, List<String>> config = new HashMap<String, List<String>>() {{
            put("configItems", Arrays.asList(configItems));
            put("two", Arrays.asList(expectedList));
        }};

        StubStringPersistence stubStringPersistence = new StubStringPersistence(config);
        ConfigService configService = new ConfigService(stubStringPersistence);

        configService.configure("two");


        CapturingConfigOptionView capturingConfigOptionView = new CapturingConfigOptionView();

        new ConfigOptionPresenter(capturingConfigOptionView,configService);

        assertThat(capturingConfigOptionView.presentedList,hasItems(expectedList));
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
    }

    //TODO next up is the connected test for this view, need to extract interface and then implement
}
