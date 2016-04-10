package uk.co.rossbeazley.wear.colour;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.android.ui.config.HashMapPersistence;
import uk.co.rossbeazley.wear.android.ui.config.TestConfigService;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class BackgroundColourConfiguredThroughConfigService {


    private Colours.Colour observedBackgroundColour;
    private TestConfigService testConfigService;
    private ConfigService configService;

    @Before
    public void setUp() throws Exception {

        configService = (testConfigService = new TestConfigService()).build();

        CanReceiveColourUpdates canReceiveColourUpdates = new CanReceiveColourUpdates() {
            @Override
            public void colourUpdate(Colours to) {
                observedBackgroundColour = to.background();
            }
        };
        testConfigService.core.canBeObservedForChangesToColour.addListener(canReceiveColourUpdates);
        observedBackgroundColour=null;
    }

    @Test
    public void notifyOfChange() {
        configService.configureItem("Background");
        configService.chooseOption("Black");
        assertThat(observedBackgroundColour,is(Colours.Colour.BLACK));
    }

    @Test
    public void notifyOfChangeOnlyWhenConfiguringColourNotAfter() {

        configService.configureItem("Background");
        configService.chooseOption("Black");
        observedBackgroundColour=null;

        String differentItem = testConfigService.aDifferentItem("Background");
        configService.configureItem(differentItem);

        String differentOption = testConfigService.anyOptionForItem(differentItem);
        configService.chooseOption(differentOption);

        assertThat(observedBackgroundColour,is(nullValue()));
    }

}
