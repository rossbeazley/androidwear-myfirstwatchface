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
}
