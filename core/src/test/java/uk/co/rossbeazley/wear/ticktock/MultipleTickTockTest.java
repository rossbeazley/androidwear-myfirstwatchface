package uk.co.rossbeazley.wear.ticktock;

import org.junit.Test;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MultipleTickTockTest implements CanBeTicked {

    private int totalTicked;

    @Test
    public void theOneWhereManyThingsAreTickedOff() {

        Calendar epoc = Calendar.getInstance();
        epoc.set(Calendar.SECOND,5);
        FakeNarrowScheduledExecutorService executor = new FakeNarrowScheduledExecutorService(epoc);
        TimeSource timeSource = executor;

        new TickTock(timeSource, executor, this, this);

        executor.command.run();

        assertThat(totalTicked,is(10));
    }

    @Override
    public void tick(Calendar to) {
        totalTicked += to.get(Calendar.SECOND);
    }

    private class FakeNarrowScheduledExecutorService implements NarrowScheduledExecutorService, TimeSource {

        private final Calendar epoc;
        private Runnable command;

        public FakeNarrowScheduledExecutorService(Calendar epoc) {
            this.epoc = epoc;
        }

        @Override
        public Cancelable scheduleAtFixedRate(Runnable command, long period, TimeUnit unit) {
            this.command = command;
            return null;
        }

        @Override
        public Calendar time() {
            return epoc;
        }
    }

}
