package uk.co.rossbeazley.wear.hours;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LateObservationOfHoursTest implements CanReceiveHoursUpdates {

    private String timeComponentString;
    private CanBeTicked hours;
    private Calendar aTimeWithThreeHours;
    private Core core;

    @Before
    public void givenATimeWithThreeHours() {
        core = new Core();
        this.hours = core.canBeTicked;
        this.aTimeWithThreeHours = Calendar.getInstance();
        aTimeWithThreeHours.set(Calendar.HOUR, 3);
    }

    @Test
    public void theOneWhereTheHourUpdatesAfterObserving() {
        hours.tick(aTimeWithThreeHours);
        core.canBeObservedForChangesToHours.addListener(this);
        assertThat(timeComponentString, is("03"));
    }

    @Override
    public void hoursUpdate(HourBase24 hourBase24) {
        this.timeComponentString = hourBase24.toBase10TwelveHour();
    }
}
