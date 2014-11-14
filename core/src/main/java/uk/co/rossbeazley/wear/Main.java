package uk.co.rossbeazley.wear;

import java.util.Calendar;

import uk.co.rossbeazley.wear.seconds.CanBeObservedForChangesToSeconds;
import uk.co.rossbeazley.wear.seconds.Seconds;
import uk.co.rossbeazley.wear.ticktock.DefaultNarrowScheduledExecutorService;
import uk.co.rossbeazley.wear.ticktock.TickTock;
import uk.co.rossbeazley.wear.ticktock.TimeSource;

public class Main {

    private static Main instance;

    public static Main instance() { return instance; }

    public static void init() { //thinking i will init the core with the external stuff?
        instance = new Main();
    }

    public final CanBeObservedForChangesToSeconds canBeObservedForChangesToSeconds;

    public Main() {
        Seconds seconds = new Seconds();
        canBeObservedForChangesToSeconds = seconds;

        new TickTock(new TimeSource() {
            @Override
            public Calendar time() {
                return Calendar.getInstance();
            }
        },new DefaultNarrowScheduledExecutorService(), seconds);
    }
}
