package uk.co.rossbeazley.wear.minutes;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Sexagesimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MinutesFromTickTockChangeTest implements CanBeObservedForChangesToMinutes.CanReceiveMinutesUpdates {

    String timeComponentString;
    private Calendar aTimeWithNineMinutes;
    private MinutesFromTick minutes;

    @Before
    public void setUp() throws Exception {
        aTimeWithNineMinutes = Calendar.getInstance();
        aTimeWithNineMinutes.set(Calendar.MINUTE, 9);
        minutes = new MinutesFromTick();
        minutes.observe(this);
    }

    @Test
    public void theOneWhereTheMinutesUpdate() {
        minutes.tick(aTimeWithNineMinutes);
        assertThat(timeComponentString, is("09"));
    }

    @Test
    public void theOneWhereTheTimeDontUpdate() {
        minutes.tick(aTimeWithNineMinutes);
        timeComponentString = "RESET";
        minutes.tick(aTimeWithNineMinutes);
        assertThat(timeComponentString, is("RESET"));
    }

    @Test
    public void timeChangesButMinuteHasStayedTheSame() {
        minutes.tick(aTimeWithNineMinutes);
        timeComponentString = "RESET";
        Calendar aDifferentTimeWithNineMinutes = (Calendar) aTimeWithNineMinutes.clone();
        aDifferentTimeWithNineMinutes.roll(Calendar.SECOND, false);
        minutes.tick(aDifferentTimeWithNineMinutes);
        assertThat(timeComponentString, is("RESET"));
    }


    @Override
    public void minutesUpdate(Sexagesimal to) {
        timeComponentString=to.base10String();
    }


}
