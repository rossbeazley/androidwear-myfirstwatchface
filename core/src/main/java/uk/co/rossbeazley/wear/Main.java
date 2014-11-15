package uk.co.rossbeazley.wear;

import uk.co.rossbeazley.wear.minutes.CanBeObservedForChangesToMinutes;
import uk.co.rossbeazley.wear.minutes.MinutesFromTick;
import uk.co.rossbeazley.wear.seconds.CanBeObservedForChangesToSeconds;
import uk.co.rossbeazley.wear.seconds.Seconds;
import uk.co.rossbeazley.wear.ticktock.TickTock;

public class Main {

    private static Main instance;
    public CanBeObservedForChangesToMinutes canBeObservedForChangesToMinutes;

    public static Main instance() { return instance; }

    public static void init() { //thinking i will init the core with the external stuff?
        instance = new Main();
    }

    public final CanBeObservedForChangesToSeconds canBeObservedForChangesToSeconds;

    public Main() {
        Seconds seconds = new Seconds();
        canBeObservedForChangesToSeconds = seconds;

        MinutesFromTick minutes = new MinutesFromTick();
        canBeObservedForChangesToMinutes = minutes;

        TickTock tickTock = TickTock.createTickTock(seconds, minutes);
    }

}
