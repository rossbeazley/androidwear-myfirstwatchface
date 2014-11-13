package uk.co.rossbeazley.wear.seconds;

import org.junit.Test;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TickTockTest implements CanBeTicked {

    Calendar tickedTo;

    private Runnable command;

    final Calendar theExpectedTime = Calendar.getInstance();

    @Test
    public void theOneWhereSomethingThatCanBeTickedIsTickedPeriodically() {

        TimeSource timeSource = new FakeTimeSource();
        NarrowScheduledExecutorService executor = new FakeNarrowScheduledExecutorService();
        new TickTock(timeSource, executor, this);

        command.run();

        assertThat(tickedTo, is(theExpectedTime));
    }

    @Override
    public void tick(Calendar to) {
        tickedTo = to;
    }

    private class TickTock {
        public TickTock(final TimeSource timeSource, NarrowScheduledExecutorService executor, final CanBeTicked tock) {
            Runnable tick = new Runnable() {
                @Override
                public void run() {
                    tock.tick(timeSource.time());
                }
            };
            executor.scheduleAtFixedRate(tick,0,200,TimeUnit.MILLISECONDS);
        }
    }

    private class FakeTimeSource implements TimeSource {
        @Override
        public Calendar time() {
            return theExpectedTime;
        }
    }

    public static interface TimeSource {
        Calendar time();
    }

    private interface NarrowScheduledExecutorService {
        void scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit);
    }

    private class FakeNarrowScheduledExecutorService implements NarrowScheduledExecutorService {
        @Override
        public void scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
            TickTockTest.this.command = command;
        }

    }
}
