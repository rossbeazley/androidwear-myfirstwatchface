package uk.co.rossbeazley.wear.seconds;

import org.junit.Ignore;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.Calendar;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SecondsChange implements Seconds.CanReceiveSecondsUpdates {

    private String timeComponentString;

    @Override
    public void secondsUpdate(Sexagesimal to)
    {
        this.timeComponentString = to.base10String();
    }
    
    @Test
    public void theOneWhereTheSecondsUpdate() 
    {
        Seconds seconds = new Seconds();
        seconds.observe(this);

        Calendar aTimeWithTenSeconds = Calendar.getInstance();
        aTimeWithTenSeconds.set(Calendar.SECOND, 10);
        
        seconds.tick(aTimeWithTenSeconds);

        assertThat(timeComponentString, is(new Sexagesimal(10).base10String()));
    }



    @Test @Ignore
    public void theOneWhereTheSecondsDontUpdate() {

    }

    @Test @Ignore
    public void theOneWhereTheSecondsRoleOver() {

    }

    public static class Sexagesimal {
        private final int value;


        public Sexagesimal(int i) {
            value = i;
        }

        public String base10String() {
            DecimalFormat numberFormat = new DecimalFormat("*00");
            return numberFormat.format(value);
        }
    }

}
