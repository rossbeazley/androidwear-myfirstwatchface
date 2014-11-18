package uk.co.rossbeazley.wear.days;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by beazlr02 on 18/11/2014.
 */
public class DayOrdinalityTest {

    @Test
    public void theFirst() {
        assertThat(Day.fromBase10(1).toOrdinalString(), is("1st"));
    }

    @Test
    public void theSecond() {
        assertThat(Day.fromBase10(2).toOrdinalString(), is("2nd"));
    }

    @Test
    public void theThird() {
        assertThat(Day.fromBase10(3).toOrdinalString(), is("3rd"));
    }

    @Test
    public void theForth() {
        assertThat(Day.fromBase10(4).toOrdinalString(), is("4th"));
    }

    @Test
    public void theTwentyFirst() {
        assertThat(Day.fromBase10(21).toOrdinalString(), is("21st"));
    }


}
