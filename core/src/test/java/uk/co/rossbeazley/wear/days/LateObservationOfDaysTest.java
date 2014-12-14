package uk.co.rossbeazley.wear.days;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LateObservationOfDaysTest implements CanReceiveDaysUpdates {

    String timeComponentString;
    private Calendar aTimeWithFirstDayOfMonth;
    private CanBeTicked days;
    private Core core;


    @Before
    public void setUp() throws Exception {
        core = new Core();
        aTimeWithFirstDayOfMonth = Calendar.getInstance();
        aTimeWithFirstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        this.days = core.canBeTicked;
    }

    @Test
    public void theOneWhereTheDayChangesBeforeObservation() {
        days.tick(aTimeWithFirstDayOfMonth);
        core.canBeObservedForChangesToDays.observe(this);
        assertThat(timeComponentString,is("1st"));
    }

    @Override
    public void daysUpdate(Day to) {
        timeComponentString = to.toOrdinalString();
    }
}
