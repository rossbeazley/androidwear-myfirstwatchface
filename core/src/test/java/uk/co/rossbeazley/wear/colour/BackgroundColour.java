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

    @Before
    public void setUp() throws Exception {
        core = new Core();
        colour = core.canBeObservedForChangesToColour;
        colour.addListener(new CanReceiveColourUpdates() {
            @Override
            public void colourUpdate(Colours to) {
                backgroundColour = to.background();
            }
        });

    }

    @Test
    public void coreDefaultsToWhite() {
        assertThat(backgroundColour,is(Colours.Colour.WHITE));
    }
}
