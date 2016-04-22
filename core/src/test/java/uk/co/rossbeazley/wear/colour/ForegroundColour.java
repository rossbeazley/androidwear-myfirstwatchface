package uk.co.rossbeazley.wear.colour;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.TestWorld;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ForegroundColour {


    private Core core;
    private CanBeObserved<CanReceiveColourUpdates> colour;
    private Colours.Colour foregroundColour;
    private CanReceiveColourUpdates canReceiveColourUpdates;
    private TestWorld testWorld;

    @Before
    public void setUp() throws Exception {
        testWorld = new TestWorld()
                        .with(new HoursColourConfigItem(Colours.Colour.BLUE));

        testWorld.build();
        core = testWorld.core;
        colour = core.canBeObservedForChangesToHoursColour;
        canReceiveColourUpdates = new CanReceiveColourUpdates() {
            @Override
            public void colourUpdate(Colours to) {
                foregroundColour = to.colour();
            }
        };
        colour.addListener(canReceiveColourUpdates);

    }

    @Test
    public void coreDefaultsColour() {
        assertThat(foregroundColour,is(Colours.Colour.BLUE));
    }

    @Test
    public void updateColour() {
        core.canBeColoured.hours(Colours.Colour.WHITE);
        assertThat(foregroundColour,is(Colours.Colour.WHITE));
    }

    @Test
    public void updateColourBeforeObserving() {
        colour.removeListener(canReceiveColourUpdates);
        foregroundColour = null;
        core.canBeColoured.hours(Colours.Colour.WHITE);
        colour.addListener(canReceiveColourUpdates);
        assertThat(foregroundColour,is(Colours.Colour.WHITE));
    }


    @Test @Ignore("to spec")
    public void optionsListReturnedBasedOnConstructionParams() {

    }
}
