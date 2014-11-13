package uk.co.rossbeazley.wear.seconds;

import org.junit.Test;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TickTockTest implements CanBeTicked {

    Calendar tickedTo;

    @Test
    public void theOneWhereSomethingThatCanBeTickedIsTickedPeriodically() {

        Calendar epoc = Calendar.getInstance();
        FakeNarrowScheduledExecutorService executor = new FakeNarrowScheduledExecutorService(epoc);
        TimeSource timeSource = executor;

        new TickTock(timeSource, executor, this);

        executor.elapseTime();

        epoc.add(Calendar.MILLISECOND,200);
        assertThat(tickedTo, is(epoc));
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
            executor.scheduleAtFixedRate(tick,200,TimeUnit.MILLISECONDS);
        }
    }

    public static interface TimeSource {
        Calendar time();
    }

    public static interface NarrowScheduledExecutorService {
        void scheduleAtFixedRate(Runnable command, long period, TimeUnit unit);
    }


    private class FakeNarrowScheduledExecutorService implements NarrowScheduledExecutorService, TimeSource {

        Calendar theNextExpectedTime;
        private Runnable command;
        private int millisecondsToAdd;

        public FakeNarrowScheduledExecutorService(Calendar epoc) {
            theNextExpectedTime = (Calendar) epoc.clone();
        }

        @Override
        public void scheduleAtFixedRate(Runnable command, long period, TimeUnit unit) {
            this.command = command;
            millisecondsToAdd = (int) TimeUnit.MILLISECONDS.convert(period, unit);
        }

        public void elapseTime() {
            theNextExpectedTime.add(Calendar.MILLISECOND, millisecondsToAdd);
            command.run();
        }

        @Override
        public Calendar time() {
            return theNextExpectedTime;
        }
    }

}
