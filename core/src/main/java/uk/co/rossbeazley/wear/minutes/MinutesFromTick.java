package uk.co.rossbeazley.wear.minutes;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Announcer;
import uk.co.rossbeazley.wear.Sexagesimal;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

public class MinutesFromTick implements CanBeObservedForChangesToMinutes<CanReceiveMinutesUpdates>, CanBeTicked {
    private final Announcer<CanReceiveMinutesUpdates> canReceiveMinutesUpdates;
    private Sexagesimal current;

    public MinutesFromTick() {
        canReceiveMinutesUpdates = Announcer.to(CanReceiveMinutesUpdates.class);
        canReceiveMinutesUpdates.registerProducer(new Announcer.Producer<CanReceiveMinutesUpdates>() {
            @Override
            public void observed(CanReceiveMinutesUpdates observer) {
                /** this null check exists because our constructor doesn't create a whole object */
                if(current!=null) observer.minutesUpdate(current);
            }
        });
    }

    @Override
    public void addListener(CanReceiveMinutesUpdates canReceiveMinutesUpdates) {
        this.canReceiveMinutesUpdates.addListener(canReceiveMinutesUpdates);
    }

    @Override
    public void removeListener(CanReceiveMinutesUpdates canReceiveSecondsUpdates) {

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
