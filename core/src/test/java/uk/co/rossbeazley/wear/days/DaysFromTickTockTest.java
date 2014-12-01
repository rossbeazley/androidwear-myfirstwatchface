package uk.co.rossbeazley.wear.days;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Core;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DaysFromTickTockTest implements CanBeObservedForChangesToDays.CanReceiveDaysUpdates {

    String timeComponentString;
    private Calendar aTimeWithFirstDayOfMonth;
    private CanBeTicked days;


    @Before
    public void setUp() throws Exception {
        Core core = new Core();

        aTimeWithFirstDayOfMonth = Calendar.getInstance();
        aTimeWithFirstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        core.canBeObservedForChangesToDays.observe(this);
        this.days = core.canBeTicked;
    }

    @Test
    public void theOneWhereTheDayChanges() {
        days.tick(aTimeWithFirstDayOfMonth);
        assertThat(timeComponentString,is("1st"));
    }

    @Test
    public void theOneWhereTheTimeChangesOnTheSameDay() {
        days.tick(aTimeWithFirstDayOfMonth);
        timeComponentString = "RESET";
        days.tick(aTimeWithFirstDayOfMonth);
        assertThat(timeComponentString,is("RESET"));
    }

    @Override
    public void daysUpdate(Day to) {
        timeComponentString = to.toOrdinalString();
    }
}
