package uk.co.rossbeazley.wear.seconds;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Sexagesimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SecondsChangeTest implements Seconds.CanReceiveSecondsUpdates {

    private String timeComponentString;
    private Seconds seconds;
    private Calendar aTimeWithNineSeconds;

    @Override
    public void secondsUpdate(Sexagesimal to)
    {
        this.timeComponentString = to.base10String();
    }

    @Before
    public void setUp() throws Exception {
        seconds = new Seconds();
        seconds.observe(this);
        aTimeWithNineSeconds = Calendar.getInstance();
        aTimeWithNineSeconds.set(Calendar.SECOND, 9);
        aTimeWithNineSeconds.set(Calendar.HOUR, 9);
    }

    @Test
    public void theOneWhereTheSecondsUpdate() 
    {
        seconds.tick(aTimeWithNineSeconds);
        assertThat(timeComponentString, is("09"));
    }

    @Test
    public void theOneWhereTheTimeDontUpdate() {
        seconds.tick(aTimeWithNineSeconds);
        timeComponentString = "RESET";
        seconds.tick(aTimeWithNineSeconds);
        assertThat(timeComponentString, is("RESET"));
    }

    @Test
    public void theOneWhereTheTimeChangesButSecondsStayTheSame() {
        seconds.tick(aTimeWithNineSeconds);
        timeComponentString = "RESET";
        Calendar aDifferentTimeWithNineSeconds = (Calendar) aTimeWithNineSeconds.clone();
        aDifferentTimeWithNineSeconds.set(Calendar.HOUR,10);
        seconds.tick(aDifferentTimeWithNineSeconds);
        assertThat(timeComponentString, is("RESET"));
    }

}
