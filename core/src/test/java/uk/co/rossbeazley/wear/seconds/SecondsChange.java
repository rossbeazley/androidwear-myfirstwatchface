package uk.co.rossbeazley.wear.seconds;

import org.junit.Test;

import java.util.Calendar;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SecondsChange implements Seconds.CanReceiveSecondsUpdates {

    private String timeComponentString;

    @Override
    public void secondsUpdate(Sexagesimal to)
    {
        this.timeComponentString = to.toString();
    }
    
    @Test
    public void theOneWhereTheSecondsUpdate() 
    {
        Calendar aTimeWithZeroSeconds = Calendar.getInstance();
        aTimeWithZeroSeconds.set(Calendar.SECOND, 0);

        Seconds seconds = new Seconds(aTimeWithZeroSeconds);
        seconds.observe(this);

        Calendar aTimeWithTenSeconds = Calendar.getInstance();
        aTimeWithTenSeconds.set(Calendar.SECOND, 10);
        
        seconds.tick(aTimeWithTenSeconds);

        assertThat(timeComponentString, is(new Sexagesimal(10).toString()));
    }



    @Test
    public void theOneWhereTheSecondsDontUpdate() {

    }

    @Test
    public void theOneWhereTheSecondsRoleOver() {

    }

    public static class Sexagesimal {
        private final int value;

        public Sexagesimal(int i) {
            value = i;
        }
    }

}
