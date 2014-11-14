package uk.co.rossbeazley.wear;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import uk.co.rossbeazley.wear.NarrowScheduledExecutorService;

/**
* Created by rdlb on 14/11/14.
*/ // TO BE USED IN THE REAL WORLD
public class DefaultNarrowScheduledExecutorService implements NarrowScheduledExecutorService {

    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void scheduleAtFixedRate(Runnable command, long period, TimeUnit unit) {
        service.scheduleAtFixedRate(command,0,period,unit);
    }
}
