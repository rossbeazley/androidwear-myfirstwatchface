package uk.co.rossbeazley.wear.colour;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.android.ui.config.TestWorld;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class BackgroundColourConfiguredThroughConfigService {


    private Colours.Colour observedBackgroundColour;
    private TestWorld testWorld;
    private ConfigService configService;

    @Before
    public void setUp() throws Exception {

        configService = (testWorld = new TestWorld()).build();

        CanReceiveColourUpdates canReceiveColourUpdates = new CanReceiveColourUpdates() {
            @Override
            public void colourUpdate(Colours to) {
                observedBackgroundColour = to.background();
            }
        };
        testWorld.core.canBeObservedForChangesToColour.addListener(canReceiveColourUpdates);
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

        String differentItem = testWorld.aDifferentItem("Background");
        configService.configureItem(differentItem);

        String differentOption = testWorld.anyOptionForItem(differentItem);
        configService.chooseOption(differentOption);

        assertThat(observedBackgroundColour,is(nullValue()));
    }

}
