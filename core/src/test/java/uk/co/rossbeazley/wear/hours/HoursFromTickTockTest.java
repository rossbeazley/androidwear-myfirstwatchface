package uk.co.rossbeazley.wear.hours;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HoursFromTickTockTest implements CanReceiveHoursUpdates {

    private String timeComponentString;
    private CanBeTicked hours;
    private Calendar aTimeWithThreeHours;

    @Before
    public void givenATimeWithThreeHours() {
        Core core = new Core();
        core.canBeObservedForChangesToHours.addListener(this);
        this.hours = core.canBeTicked;
        this.aTimeWithThreeHours = Calendar.getInstance();
        aTimeWithThreeHours.set(Calendar.HOUR,3);
    }

    @Test
    public void theOneWhereTheHourUpdates() {
        hours.tick(aTimeWithThreeHours);
        assertThat(timeComponentString, is("03"));
    }

    @Test
    public void theOneWhereTheTimeDontUpdate() {
        hours.tick(aTimeWithThreeHours);
        timeComponentString = "RESET";
        hours.tick(aTimeWithThreeHours);
        assertThat(timeComponentString,is("RESET"));
    }

    @Test
    public void theOneWhereTheTimeChangesButHoursStayTheSame() {
        hours.tick(aTimeWithThreeHours);
        timeComponentString = "RESET";
        Calendar aDifferentTimeWithThreeMinutes = aTimeWithThreeHours;
        aDifferentTimeWithThreeMinutes.roll(Calendar.DAY_OF_MONTH,true);
        hours.tick(aDifferentTimeWithThreeMinutes);
        assertThat(timeComponentString,is("RESET"));
    }

    @Override
    public void hoursUpdate(HourBase24 hourBase24) {
        this.timeComponentString = hourBase24.toBase10TwelveHour();
    }
}
