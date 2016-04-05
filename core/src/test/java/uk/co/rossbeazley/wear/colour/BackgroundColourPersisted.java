package uk.co.rossbeazley.wear.colour;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.android.ui.config.HashMapPersistence;
import uk.co.rossbeazley.wear.android.ui.config.service.StringPersistence;
import uk.co.rossbeazley.wear.rotation.Orientation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BackgroundColourPersisted {


    private Core core;
    private CanBeObserved<CanReceiveColourUpdates> colour;
    private Colours.Colour observedBackgroundColour;
    private CanReceiveColourUpdates canReceiveColourUpdates;
    private HashMapPersistence persistence;

    @Before
    public void setUp() throws Exception {
        persistence = new HashMapPersistence();
        buildCore(persistence);

    }

    private void buildCore(StringPersistence persistence) {
        core = new Core(persistence);
        colour = core.canBeObservedForChangesToColour;
        canReceiveColourUpdates = new CanReceiveColourUpdates() {
            @Override
            public void colourUpdate(Colours to) {
                observedBackgroundColour = to.background();
            }
        };
        colour.addListener(canReceiveColourUpdates);
    }

    @Test
    public void coreDefaultsToWhite() {
        assertThat(observedBackgroundColour,is(Colours.Colour.WHITE));
    }

    @Test
    public void canRememberChangedColour() {
        core.canBeColoured.background(Colours.Colour.BLACK);

        buildCore(persistence);

        assertThat(observedBackgroundColour,is(Colours.Colour.BLACK));
    }

}
