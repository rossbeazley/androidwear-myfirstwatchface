package uk.co.rossbeazley.wear.ticktock;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.wear.seconds.Seconds;

public class TickTock {
    TickTock(final TimeSource timeSource, NarrowScheduledExecutorService executor, final CanBeTicked... tocks) {
        Runnable tick = new Runnable() {
            @Override
            public void run() {
                for(CanBeTicked tock :tocks) {
                    tock.tick(timeSource.time());
                }
            }
        };
        executor.scheduleAtFixedRate(tick,200, TimeUnit.MILLISECONDS);
    }

    public static TickTock createTickTock(CanBeTicked... tocks) {

        class CalendarTimeSource implements TimeSource {
            @Override public Calendar time() {
                return Calendar.getInstance();
            }
        }

        return new TickTock(new CalendarTimeSource(), new DefaultNarrowScheduledExecutorService(), tocks);
    }
}
