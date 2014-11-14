package uk.co.rossbeazley.wear.ticktock;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DefaultNarrowScheduledExecutorService implements NarrowScheduledExecutorService {

    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void scheduleAtFixedRate(Runnable command, long period, TimeUnit unit) {
        service.scheduleAtFixedRate(command,0,period,unit);
    }
}
