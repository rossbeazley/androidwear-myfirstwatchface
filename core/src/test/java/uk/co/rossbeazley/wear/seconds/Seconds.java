package uk.co.rossbeazley.wear.seconds;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Announcer;

public class Seconds {

    private SecondsChange.Sexagesimal current = null;

    public static interface CanReceiveSecondsUpdates {
        void secondsUpdate(SecondsChange.Sexagesimal to);
    }


    final private Announcer<CanReceiveSecondsUpdates> canReceiveSecondsUpdates;

    public Seconds() {
        canReceiveSecondsUpdates = Announcer.to(CanReceiveSecondsUpdates.class);
    }

    public void tick(Calendar to) {
        int secondsFromTick = to.get(Calendar.SECOND);
        SecondsChange.Sexagesimal toSeconds = SecondsChange.Sexagesimal.fromBase10(secondsFromTick);

        if(toSeconds.equals(current)) return;

        current=toSeconds;
        announcer().secondsUpdate(current);
    }

    private CanReceiveSecondsUpdates announcer() {
        return canReceiveSecondsUpdates.announce();
    }

    public void observe(CanReceiveSecondsUpdates canReceiveSecondsUpdates) {
        this.canReceiveSecondsUpdates.addListener(canReceiveSecondsUpdates);
    }


}
