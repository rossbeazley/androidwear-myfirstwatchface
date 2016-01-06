package uk.co.rossbeazley.wear.colour;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.Core;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BackgroundColour {


    private Core core;
    private CanBeObserved<CanReceiveColourUpdates> colour;
    private Colours.Colour backgroundColour;
    private CanReceiveColourUpdates canReceiveColourUpdates;

    @Before
    public void setUp() throws Exception {
        core = new Core();
        colour = core.canBeObservedForChangesToColour;
        canReceiveColourUpdates = new CanReceiveColourUpdates() {
            @Override
            public void colourUpdate(Colours to) {
                backgroundColour = to.background();
            }
        };
        colour.addListener(canReceiveColourUpdates);

    }

    @Test
    public void coreDefaultsToWhite() {
        assertThat(backgroundColour,is(Colours.Colour.WHITE));
    }

    @Test
    public void canChangeColour() {
        core.canBeColoured.background(Colours.Colour.BLACK);
        assertThat(backgroundColour,is(Colours.Colour.BLACK));
    }

    @Test
    public void canObserveNewColourAfterChange() {
        core.canBeColoured.background(Colours.Colour.BLACK);
        backgroundColour = null;
        colour.removeListener(canReceiveColourUpdates);

        colour.addListener(canReceiveColourUpdates);
        assertThat(backgroundColour,is(Colours.Colour.BLACK));
    }
}
