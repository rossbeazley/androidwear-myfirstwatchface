package uk.co.rossbeazley.wear.seconds;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TickerTest implements CanBeTicked {

    Calendar tickedTo;

    private Runnable command;

    final Calendar theExpectedTime = Calendar.getInstance();

    @Test
    public void theOneWhereSomethingThatCanBeTickedIsTickedPeriodically() {

        TimeSource timeSource = new FakeTimeSource();
        ScheduledExecutorService executor = new FakeScheduledExecutorService();
        new Ticker(timeSource, executor, this);

        command.run();

        assertThat(tickedTo, is(theExpectedTime));
    }

    @Override
    public void tick(Calendar to) {
        tickedTo = to;
    }

    private class Ticker {
        public Ticker(final TimeSource timeSource, ScheduledExecutorService executor, final CanBeTicked tickerTest) {
            executor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    tickerTest.tick(timeSource.time());
                }
            },0,200,TimeUnit.MILLISECONDS);
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

    private class FakeScheduledExecutorService implements ScheduledExecutorService {


        @Override
        public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
            return null;
        }

        @Override
        public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
            return null;
        }

        @Override
        public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
            TickerTest.this.command = command;
            return null;
        }

        @Override
        public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
            return null;
        }

        @Override
        public void shutdown() {

        }

        @Override
        public List<Runnable> shutdownNow() {
            return null;
        }

        @Override
        public boolean isShutdown() {
            return false;
        }

        @Override
        public boolean isTerminated() {
            return false;
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return null;
        }

        @Override
        public <T> Future<T> submit(Runnable task, T result) {
            return null;
        }

        @Override
        public Future<?> submit(Runnable task) {
            return null;
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
            return null;
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
            return null;
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
            return null;
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }

        @Override
        public void execute(Runnable command) {

        }
    }
}
