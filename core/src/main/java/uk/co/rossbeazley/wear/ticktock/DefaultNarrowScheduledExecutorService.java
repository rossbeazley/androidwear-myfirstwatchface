package uk.co.rossbeazley.wear.ticktock;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class DefaultNarrowScheduledExecutorService implements NarrowScheduledExecutorService {

    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    @Override
    public Cancelable scheduleAtFixedRate(Runnable command, long period, TimeUnit unit) {
        command.run();
        final ScheduledFuture<?> result = service.scheduleAtFixedRate(command, period, period, unit);
        return new Cancelable() {
            @Override
            public void cancel() {
                result.cancel(true);
            }
        };
    }
}
