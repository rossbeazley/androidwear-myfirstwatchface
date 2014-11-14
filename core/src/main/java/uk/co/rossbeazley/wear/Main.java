package uk.co.rossbeazley.wear;

import java.util.Calendar;

import uk.co.rossbeazley.wear.seconds.CanBeObservedForChangesToSeconds;
import uk.co.rossbeazley.wear.seconds.Seconds;

public class Main {

    private static Main instance;

    public static Main instance() { return instance; }

    public static void init() {
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
