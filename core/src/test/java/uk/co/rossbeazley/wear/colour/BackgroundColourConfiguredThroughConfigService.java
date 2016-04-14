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
    private String backgroundItemId;
    private BackgroundColourConfigItem backgroundColourConfigItem;

    @Before
    public void setUp() throws Exception {

        configService = (testWorld = new TestWorld()).build();

        CanReceiveColourUpdates canReceiveColourUpdates = new CanReceiveColourUpdates() {
            @Override
            public void colourUpdate(Colours to) {
                observedBackgroundColour = to.colour();
            }
        };
        testWorld.core.canBeObservedForChangesToColour.addListener(canReceiveColourUpdates);
        observedBackgroundColour=null;
        backgroundColourConfigItem = testWorld.core.backgroundColourConfigItem();
        backgroundItemId = backgroundColourConfigItem.itemId();
    }

    @Test
    public void notifyOfChange() {
        configService.configureItem(backgroundItemId);
        configService.chooseOption(backgroundColourConfigItem.optionFor(Colours.Colour.BLACK));
        assertThat(observedBackgroundColour,is(Colours.Colour.BLACK));
    }

    @Test
    public void notifyOfChangeToWhite() {
        configService.configureItem(backgroundItemId);
        configService.chooseOption(backgroundColourConfigItem.optionFor(Colours.Colour.WHITE));
        assertThat(observedBackgroundColour,is(Colours.Colour.WHITE));
    }

    @Test
    public void notifyOfChangeOnlyWhenConfiguringColourNotAfter() {

        configService.configureItem(backgroundItemId);
        configService.chooseOption(backgroundColourConfigItem.optionFor(Colours.Colour.BLACK));
        observedBackgroundColour=null;

        String differentItem = testWorld.aDifferentItem(backgroundItemId);
        configService.configureItem(differentItem);

        String differentOption = testWorld.anyOptionForItem(differentItem);
        configService.chooseOption(differentOption);

        assertThat(observedBackgroundColour,is(nullValue()));
    }

}
