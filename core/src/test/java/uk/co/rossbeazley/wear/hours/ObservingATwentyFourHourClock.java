package uk.co.rossbeazley.wear.hours;

import org.junit.Test;

import java.util.Calendar;

import uk.co.rossbeazley.wear.TestWorld;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.rossbeazley.wear.hours.HoursModeConfigItem.*;

public class ObservingATwentyFourHourClock implements CanReceiveHoursUpdates {

    private String timeComponentString;
    private CanBeTicked hours;

    @Test public void
    itsTwoPM() {

        final TestWorld testWorld = new TestWorld();
        testWorld.with(new HoursModeConfigItem(HR_12));
        testWorld.build();
        hours = testWorld.core.canBeTicked;
        testWorld.core.canBeObservedForChangesToHours.addListener(this);

        Calendar aTimeWithThreeHours = Calendar.getInstance();
        aTimeWithThreeHours.set(Calendar.HOUR, 2);
        aTimeWithThreeHours.set(Calendar.AM_PM, Calendar.PM);

        hours.tick(aTimeWithThreeHours);

        assertThat(timeComponentString,is("02"));
    }

    @Test public void
    itsDefaultedToFourteenHundredHours() {

        final TestWorld testWorld = new TestWorld();
        testWorld.with(new HoursModeConfigItem(HR_24));
        testWorld.build();
        hours = testWorld.core.canBeTicked;
        testWorld.core.canBeObservedForChangesToHours.addListener(this);

        Calendar aTimeWithThreeHours = Calendar.getInstance();
        aTimeWithThreeHours.set(Calendar.HOUR, 2);
        aTimeWithThreeHours.set(Calendar.AM_PM, Calendar.PM);
        hours.tick(aTimeWithThreeHours);

        assertThat(timeComponentString,is("14"));
    }


    @Override
    public void hoursUpdate(HourBase24 hourBase24) {
        this.timeComponentString = hourBase24.toBase10TwelveHour();
    }
}
