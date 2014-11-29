package uk.co.rossbeazley.wear.minutes;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Main;
import uk.co.rossbeazley.wear.Sexagesimal;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LateObservationOfMinutesTest implements CanBeObservedForChangesToMinutes.CanReceiveMinutesUpdates {

    String timeComponentString;
    private Calendar aTimeWithNineMinutes;
    private CanBeTicked minutes;
    private Main.Core core;


    @Before
    public void setUp() throws Exception {
        core = new Main.Core();

        aTimeWithNineMinutes = Calendar.getInstance();
        aTimeWithNineMinutes.set(Calendar.MINUTE, 9);
        this.minutes = core.canBeTicked;
    }

    @Test
    public void theOneWhereTheMinutesUpdateBeforeObservation() {
        minutes.tick(aTimeWithNineMinutes);
        core.canBeObservedForChangesToMinutes.observe(this);
        assertThat(timeComponentString, is("09"));
    }


    @Override
    public void minutesUpdate(Sexagesimal to) {
        timeComponentString=to.base10String();
    }


}
