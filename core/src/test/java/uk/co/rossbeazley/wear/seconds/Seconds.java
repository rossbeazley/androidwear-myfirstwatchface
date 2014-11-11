package uk.co.rossbeazley.wear.seconds;

import java.util.Calendar;

public class Seconds {

    private CanReceiveSecondsUpdates canReceiveSecondsUpdates = CanReceiveSecondsUpdates.NULL;

    public Seconds() {

    }

    public void tick(Calendar instance) {
        SecondsChange.Sexagesimal value = new SecondsChange.Sexagesimal(instance.get(Calendar.SECOND));
        canReceiveSecondsUpdates.secondsUpdate(value);
    }

    public void observe(CanReceiveSecondsUpdates canReceiveSecondsUpdates) {
        this.canReceiveSecondsUpdates = canReceiveSecondsUpdates;
    }

    public static interface CanReceiveSecondsUpdates {

        void secondsUpdate(SecondsChange.Sexagesimal to);

        final CanReceiveSecondsUpdates NULL = new CanReceiveSecondsUpdates() {
            @Override
            public void secondsUpdate(SecondsChange.Sexagesimal to) {
            }
        };
    }
}
