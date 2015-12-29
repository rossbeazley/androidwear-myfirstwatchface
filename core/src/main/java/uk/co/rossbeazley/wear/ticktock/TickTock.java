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
                    try {
                        tock.tick(timeSource.time());
                    } catch (Exception ignored) {
                    }
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
        newTimer(200, TimeUnit.MILLISECONDS);
    }

    public void startLowResolution() {
        newTimer(1, TimeUnit.MINUTES);
    }

    private void newTimer(int period, TimeUnit timeUnit) {
        cancelable.cancel();
        cancelable = executor.scheduleAtFixedRate(tick, period, timeUnit);
    }

    private static class NullCancelable implements NarrowScheduledExecutorService.Cancelable {
        @Override public void cancel() { }
    }
}
