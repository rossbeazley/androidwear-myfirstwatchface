package uk.co.rossbeazley.wear.seconds;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Announcer;

public class Seconds {

    private Calendar current = null;

    public static interface CanReceiveSecondsUpdates {
        void secondsUpdate(SecondsChange.Sexagesimal to);
    }


    final private Announcer<CanReceiveSecondsUpdates> canReceiveSecondsUpdates;

    public Seconds() {
        canReceiveSecondsUpdates = Announcer.to(CanReceiveSecondsUpdates.class);
    }

    public void tick(Calendar to) {
        if(to.equals(current)) return;

        current=to;
        int secondsFromTick = current.get(Calendar.SECOND);
        SecondsChange.Sexagesimal value = SecondsChange.Sexagesimal.fromBase10(secondsFromTick);
        announcer().secondsUpdate(value);
    }

    private CanReceiveSecondsUpdates announcer() {
        return canReceiveSecondsUpdates.announce();
    }

    public void observe(CanReceiveSecondsUpdates canReceiveSecondsUpdates) {
        this.canReceiveSecondsUpdates.addListener(canReceiveSecondsUpdates);
    }


}
