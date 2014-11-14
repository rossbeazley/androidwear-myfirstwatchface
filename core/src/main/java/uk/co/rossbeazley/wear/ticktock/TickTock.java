package uk.co.rossbeazley.wear.ticktock;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.wear.seconds.Seconds;

public class TickTock {
    TickTock(final TimeSource timeSource, NarrowScheduledExecutorService executor, final CanBeTicked tock) {
        Runnable tick = new Runnable() {
            @Override
            public void run() {
                tock.tick(timeSource.time());
            }
        };
        executor.scheduleAtFixedRate(tick,200, TimeUnit.MILLISECONDS);
    }

    public static TickTock createTickTock(Seconds seconds) {
        return new TickTock(new TimeSource() {
            @Override
            public Calendar time() {
                return Calendar.getInstance();
            }
        },new DefaultNarrowScheduledExecutorService(), seconds);
    }
}
