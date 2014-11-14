package uk.co.rossbeazley.wear.ticktock;

import java.util.concurrent.TimeUnit;

/**
* Created by rdlb on 14/11/14.
*/
interface NarrowScheduledExecutorService {
    void scheduleAtFixedRate(Runnable command, long period, TimeUnit unit);
}
