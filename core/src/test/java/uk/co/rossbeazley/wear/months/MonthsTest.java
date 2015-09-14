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
        assertThat(MonthFactory.fromBaseTen(1).toString(),is("January"));
    }

    @Test
    public void secondMonthIsFebruary() {
        assertThat(MonthFactory.fromBaseTen(2).toString(),is("February"));
    }

    @Test
    public void forthMonthIsApril() {
        assertThat(MonthFactory.fromBaseTen(4).toString(),is("April"));
    }

    @Test
    public void fifthMonthIsMay() {
        assertThat(MonthFactory.fromBaseTen(5).toString(),is("May"));
    }

    @Test
    public void sixthMonthIsJune() {
        assertThat(MonthFactory.fromBaseTen(6).toString(),is("June"));
    }

    @Test
    public void seventhMonthIsJuly() {
        assertThat(MonthFactory.fromBaseTen(7).toString(),is("July"));
    }

    @Test
    public void eighthMonthIsAugust() {
        assertThat(MonthFactory.fromBaseTen(8).toString(),is("August"));
    }

    @Test
    public void ninthMonthIsSeptember() {
        assertThat(MonthFactory.fromBaseTen(9).toString(),is("September"));
    }

    @Test
    public void tenthMonthIsOctober() {
        assertThat(MonthFactory.fromBaseTen(10).toString(),is("October"));
    }

    @Test
    public void eleventhMonthIsNovember() {
        assertThat(MonthFactory.fromBaseTen(11).toString(),is("November"));
    }

    @Test
    public void twelthMonthIsDecember() {
        assertThat(MonthFactory.fromBaseTen(12).toString(),is("December"));
    }
}
