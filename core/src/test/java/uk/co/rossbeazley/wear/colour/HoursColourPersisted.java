package uk.co.rossbeazley.wear.colour;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.android.ui.config.HashMapPersistence;
import uk.co.rossbeazley.wear.android.ui.config.TestWorld;
import uk.co.rossbeazley.wear.android.ui.config.service.StringPersistence;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HoursColourPersisted {


    private Core core;
    private CanBeObserved<CanReceiveColourUpdates> colour;
    private Colours.Colour observedBackgroundColour;
    private CanReceiveColourUpdates canReceiveColourUpdates;
    private HashMapPersistence persistence;
    private TestWorld testWorld;

    @Before
    public void setUp() throws Exception {
        persistence = new HashMapPersistence();
        buildCore(persistence);

    }

    private void buildCore(HashMapPersistence persistence) {
        testWorld = new TestWorld();
        testWorld.with(persistence).build();
        core = testWorld.core;
        colour = core.canBeObservedForChangesToHoursColour;
        canReceiveColourUpdates = new CanReceiveColourUpdates() {
            @Override
            public void colourUpdate(Colours to) {
                observedBackgroundColour = to.colour();
            }
        };
        colour.addListener(canReceiveColourUpdates);
    }

    @Test
    public void coreHasDefaultColour() {
        String defaultOption = core.hoursColourConfigItem().defaultOption();
        assertThat(observedBackgroundColour,is(core.hoursColourConfigItem().colourFor(defaultOption)));
    }

    @Test
    public void canRememberChangedColour() {
        core.canBeColoured.hours(Colours.Colour.BLUE);
        buildCore(persistence);
        assertThat(observedBackgroundColour,is(Colours.Colour.BLUE));
    }

}
