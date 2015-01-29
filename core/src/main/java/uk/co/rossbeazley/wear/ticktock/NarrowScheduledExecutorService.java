package uk.co.rossbeazley.wear.ticktock;

import java.util.concurrent.TimeUnit;

interface NarrowScheduledExecutorService {
    Cancelable scheduleAtFixedRate(Runnable command, long period, TimeUnit unit);

    /**
     * Created by beazlr02 on 29/01/2015.
     */
    interface Cancelable {
        void cancel();
    }
}
