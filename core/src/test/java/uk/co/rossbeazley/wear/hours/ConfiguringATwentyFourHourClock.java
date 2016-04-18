package uk.co.rossbeazley.wear.hours;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import uk.co.rossbeazley.wear.android.ui.config.TestWorld;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.rossbeazley.wear.hours.HoursBaseConfigItem.HR_12;

public class ConfiguringATwentyFourHourClock implements CanReceiveHoursUpdates {

    private String timeComponentString;
    private CanBeTicked hours;
    private Calendar calendar;
    private TestWorld testWorld;

    @Before
    public void
    buildaTwoPMTwelveHourWorld() {

        testWorld = new TestWorld();
        testWorld.with(new HoursBaseConfigItem(HR_12));
        testWorld.build();
        hours = testWorld.core.canBeTicked;
        testWorld.core.canBeObservedForChangesToHours.addListener(this);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 2);
        calendar.set(Calendar.AM_PM, Calendar.PM);

    }

    @Test public void
    itsTwoPM() {

        testWorld.core.canConfigureHours.twentyFourHour();

        hours.tick(calendar);

        assertThat(timeComponentString,is("14"));
    }

    @Override
    public void hoursUpdate(HourBase24 hourBase24) {
        this.timeComponentString = hourBase24.toBase10TwelveHour();
    }
}
