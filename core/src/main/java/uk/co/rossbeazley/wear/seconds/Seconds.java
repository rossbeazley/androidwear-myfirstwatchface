package uk.co.rossbeazley.wear.seconds;

import java.util.Calendar;

import uk.co.rossbeazley.wear.ticktock.CanBeTicked;
import uk.co.rossbeazley.wear.Sexagesimal;

public class Seconds implements CanBeTicked {

    private Sexagesimal current = null;
    private CanReceiveSecondsUpdates secondsUpdates;

    public Seconds(CanReceiveSecondsUpdates canReceiveSecondsUpdates) {
        secondsUpdates = canReceiveSecondsUpdates;
    }

    @Override
    public void tick(Calendar to) {
        int secondsFromTick = to.get(Calendar.SECOND);
        Sexagesimal toSeconds = Sexagesimal.fromBase10(secondsFromTick);
        tick(toSeconds);
    }

    private void tick(Sexagesimal toSeconds) {
        if(toSeconds.equals(current)) return;

        updateCurrentTime(toSeconds);
    }

    private void updateCurrentTime(Sexagesimal toSeconds) {
        current=toSeconds;
        secondsUpdates.secondsUpdate(toSeconds);
    }


}
