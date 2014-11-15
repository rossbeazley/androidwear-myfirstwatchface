package uk.co.rossbeazley.wear.ticktock;

import org.junit.Test;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TickTockTest implements CanBeTicked {

    Calendar tickedTo;

    @Test
    public void theOneWhereSomethingThatCanBeTickedIsTickedPeriodically() {

        Calendar epoc = Calendar.getInstance();
        FakeNarrowScheduledExecutorService executor = new FakeNarrowScheduledExecutorService(epoc);
        TimeSource timeSource = executor;

        new TickTock(timeSource, executor, this, null);

        executor.elapseTime();

        epoc.add(Calendar.MILLISECOND,200); //Should this be using time in millis in assertion?
        assertThat(tickedTo, is(epoc));
    }

    @Override
    public void tick(Calendar to) {
        tickedTo = to;
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
