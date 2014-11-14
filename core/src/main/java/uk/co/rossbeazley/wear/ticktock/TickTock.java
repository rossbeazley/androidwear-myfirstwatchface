package uk.co.rossbeazley.wear.ticktock;

import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.wear.ticktock.CanBeTicked;
import uk.co.rossbeazley.wear.ticktock.NarrowScheduledExecutorService;
import uk.co.rossbeazley.wear.ticktock.TimeSource;

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
