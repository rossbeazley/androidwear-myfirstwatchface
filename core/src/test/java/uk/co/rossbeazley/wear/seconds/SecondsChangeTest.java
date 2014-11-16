package uk.co.rossbeazley.wear.seconds;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Main;
import uk.co.rossbeazley.wear.Sexagesimal;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SecondsChangeTest implements CanBeObservedForChangesToSeconds.CanReceiveSecondsUpdates {

    private String timeComponentString;
    private CanBeTicked secondsToTick;
    private Calendar aTimeWithNineSeconds;

    @Override
    public void secondsUpdate(Sexagesimal to)
    {
        this.timeComponentString = to.base10String();
    }

    @Before
    public void setUp() throws Exception {
        Main.Core core = new Main.Core();
        secondsToTick = core.ticked;
        core.canBeObservedForChangesToSeconds.observe(this);

        aTimeWithNineSeconds = Calendar.getInstance();
        aTimeWithNineSeconds.set(Calendar.SECOND, 9);
        aTimeWithNineSeconds.set(Calendar.HOUR, 9);
    }

    @Test
    public void theOneWhereTheSecondsUpdate() 
    {
        secondsToTick.tick(aTimeWithNineSeconds);
        assertThat(timeComponentString, is("09"));
    }

    @Test
    public void theOneWhereTheTimeDontUpdate() {
        secondsToTick.tick(aTimeWithNineSeconds);
        timeComponentString = "RESET";
        secondsToTick.tick(aTimeWithNineSeconds);
        assertThat(timeComponentString, is("RESET"));
    }

    @Test
    public void theOneWhereTheTimeChangesButSecondsStayTheSame() {
        secondsToTick.tick(aTimeWithNineSeconds);
        timeComponentString = "RESET";
        Calendar aDifferentTimeWithNineSeconds = (Calendar) aTimeWithNineSeconds.clone();
        aDifferentTimeWithNineSeconds.roll(Calendar.HOUR,true);
        secondsToTick.tick(aDifferentTimeWithNineSeconds);
        assertThat(timeComponentString, is("RESET"));
    }

}
