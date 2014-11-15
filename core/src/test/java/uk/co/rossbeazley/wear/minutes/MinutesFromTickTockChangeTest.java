package uk.co.rossbeazley.wear.minutes;

import org.junit.Test;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Sexagesimal;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MinutesFromTickTockChangeTest implements CanBeObservedForChangesToMinutes.CanReceiveMinutesUpdates {

    String timeComponentString;
    private Calendar aTimeWithNineMinutes;

    @Test
    public void theOneWhereTheMinutesUpdate() {

        aTimeWithNineMinutes = Calendar.getInstance();
        aTimeWithNineMinutes.set(Calendar.MINUTE, 9);

        MinutesFromTick minutes = new MinutesFromTick();
        minutes.observe(this);
        minutes.tick(aTimeWithNineMinutes);
        assertThat(timeComponentString, is("09"));
    }




    @Override
    public void minutesUpdate(Sexagesimal to) {
        timeComponentString=to.base10String();
    }


    private class MinutesFromTick implements CanBeObservedForChangesToMinutes, CanBeTicked {
        private CanReceiveMinutesUpdates canReceiveMinutesUpdates;

        @Override
        public void observe(CanReceiveMinutesUpdates canReceiveMinutesUpdates) {

            this.canReceiveMinutesUpdates = canReceiveMinutesUpdates;
        }

        @Override
        public void tick(Calendar to) {
            canReceiveMinutesUpdates.minutesUpdate(Sexagesimal.fromBase10(to.get(Calendar.MINUTE)));

        }
    }
}
