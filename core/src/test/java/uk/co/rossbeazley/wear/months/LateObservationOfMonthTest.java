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
public class LateObservationOfMonthTest implements CanBeObservedForChangesToMonths.CanReceiveMonthsUpdates {


    private CanBeTicked months;
    private Calendar aTimeWithNineMonths;
    private String monthString = "UNSET";
    private Main.Core core;

    @Before
    public void setUp() throws Exception {
        core = new Main.Core();
        aTimeWithNineMonths = Calendar.getInstance();
        aTimeWithNineMonths.set(Calendar.MONTH, Calendar.SEPTEMBER);
        this.months = core.canBeTicked;
    }

    @Test
    public void theOneWhereTheMonthUpdates() {
        months.tick(aTimeWithNineMonths);
        core.canBeObservedForChangesToMonths.observe(this);
        assertThat(monthString,is("September"));
    }

    @Override
    public void monthsUpdate(Month to) {
        this.monthString = to.toString();
    }
}
