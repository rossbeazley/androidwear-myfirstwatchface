package uk.co.rossbeazley.wear.colour;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.android.ui.config.TestWorld;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class HoursColourConfiguredThroughConfigService {


    private Colours.Colour observedColour;
    private TestWorld testWorld;
    private ConfigService configService;
    private String itemId;
    private HoursColourConfigItem colourConfigItem;

    @Before
    public void setUp() throws Exception {

        configService = (testWorld = new TestWorld()).build();

        CanReceiveColourUpdates canReceiveColourUpdates = new CanReceiveColourUpdates() {
            @Override
            public void colourUpdate(Colours to) {
                observedColour = to.colour();
            }
        };
        testWorld.core.canBeObservedForChangesToHoursColour.addListener(canReceiveColourUpdates);
        observedColour =null;
        colourConfigItem = testWorld.core.hoursColourConfigItem();
        itemId = colourConfigItem.itemId();
    }

    @Test
    public void notifyOfChange() {
        configService.configureItem(itemId);
        configService.chooseOption(colourConfigItem.optionFor(Colours.Colour.RED));
        assertThat(observedColour,is(Colours.Colour.RED));
    }
/*
    @Test
    public void notifyOfChangeToWhite() {
        configService.configureItem(itemId);
        configService.chooseOption(colourConfigItem.optionFor(Colours.Colour.WHITE));
        assertThat(observedColour,is(Colours.Colour.WHITE));
    }

    @Test
    public void notifyOfChangeOnlyWhenConfiguringColourNotAfter() {

        configService.configureItem(itemId);
        configService.chooseOption(colourConfigItem.optionFor(Colours.Colour.BLACK));
        observedColour=null;

        String differentItem = testWorld.aDifferentItem(itemId);
        configService.configureItem(differentItem);

        String differentOption = testWorld.anyOptionForItem(differentItem);
        configService.chooseOption(differentOption);

        assertThat(observedColour,is(nullValue()));
    }
*/
}
