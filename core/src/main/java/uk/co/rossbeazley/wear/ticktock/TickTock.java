package uk.co.rossbeazley.wear.ticktock;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TickTock {


    public NarrowScheduledExecutorService.Cancelable cancelable;
    private final NarrowScheduledExecutorService executor;
    public final Runnable tick;

    TickTock(final TimeSource timeSource, NarrowScheduledExecutorService executor, final CanBeTicked... tocks) {
        this.executor = executor;
        cancelable = new NullCancelable();
        tick = new Runnable() {
            @Override
            public void run() {
                for(CanBeTicked tock :tocks) {
                    tock.tick(timeSource.time());
                }
            }
        };
        start();
    }

    public static TickTock createTickTock(CanBeTicked... tocks) {

        class CalendarTimeSource implements TimeSource {
            @Override public Calendar time() {
                return Calendar.getInstance();
            }
        }

        return new TickTock(new CalendarTimeSource(), new DefaultNarrowScheduledExecutorService(), tocks);
    }

    public void stop() {
        cancelable.cancel();
        cancelable = new NullCancelable();
    }

    public void start() {
        cancelable.cancel();
        cancelable = executor.scheduleAtFixedRate(tick,200, TimeUnit.MILLISECONDS);
    }

    private static class NullCancelable implements NarrowScheduledExecutorService.Cancelable {
        @Override public void cancel() { }
    }
}
