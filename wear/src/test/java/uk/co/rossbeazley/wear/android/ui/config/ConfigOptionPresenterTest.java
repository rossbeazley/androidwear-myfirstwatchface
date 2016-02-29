package uk.co.rossbeazley.wear.android.ui.config;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

public class ConfigOptionPresenterTest {

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
        public ConfigOptionPresenter(CapturingConfigOptionView capturingConfigOptionView, ConfigService configService) {
            capturingConfigOptionView.showConfigOptions(configService.selectedConfigOptions());
        }
    }

    private class CapturingConfigOptionView {
        public List<String> presentedList;

        public void showConfigOptions(List<String> configOptions) {
            presentedList = configOptions;
        }
    }
}
