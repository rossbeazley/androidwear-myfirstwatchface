package uk.co.rossbeazley.wear.ticktock;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TickTockTest implements CanBeTicked {

    Calendar tickedTo;
    public int millisecondsToAdd;

    @Before
    public void setUp() throws Exception {
        millisecondsToAdd = 200;
    }

    @Test
    public void theOneWhereSomethingThatCanBeTickedIsTickedPeriodically() {

        Calendar epoc = Calendar.getInstance();
        FakeNarrowScheduledExecutorService executor = new FakeNarrowScheduledExecutorService(epoc, millisecondsToAdd);
        TimeSource timeSource = executor;

        TickTock clock = new TickTock(timeSource, executor, this);

        executor.elapseTime();

        epoc.add(Calendar.MILLISECOND, millisecondsToAdd); //Should this be using time in millis in assertion?
        assertThat(tickedTo, is(epoc));
    }

    @Test
    public void theOneWhereWeStopTheTickTockAfterOnePassageOfTime() {
        Calendar epoc = Calendar.getInstance();
        FakeNarrowScheduledExecutorService executor = new FakeNarrowScheduledExecutorService(epoc, millisecondsToAdd);
        TimeSource timeSource = executor;

        TickTock clock = new TickTock(timeSource, executor, this);//1422537103807

        executor.elapseTime();

        clock.stop();

        executor.elapseTime();


        epoc.add(Calendar.MILLISECOND, millisecondsToAdd); //Should this be using time in millis in assertion?

        assertThat(tickedTo, is(epoc));
    }

    @Test
    public void theOneWhereWeReStartTheClockAsTimePasses() {
        Calendar epoc = Calendar.getInstance();
        tickedTo = epoc;
        FakeNarrowScheduledExecutorService executor = new FakeNarrowScheduledExecutorService(epoc, millisecondsToAdd);
        TimeSource timeSource = executor;

        TickTock clock = new TickTock(timeSource, executor, this);

        executor.elapseTime();

        clock.stop();

        clock.start();

        executor.elapseTime();


        epoc.add(Calendar.MILLISECOND, millisecondsToAdd); //Should this be using time in millis in assertion?

        assertThat(tickedTo, is(epoc));
    }

    @Override
    public void tick(Calendar to) {
        tickedTo = to;
    }


    private class FakeNarrowScheduledExecutorService implements NarrowScheduledExecutorService, TimeSource {

        private long epocMS;
        int UNSET = Integer.MIN_VALUE;
        Calendar theNextExpectedTime;
        private Runnable command;
        private int millisecondsToAdd;
        private boolean started;

        public FakeNarrowScheduledExecutorService(Calendar epoc, int millisecondsToAdd) {
            this.millisecondsToAdd = millisecondsToAdd;
            epocMS = epoc.getTimeInMillis();
            started = true;
        }

        @Override
        public Cancelable scheduleAtFixedRate(Runnable command, long period, TimeUnit unit) {
            this.command = command;
            return new Cancelable() {
                @Override
                public void cancel() {
                    started = false;
                }
            };
        }

        public void elapseTime() {

            theNextExpectedTime = Calendar.getInstance();
            epocMS = epocMS + millisecondsToAdd;
            theNextExpectedTime.setTimeInMillis(epocMS);
            if(started) {
                command.run();
            }
        }

        @Override
        public Calendar time() {
            return theNextExpectedTime;
        }
    }

}
