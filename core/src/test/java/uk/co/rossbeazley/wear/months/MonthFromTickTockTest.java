package uk.co.rossbeazley.wear.months;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Main;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by beazlr02 on 20/11/2014.
 */
public class MonthFromTickTockTest implements CanBeObservedForChangesToMonths.CanReceiveMonthsUpdates {


    private CanBeTicked months;
    private Calendar aTimeWithNineMonths;
    private String monthString = "UNSET";

    @Before
    public void setUp() throws Exception {
        Main.Core core = new Main.Core();
        aTimeWithNineMonths = Calendar.getInstance();
        aTimeWithNineMonths.set(Calendar.MONTH, 9);
        core.canBeObservedForChangesToMonths.observe(this);
        this.months = core.canBeTicked;
    }

    @Test
    public void theOneWhereTheMonthUpdates() {
        months.tick(aTimeWithNineMonths);
        assertThat(monthString,is("September"));
    }

    @Override
    public void daysUpdate(Month to) {
        this.monthString = to.toString();
    }
}