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
}
