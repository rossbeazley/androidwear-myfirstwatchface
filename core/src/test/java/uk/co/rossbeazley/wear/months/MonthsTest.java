package uk.co.rossbeazley.wear.months;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by beazlr02 on 20/11/2014.
 */
public class MonthsTest {

    @Test
    public void firstMonthIsJanuary() {
        assertThat(Month.fromBaseTen(1).toString(),is("January"));
    }

    @Test
    public void secondMonthIsFebruary() {
        assertThat(Month.fromBaseTen(2).toString(),is("February"));
    }

    @Test
    public void forthMonthIsApril() {
        assertThat(Month.fromBaseTen(4).toString(),is("April"));
    }

    @Test
    public void fifthMonthIsMay() {
        assertThat(Month.fromBaseTen(5).toString(),is("May"));
    }

    @Test
    public void sixthMonthIsJune() {
        assertThat(Month.fromBaseTen(6).toString(),is("June"));
    }

    @Test
    public void seventhMonthIsJuly() {
        assertThat(Month.fromBaseTen(7).toString(),is("July"));
    }
}
