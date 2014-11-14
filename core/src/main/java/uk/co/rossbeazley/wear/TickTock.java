package uk.co.rossbeazley.wear;

import java.util.concurrent.TimeUnit;

/**
* Created by rdlb on 14/11/14.
*/
public class TickTock {
    public TickTock(final TimeSource timeSource, NarrowScheduledExecutorService executor, final CanBeTicked tock) {
        Runnable tick = new Runnable() {
            @Override
            public void run() {
                tock.tick(timeSource.time());
            }
        };
        executor.scheduleAtFixedRate(tick,200, TimeUnit.MILLISECONDS);
    }
}
