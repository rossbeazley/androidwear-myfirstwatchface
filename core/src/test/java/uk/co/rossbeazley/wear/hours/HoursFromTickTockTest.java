package uk.co.rossbeazley.wear.hours;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import uk.co.rossbeazley.wear.HourBase24;
import uk.co.rossbeazley.wear.Main;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HoursFromTickTockTest implements CanBeObservedForChangesToHours.CanReceiveHoursUpdates {

    private String timeComponentString;
    private CanBeTicked hours;
    private Calendar aTimeWithThreeHours;

    @Before
    public void givenATimeWithThreeHours() {
        Main.Core core = new Main.Core();
        core.canBeObservedForChangesToHours.observe(this);
        this.hours = core.canBeTicked;
        this.aTimeWithThreeHours = Calendar.getInstance();
        aTimeWithThreeHours.set(Calendar.HOUR,3);
    }

    @Test
    public void theOneWhereTheHourUpdates() {
        hours.tick(aTimeWithThreeHours);
        assertThat(timeComponentString, is("03"));
    }

    @Override
    public void hoursUpdate(HourBase24 hourBase24) {
        this.timeComponentString = hourBase24.toBase10TwelveHour();
    }
}
