package uk.co.rossbeazley.wear;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class HourBase24Test {

    @Test
    public void twelveHourClockShows12pmAs12() throws Exception {
        String actual = HourBase24.fromBase10(12).toBase10TwelveHour();
        assertThat(actual,is("12"));
    }

    public void twelveHourClockShows12amAs12() throws Exception {
        String actual = HourBase24.fromBase10(24).toBase10TwelveHour();
        assertThat(actual,is("12"));
    }

    public void twelveHourClockShows00As12() throws Exception {
        String actual = HourBase24.fromBase10(0).toBase10TwelveHour();
        assertThat(actual,is("12"));
    }
}