package uk.co.rossbeazley.wear.minutes;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Announcer;
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

    @Test
    public void theOneWhereTheTimeDontUpdate() {

    }

    @Test @Ignore("just thinking ahead")
    public void timeChangesButMinuteHasStayedTheSame() {

    }


    @Override
    public void minutesUpdate(Sexagesimal to) {
        timeComponentString=to.base10String();
    }


    private class MinutesFromTick implements CanBeObservedForChangesToMinutes, CanBeTicked {
        private final Announcer<CanReceiveMinutesUpdates> canReceiveMinutesUpdates;

        private MinutesFromTick() {
            canReceiveMinutesUpdates = Announcer.to(CanReceiveMinutesUpdates.class);
        }

        @Override
        public void observe(CanReceiveMinutesUpdates canReceiveMinutesUpdates) {
            this.canReceiveMinutesUpdates.addListener(canReceiveMinutesUpdates);
        }

        @Override
        public void tick(Calendar to) {
            int minsFromTick = to.get(Calendar.MINUTE);
            Sexagesimal minutes = Sexagesimal.fromBase10(minsFromTick);
            tick(minutes);
        }

        private void tick(Sexagesimal to) {
            canReceiveMinutesUpdates.announce().minutesUpdate(to);
        }
    }
}
