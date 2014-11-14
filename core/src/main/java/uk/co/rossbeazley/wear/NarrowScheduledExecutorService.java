package uk.co.rossbeazley.wear;

import java.util.concurrent.TimeUnit;

/**
* Created by rdlb on 14/11/14.
*/
public interface NarrowScheduledExecutorService {
    void scheduleAtFixedRate(Runnable command, long period, TimeUnit unit);
}
