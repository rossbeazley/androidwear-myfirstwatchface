package uk.co.rossbeazley.wear.minutes;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Announcer;
import uk.co.rossbeazley.wear.Sexagesimal;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

public class MinutesFromTick implements CanBeObservedForChangesToMinutes, CanBeTicked {
    private final Announcer<CanReceiveMinutesUpdates> canReceiveMinutesUpdates;
    private Sexagesimal current;

    public MinutesFromTick() {
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
        current = to.equals(current) ? current : change(to);
    }

    private Sexagesimal change(Sexagesimal to) {
        canReceiveMinutesUpdates.announce().minutesUpdate(to);
        return to;
    }
}
