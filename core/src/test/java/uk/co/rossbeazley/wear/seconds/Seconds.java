package uk.co.rossbeazley.wear.seconds;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Announcer;

public class Seconds {

    public static interface CanReceiveSecondsUpdates {
        void secondsUpdate(SecondsChange.Sexagesimal to);
    }


    final private Announcer<CanReceiveSecondsUpdates> canReceiveSecondsUpdates;

    public Seconds() {
        canReceiveSecondsUpdates = Announcer.to(CanReceiveSecondsUpdates.class);
    }

    public void tick(Calendar instance) {
        int secondsFromTick = instance.get(Calendar.SECOND);
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
