package uk.co.rossbeazley.wear.seconds;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.Calendar;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SecondsChange implements Seconds.CanReceiveSecondsUpdates {

    private String timeComponentString;
    private Seconds seconds;
    private Calendar aTimeWithTenSeconds;

    @Override
    public void secondsUpdate(Sexagesimal to)
    {
        this.timeComponentString = to.base10String();
    }

    @Before
    public void setUp() throws Exception {
        seconds = new Seconds();
        seconds.observe(this);
        aTimeWithTenSeconds = Calendar.getInstance();
        aTimeWithTenSeconds.set(Calendar.SECOND, 10);
    }

    @Test
    public void theOneWhereTheSecondsUpdate() 
    {
        seconds.tick(aTimeWithTenSeconds);
        assertThat(timeComponentString, is(Sexagesimal.fromBase10(10).base10String()));
    }



    @Test @Ignore
    public void theOneWhereTheSecondsDontUpdate() {

    }

    @Test @Ignore
    public void theOneWhereTheSecondsRollOver() {

    }

    public static class Sexagesimal {
        private final int value;

        private Sexagesimal(int base10) {
            value = base10;
        }

        public static Sexagesimal fromBase10(int base10) {
            return new Sexagesimal(base10);
        }

        public String base10String() {
            DecimalFormat numberFormat = new DecimalFormat("*00");
            return numberFormat.format(value);
        }
    }

}
