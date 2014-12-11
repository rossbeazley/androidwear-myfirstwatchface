package uk.co.rossbeazley.wear.seconds;

import java.util.Calendar;

import uk.co.rossbeazley.wear.Announcer;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;
import uk.co.rossbeazley.wear.Sexagesimal;

public class Seconds implements CanBeObservedForChangesToSeconds, CanBeTicked {

    final private Announcer<CanBeObservedForChangesToSeconds.CanReceiveSecondsUpdates> announcer;
    private Sexagesimal current = null;

    public Seconds() {
        announcer = Announcer.to(CanBeObservedForChangesToSeconds.CanReceiveSecondsUpdates.class);
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
        announcer().secondsUpdate(toSeconds);
    }

    private CanBeObservedForChangesToSeconds.CanReceiveSecondsUpdates announcer() {
        return announcer.announce();
    }

    public void observe(CanBeObservedForChangesToSeconds.CanReceiveSecondsUpdates canReceiveSecondsUpdates) {
        announcer.addListener(canReceiveSecondsUpdates);
    }

    @Override
    public void unObserve(CanReceiveSecondsUpdates canReceiveSecondsUpdates) {
        announcer.removeListener(canReceiveSecondsUpdates);
    }


}
